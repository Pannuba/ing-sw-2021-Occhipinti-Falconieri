package it.polimi.ingsw.client.localmatch;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.DevCardsMarket;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.*;

import it.polimi.ingsw.server.messages.DiscardedResourcesMessage;
import it.polimi.ingsw.server.messages.MatchOverMessage;
import it.polimi.ingsw.util.XML_Serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * This class is a clone of Model (in the server package) with modifications to only take in account one player. For example,
 * the players list is now a single Player variable and the choosePlayerOrder method and many for loops that iterate for all players have been removed.
 * @author Giulio Occhipinti
 */

public class LocalModel extends Observable        /* Observed by the views to create the new gamestate */
{
	private final View view;
	private Track track;
	private final MarblesMarket marblesMarket;
	private DevCardsMarket devCardsMarket;
	private final Player player;
	private List<LeaderCard> allLeaderCards;		/* All 16 leadercards, each player picks 2 out of 4 */
	private List<ActionToken> actionTokens;

	public LocalModel(Player player, View view)
	{
		System.out.println("Creating model...");
		this.player = player;
		player.setMyTurn(true);
		this.view = view;

		List<Player> playerInList = new ArrayList<>();
		playerInList.add(player);

		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		track = new Track(playerInList);
		marblesMarket = new MarblesMarket();
		marblesMarket.create();

		createLeaderCards();
		createActionTokens();
	}

	public void update()			/* Creates the new gamestate and sends it to the views, which send it to the clients */
	{
		System.out.println("Updating gamestate...");
		List<Player> playerAsList = new ArrayList<>();
		playerAsList.add(player);
		view.update(new GameState(playerAsList, player.getUsername(), track, marblesMarket, devCardsMarket));
	}

