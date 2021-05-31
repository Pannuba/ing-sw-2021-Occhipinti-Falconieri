package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.*;

import it.polimi.ingsw.server.messages.DiscardedResourcesMessage;
import it.polimi.ingsw.server.messages.MatchOverMessage;
import it.polimi.ingsw.server.messages.VaticanReportMessage;
import it.polimi.ingsw.util.XML_Serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Model extends Observable		/* Observed by the views to create the new gamestate */
{
	private final int numPlayers;
	private Track track;
	private final MarblesMarket marblesMarket;
	private final DevCardsMarket devCardsMarket;
	private List<Player> players;
	private List<LeaderCard> allLeaderCards;		/* All 16 leadercards, each player picks 2 out of 4 */
	private List<ActionToken> actionTokens;

	public Model(List<Player> players)
	{
		System.out.println("Creating model...");
		this.players = players;
		numPlayers = players.size();

		createLeaderCards();
		choosePlayerOrder();

		track = new Track(players);
		marblesMarket = new MarblesMarket();
		devCardsMarket = new DevCardsMarket();

		if (numPlayers == 1)
			createActionTokens();
	}

	public void update()			/* Creates the new gamestate and sends it to the views, which send it to the clients */
	{
		System.out.println("Updating gamestate...");
		setChanged();
		notifyObservers(new GameState(players, getCurrentPlayerName(), track, marblesMarket, devCardsMarket));
	}

	private void createLeaderCards()
	{
		System.out.println("Model: creating leadercards...");

		allLeaderCards = new ArrayList<>();		/* Necessary? YES */
		LeaderCard cardToAdd = null;

		for (int i = 0; i < 16; i++)
		{
			try
			{
				cardToAdd = (LeaderCard) XML_Serialization.deserialize("src/main/resources/xml/leadercards/leadercard" + (i + 1) + ".xml");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			allLeaderCards.add(cardToAdd);
		}
	}

	private void choosePlayerOrder()		/* IDs go from 0 to 3. Need to test if it's actually random */
	{
		System.out.println("Choosing a random first player...");

		Collections.shuffle(players);		/* Randomize player list */

		players.get(0).setMyTurn(true);		/* Set active turn for player with ID 0 */

		for (int i = 0; i < numPlayers; i++)
			players.get(i).setId(i);

		for (int i = 0; i < numPlayers; i++)
			System.out.println(players.get(i).getUsername() + " has ID " + players.get(i).getId() + " and isFirstTurn: " + players.get(i).isMyTurn());
	}

	public List<List<LeaderCard>> createLeaderCardsLists()				/* Returns a list of lists of leadercards, 1 for each player, each list has 4 leadercards to choose 2 from */
	{
		List<List<LeaderCard>> listOfLists = new ArrayList<>();

		Collections.shuffle(allLeaderCards);

		int i = 0;

		for (int j = 0; j < numPlayers; j++)        /* Create numPlayer lists of 4 cards each */
		{
			listOfLists.add(new ArrayList<>());

			for (int k = 0; k < 4; k++)
			{
				listOfLists.get(j).add(allLeaderCards.get(i));
				i++;
			}
		}

		return listOfLists;
	}

	public void createActionTokens()		/* Hardcoded, don't really see a use in making them serializable */
	{
		actionTokens = new ArrayList<>();

		actionTokens.add(new ActionDevCard(DevCardColor.GREEN,	devCardsMarket));
		actionTokens.add(new ActionDevCard(DevCardColor.BLUE,	devCardsMarket));
		actionTokens.add(new ActionDevCard(DevCardColor.PURPLE, devCardsMarket));
		actionTokens.add(new ActionDevCard(DevCardColor.YELLOW, devCardsMarket));
		actionTokens.add(new ActionBlack1(track, actionTokens));		/* Pass the list itself to shuffle it */
		actionTokens.add(new ActionBlack2(track));

		Collections.shuffle(actionTokens);
	}

	public void vaticanReport(int popeBoxNumber)		/* Called when a player reaches a pope box. Put in controller? */
	{
		System.out.println("Calling vatican report for pope box " + popeBoxNumber);

		for (int i = 0; i < numPlayers; i++)
		{
			switch (popeBoxNumber)
			{
				case 8:				/* First pope box has been reached */

					track.getPopeTokens()[0].setDiscarded(true);		/* Discard the track's popeToken so no more vatican reports can be called for the same box */

					if (track.getRedPawns().get(i) >= 5 && track.getRedPawns().get(i) <= 8)		/* If a player is inside the perimeter */
						players.get(i).getPopeTokens()[0].setActive(true);

					else
						players.get(i).getPopeTokens()[0].setDiscarded(true);

					break;

				case 16:        	/* Second pope box */

					track.getPopeTokens()[1].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 12 && track.getRedPawns().get(i) <= 16)
						players.get(i).getPopeTokens()[1].setActive(true);

					else
						players.get(i).getPopeTokens()[1].setDiscarded(true);

					break;

				case 24:			/* Third pope box */

					track.getPopeTokens()[2].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 19 && track.getRedPawns().get(i) <= 24)
						players.get(i).getPopeTokens()[2].setActive(true);

					else
						players.get(i).getPopeTokens()[2].setDiscarded(true);

					break;

				default:
					System.out.println("vaticanReport: error");
			}
		}

		setChanged();
		notifyObservers(new VaticanReportMessage(popeBoxNumber, players));
	}

	public void discardResources(int discardedResNum, String playerWhoDiscarded)
	{
		setChanged();
		notifyObservers(new DiscardedResourcesMessage(discardedResNum, playerWhoDiscarded));
	}

	public boolean isMatchOver()
	{
		for (int i = 0; i < numPlayers; i++)        /* Check for every player */
		{
			if (track.getRedPawns().get(i) >= 24)
				return true;

			if (players.get(i).getDashboard().getTotalDevCardsNum() >= 7)
				return true;
		}

		return false;
	}

	public String isSinglePlayerMatchLost()		/* Returns a string if the player has lost, otherwise null */
	{
		List<List<DevCard>> devCardStacks = devCardsMarket.getDevCardStacks();
		/* Match over if all "color" devcards are gone. wow */
		if ((devCardStacks.get(0).isEmpty() && devCardStacks.get(1).isEmpty()  && devCardStacks.get(2).isEmpty()))
			return "You lose! There are no more green dev cards in the market.";

		if ((devCardStacks.get(3).isEmpty() && devCardStacks.get(4).isEmpty()  && devCardStacks.get(5).isEmpty()))
			return "You lose! There are no more blue dev cards in the market.";

		if ((devCardStacks.get(6).isEmpty() && devCardStacks.get(7).isEmpty()  && devCardStacks.get(8).isEmpty()))
			return "You lose! There are no more purple dev cards in the market.";

		if ((devCardStacks.get(9).isEmpty() && devCardStacks.get(10).isEmpty() && devCardStacks.get(11).isEmpty()))
			return "You lose! There are no more yellow dev cards in the market.";

		if (track.getBlackPawn() >= 24)
			return "You lose! Lorenzo has reached the end of the Faith Track.";

		return null;
	}

	public int calculatePoints(Player player)		/* Gets total points for a player at the end of the match */
	{												/* Should this method go here? in Player? */
		int points = 0;

		List<LeaderCard> leaderCards = player.getLeaderCards();

		for (int i = 0; i < leaderCards.size(); i++)		/* Points from leadercards */
		{
			if (leaderCards.get(i).isActive())
				points += leaderCards.get(i).getPoints();
		}

		List<DevCard> devCards = player.getDashboard().getAllDevCards();

		for (int i = 0; i < devCards.size(); i++)			/* Points from devcards */
			points += devCards.get(i).getPoints();

		int totalResources = 0;
		totalResources += player.getDashboard().getVault().getTotalResources();		/* Points from resources */
		totalResources += player.getDashboard().getStorage().getTotalResources();

		points += totalResources / 5;	/* 1 point every 5 resources, dividing integers only keeps the whole part of the number */

		points += player.getPopeTokenPoints();		/* Points from activated popeTokens */

		points += track.getFaithTrack()[track.getRedPawns().get(player.getId())].getVictoryPoints();	/* Points if the player's pawn is on a POINTS box */

		return points;
	}

	public void endMatch()		/* Pass reason why the match is over from isMatchOver */
	{
		String winnerName = "";
		int winnerPoints = 0;

		for (int i = 0; i < numPlayers; i++)		/* Calculate victoryPoints for every player */
		{
			int victoryPoints = calculatePoints(players.get(i));
			players.get(i).setVictoryPoints(victoryPoints);

			if (victoryPoints > winnerPoints)
			{
				winnerPoints = victoryPoints;
				winnerName = players.get(i).getUsername();
			}
		}
		/* Send MatchOverMessage to everyone (notifyObservers) including a message of who won */
		setChanged();
		notifyObservers(new MatchOverMessage(winnerName, players));
	}

	public Player getPlayerByUsername(String username)		/* 	NullPointerException because the usernames taken in ServerListener are discarded when a new model is created in Match */
	{
		for (int i = 0; i < numPlayers; i++)
		{
			if (players.get(i).getUsername().equals(username))
				return players.get(i);
		}

		System.out.println("getPlayerByUsername: returning null");
		return null;
	}

	public Player getPlayerByID(int ID)
	{
		for (int i = 0; i < numPlayers; i++)
		{
			if (players.get(i).getId() == ID)
				return players.get(i);
		}

		System.out.println("getPlayerByID: returning null");
		return null;
	}

	public String getCurrentPlayerName()
	{
		for (int i = 0; i < numPlayers; i++)
		{
			if (players.get(i).isMyTurn())
				return players.get(i).getUsername();
		}

		return null;
	}

	public int getNumPlayers()
	{
		return numPlayers;
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack (Track track)
	{
		this.track = track;
	}

	public MarblesMarket getMarblesMarket()
	{
		return marblesMarket;
	}

	public DevCardsMarket getDevCardsMarket()
	{
		return devCardsMarket;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public void setPlayers(List<Player> players)
	{
		this.players = players;
	}

	public List<LeaderCard> getAllLeaderCards()
	{
		return allLeaderCards;
	}

	public ActionToken getNextActionToken()		/* This method un-flips the current flipped token, flips the next one and returns it */
	{
		boolean noFlippedTokens = true;
		int flippedTokenPos = 0, tokenToFlipPos;		/* Both are [0, 5] */

		for (int i = 0; i < actionTokens.size(); i++)		/* Checks if there is at least 1 flipped token. yes -> return the next one, no -> return 1 random */
		{
			if (actionTokens.get(i).isFlipped())
			{
				noFlippedTokens = false;
				flippedTokenPos = i;
				actionTokens.get(i).setFlipped(false);		/* There can't be multiple tokens flipped at the same time */
			}
		}

		System.out.println("getNextActionToken: noFlippedTokens = " + noFlippedTokens + ", flippedTokenPos = " + flippedTokenPos);

		if (noFlippedTokens)		/* If all goes well, this happens only at the first round */
			tokenToFlipPos = 0;		/* No need to use random, tokens are already shuffled in createActionTokens() */

		else
		{
			if (flippedTokenPos == actionTokens.size() - 1)		/* 6 ActionTokens, 0-indexed so the position of the last token is 5 = 6 - 1 */
				tokenToFlipPos = 0;

			else
				tokenToFlipPos = flippedTokenPos + 1;
		}

		System.out.println("getNextActionToken: returning token at position " + tokenToFlipPos);
		actionTokens.get(tokenToFlipPos).setFlipped(true);
		return actionTokens.get(tokenToFlipPos);
	}
}
