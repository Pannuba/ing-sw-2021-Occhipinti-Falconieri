package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.*;

import it.polimi.ingsw.server.messages.DiscardedResourcesMessage;
import it.polimi.ingsw.server.messages.MatchOverMessage;
import it.polimi.ingsw.server.messages.OperationResultMessage;
import it.polimi.ingsw.server.messages.VaticanReportMessage;
import it.polimi.ingsw.util.XML_Serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Main model class. Contains all the information of a Match (players and their dashboard, markets, track, action tokens...)
 * Created by Match in server
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class Model extends Observable        /* Observed by the views to create the new gamestate */
{
	private int numPlayers;
	private boolean matchOver;
	private Track track;
	private MarblesMarket marblesMarket;
	private DevCardsMarket devCardsMarket;
	private List<Player> players;
	private List<LeaderCard> allLeaderCards;		/* All 16 leadercards, each player picks 2 out of 4 */
	private List<ActionToken> actionTokens;

	/**
	 * Empty constructor required by XMLEncoder for the "persistence" advanced feature
	 */

	public Model()
	{

	}

	/**
	 * Constructor
	 * @param players the list of player taking part in the match
	 */

	public Model(List<Player> players)
	{
		System.out.println("Creating model...");
		this.players = players;
		numPlayers = players.size();

		createLeaderCards();
		choosePlayerOrder();

		track = new Track(players);
		marblesMarket = new MarblesMarket();
		marblesMarket.create();
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();

		matchOver = false;

		if (numPlayers == 1)
			createActionTokens();
	}

	/**
	 * Creates the new game state and sends it to the ClientHandlers through the observer-observable pattern, which send it to the clients through sockets
	 */

	public void update()
	{
		System.out.println("Updating gamestate...");
		setChanged();
		notifyObservers(new GameState(players, getCurrentPlayerName(), track, marblesMarket, devCardsMarket));
	}

	/**
	 * Creates a list of 16 leadercards by deserializing the xml files
	 */

	private void createLeaderCards()
	{
		System.out.println("Creating leadercards...");

		allLeaderCards = new ArrayList<>();
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

	/**
	 * Randomly chooses the players' round order
	 */

	private void choosePlayerOrder()		/* IDs go from 0 to 3 */
	{
		System.out.println("Choosing a random first player...");

		Collections.shuffle(players);		/* Randomize player list */

		players.get(0).setMyTurn(true);		/* Set active turn for player with ID 0 */

		for (int i = 0; i < numPlayers; i++)
			players.get(i).setId(i);

		for (int i = 0; i < numPlayers; i++)
			System.out.println(players.get(i).getUsername() + " has ID " + players.get(i).getId() + " and isFirstTurn: " + players.get(i).isMyTurn());
	}

	/**
	 * Creates four lists of four leader cards each, one for each player from which they can choose two cards
	 * @return the list of lists of leader cards
	 */

	public List<List<LeaderCard>> createLeaderCardsLists()
	{
		List<List<LeaderCard>> listOfLists = new ArrayList<>();

		Collections.shuffle(allLeaderCards);

		int i = 0;

		for (int j = 0; j < numPlayers; j++)
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

	/**
	 * If it's a single player game, create a list of seven action tokens
	 */

	public void createActionTokens()
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

	/**
	 * Changes every player's pope token (the one close to the passed popeBoxNumber) relative to the player's pawn position (flips/discards it)
	 * Then sends a VaticanReportMessage to every view
	 * Called when a player reaches a pope box. The check is performed by the controller after each round, which calls checkVaticanReport in Track
	 * @param popeBoxNumber the pope box number on which the vatican report has happened
	 */

	public void vaticanReport(int popeBoxNumber)
	{
		System.out.println("Calling vatican report for pope box " + popeBoxNumber);

		for (int i = 0; i < numPlayers; i++)
		{
			switch (popeBoxNumber)
			{
				case 8:				/* First pope box has been reached */

					track.getPopeTokens()[0].setDiscarded(true);		/* Discard the track's popeToken so no more vatican reports can be called for the same box */

					if (track.getRedPawns().get(i) >= 5)		/* If a player is inside the perimeter */
						players.get(i).getPopeTokens()[0].setActive(true);

					else
						players.get(i).getPopeTokens()[0].setDiscarded(true);

					break;

				case 16:        	/* Second pope box */

					track.getPopeTokens()[1].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 12)
						players.get(i).getPopeTokens()[1].setActive(true);

					else
						players.get(i).getPopeTokens()[1].setDiscarded(true);

					break;

				case 24:			/* Third pope box */

					track.getPopeTokens()[2].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 19)
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

	/**
	 * Sends a DiscardedResourcesMessage to every view containing discardedResNum and playerWhoDiscarded
	 * @param discardedResNum the number of discarded resources
	 * @param playerWhoDiscarded the username of the player who discarded the resources
	 */

	public void discardResources(int discardedResNum, String playerWhoDiscarded)
	{
		setChanged();
		notifyObservers(new DiscardedResourcesMessage(discardedResNum, playerWhoDiscarded));
	}

	/**
	 * If the match is currently not over, check if it is. Otherwise do nothing. Called by the controller after every round
	 */

	public void checkMatchOver()
	{
		String message = "";

		if (!matchOver)
		{
			for (int i = 0; i < numPlayers; i++)
			{
				if (track.getRedPawns().get(i) >= 24)
				{
					message = getPlayerByID(i).getUsername() + " has reached the end of the Faith Track!";
					matchOver = true;
				}

				if (players.get(i).getDashboard().getTotalDevCardsNum() >= 7)
				{
					message = getPlayerByID(i).getUsername() + " has bought their seventh dev card!";
					matchOver = true;
				}
			}

			if (!message.isEmpty())		/* If an end condition has verified, send the message to everyone */
			{
				message += "\nThe match will end when " + getPlayerByID(numPlayers - 1).getUsername() + " finishes their turn!";
				setChanged();
				notifyObservers(new OperationResultMessage(message, false));	/* isFailed = false, no need to create a new message type */
			}
		}
	}

	/**
	 * Called by the controller after each round, if it's a single player game
	 * @return the string containing the message why the player has lost, otherwise null
	 */
	public String isSinglePlayerMatchLost()
	{
		List<List<DevCard>> devCardStacks = devCardsMarket.getDevCardStacks();

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

	/**
	 * Gets total points for a player at the end of the match
	 * @param player the player for which the total points will be calculated
	 * @return the amount of victory points of the passed player
	 */

	public int calculatePoints(Player player)
	{
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

	/**
	 * Calculate the victory points for all players and send a MatchOverMessage to all views including the winner's username
	 * Called by the controller after the match has ended.
	 */

	public void endMatch()
	{
		String winnerName = "";
		int winnerPoints = 0;
		List<Integer> victoryPoints = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++)		/* Calculate victoryPoints for every player */
		{
			victoryPoints.add(calculatePoints(players.get(i)));
			players.get(i).setVictoryPoints(victoryPoints.get(i));

			if (victoryPoints.get(i) > winnerPoints)
			{
				winnerPoints = victoryPoints.get(i);
				winnerName = players.get(i).getUsername();
			}
		}

		Collections.sort(victoryPoints);		/* Sort the list to make eventual equal values adjacent, and in case of multiple ties the last check is performed on the biggest one */

		for (int i = 0; i < (victoryPoints.size() - 1); i++)	/* -1 to avoid NullPointerException when accessing list element 4 (3 + 1). Also skips singleplayer */
		{
			if (victoryPoints.get(i).equals(victoryPoints.get(i + 1)))		/* If two scores are the same, the winner is the one with the most resources */
			{
				if (players.get(i).getTotalResources() < players.get(i + 1).getTotalResources())
					winnerName = players.get(i + 1).getUsername();

				if (players.get(i).getTotalResources() == players.get(i + 1).getTotalResources())
					winnerName = "";    /* Tie */
			}
		}

		System.out.println("endGame: winnerName = " + winnerName);
		setChanged();
		notifyObservers(new MatchOverMessage(winnerName, players));
	}

	/**
	 * Un-flips the current flipped action token, flips the next one and returns it. Called by the controller after every round, if it's a single player game
	 * @return the next flipped action token
	 */

	public ActionToken getNextActionToken()
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

	public void setNumPlayers(int numPlayers)		/* For the persistence feature */
	{
		this.numPlayers = numPlayers;
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

	public void setMarblesMarket(MarblesMarket marblesMarket)
	{
		this.marblesMarket = marblesMarket;
	}

	public DevCardsMarket getDevCardsMarket()
	{
		return devCardsMarket;
	}

	public void setDevCardsMarket (DevCardsMarket devCardsMarket)
	{
		this.devCardsMarket = devCardsMarket;
	}

	public List<Player> getPlayers()
	{
		return players;
	}

	public void setPlayers(List<Player> players)
	{
		this.players = players;
	}

	public boolean isMatchOver()
	{
		return matchOver;
	}

	public List<LeaderCard> getAllLeaderCards()
	{
		return allLeaderCards;
	}

	public List<ActionToken> getActionTokens()
	{
		return actionTokens;
	}

	public void setActionTokens(List<ActionToken> actionTokens)
	{
		this.actionTokens = actionTokens;
	}
}