	private void createLeaderCards()
	{
		System.out.println("Creating leadercards...");

		allLeaderCards = new ArrayList<>();		/* Necessary? YES */
		LeaderCard cardToAdd = null;

		for (int i = 0; i < 16; i++)
		{
			try
			{
				cardToAdd = (LeaderCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/leadercards/leadercard" + (i + 1) + ".xml"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			allLeaderCards.add(cardToAdd);
		}
	}

	public List<List<LeaderCard>> createLeaderCardsLists()				/* Returns a list of lists of leadercards, 1 for each player, each list has 4 leadercards to choose 2 from */
	{
		List<List<LeaderCard>> listOfLists = new ArrayList<>();

		Collections.shuffle(allLeaderCards);

		int i = 0;

		for (int j = 0; j < 4; j++)        /* Create numPlayer lists of 4 cards each */
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
		actionTokens.add(new ActionBlack2(track));

		Collections.shuffle(actionTokens);
	}

	public void vaticanReport(int popeBoxNumber)		/* Called when a player reaches a pope box. Put in controller? */
	{
		System.out.println("Calling vatican report for pope box " + popeBoxNumber);

			switch (popeBoxNumber)
			{
				case 8:				/* First pope box has been reached */

				track.getPopeTokens()[0].setDiscarded(true);		/* Discard the track's popeToken so no more vatican reports can be called for the same box */

				if (track.getRedPawns().get(0) >= 5)		/* If a player is inside the perimeter */
					player.getPopeTokens()[0].setActive(true);

				else
					player.getPopeTokens()[0].setDiscarded(true);

				break;

			case 16:        	/* Second pope box */

				track.getPopeTokens()[1].setDiscarded(true);

				if (track.getRedPawns().get(0) >= 12)
					player.getPopeTokens()[1].setActive(true);

				else
					player.getPopeTokens()[1].setDiscarded(true);

				break;

			case 24:			/* Third pope box */

				track.getPopeTokens()[2].setDiscarded(true);

				if (track.getRedPawns().get(0) >= 19)
					player.getPopeTokens()[2].setActive(true);

				else
					player.getPopeTokens()[2].setDiscarded(true);

				break;

			default:
				System.out.println("vaticanReport: error");
		}

		setChanged();
		//notifyObservers(new VaticanReportMessage(popeBoxNumber, players));
	}

	public void discardResources(int discardedResNum, String playerWhoDiscarded)
	{
		view.update(new DiscardedResourcesMessage(discardedResNum, playerWhoDiscarded));
	}

	public boolean isMatchOver()
	{
		String message = "";

		if (track.getRedPawns().get(0) >= 24)
			return true;
			//message = player.getUsername() + " has reached the end of the Faith Track!";

		if (player.getDashboard().getTotalDevCardsNum() >= 7)
			return true;
			//message = player.getUsername() + " has bought their seventh dev card!";

		return false;
		//view.update(new OperationResultMessage(message, false));	/* isFailed = false, no need to create a new message type */
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

		points += player.getTotalResources() / 5;	/* 1 point every 5 resources, dividing integers only keeps the whole part of the number */

		points += player.getPopeTokenPoints();		/* Points from activated popeTokens */

		/* Points if the player's pawn is on a POINTS box or after */
		switch (track.getRedPawns().get(player.getId()))
		{
			case 0:  case 1:  case 2:											break;
			case 3:  case 4:  case 5:	points += track.getFaithTrack()[3];		break;
			case 6:  case 7:  case 8:	points += track.getFaithTrack()[6];		break;
			case 9:  case 10: case 11:	points += track.getFaithTrack()[9];		break;
			case 12: case 13: case 14:	points += track.getFaithTrack()[12];	break;
			case 15: case 16: case 17:	points += track.getFaithTrack()[15];	break;
			case 18: case 19: case 20:	points += track.getFaithTrack()[18];	break;
			case 21: case 22: case 23:	points += track.getFaithTrack()[21];	break;
			default:					points += track.getFaithTrack()[24];
		}

		return points;
	}

	public void endMatch()		/* Only one player can win... */
	{
		player.setVictoryPoints(calculatePoints(player));

		/* Send MatchOverMessage to everyone (notifyObservers) including a message of who won */
		List<Player> playerInList = new ArrayList<>();
		playerInList.add(player);
		view.update(new MatchOverMessage(player.getUsername(), playerInList));		/* If the player didn't lose, they won */
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack (Track track)
	{
		this.track = track;
	}

	public Player getPlayer()
	{
		return player;
	}

	public MarblesMarket getMarblesMarket()
	{
		return marblesMarket;
	}

	public DevCardsMarket getDevCardsMarket()
	{
		return devCardsMarket;
	}

	public void setDevCardsMarket (DevCardsMarket devCardsMarket)
	{
		this.devCardsMarket = devCardsMarket;
	}

	public List<LeaderCard> getAllLeaderCards()
	{
		return allLeaderCards;
	}

	public List<ActionToken> getActionTokens()
	{
		return actionTokens;
	}

	public ActionToken getNextActionToken()		/* This method un-flips the current flipped token, flips the next one and returns it */
	{
		boolean noFlippedTokens = true;
		int flippedTokenPos = 0, tokenToFlipPos;		/* Both are [0, 6] */

		for (int i = 0; i < actionTokens.size(); i++)		/* Checks if there is at least 1 flipped token. yes -> return the next one, no -> return 1 random */
		{
			if (actionTokens.get(i).isFlipped())
			{
				noFlippedTokens = false;
				flippedTokenPos = i;
				actionTokens.get(i).setFlipped(false);		/* There can't be multiple tokens flipped at the same time */
			}
		}

		if (noFlippedTokens)		/* If all goes well, this happens only at the first round */
			tokenToFlipPos = 0;		/* No need to use random, tokens are already shuffled in createActionTokens() */

		else
		{
			if (flippedTokenPos == actionTokens.size() - 1)		/* 6 ActionTokens, 0-indexed so the position of the last token is 5 = 6 - 1 */
				tokenToFlipPos = 0;

			else
				tokenToFlipPos = flippedTokenPos + 1;
		}

		actionTokens.get(tokenToFlipPos).setFlipped(true);
		return actionTokens.get(tokenToFlipPos);
	}
}