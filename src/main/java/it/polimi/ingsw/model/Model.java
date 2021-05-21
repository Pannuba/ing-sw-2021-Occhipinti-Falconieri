package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import it.polimi.ingsw.util.XML_Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class Model extends Observable		/* Observed by the views to create the new gamestate */
{
	private final int numPlayers;
	private final Track track;
	private final MarblesMarket marblesMarket;
	private final DevCardsMarket devCardsMarket;
	private List<Player> players;
	private List<LeaderCard> allLeaderCards;		/* All 16 leadercards, each player picks 2 out of 4 */

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
	}

	public void update()			/* Creates the new gamestate and sends it to the views, which send it to the clients */
	{
		System.out.println("Updating gamestate...");

		/* TODO: add message from server to gamestate with the result of every command, for example what resources were bought */
		setChanged();
		notifyObservers(new GameState(players, getCurrentPlayerName(), track, marblesMarket, devCardsMarket));
	}

	private void createLeaderCards()
	{
		System.out.println("Model: creating leadercards...");
		allLeaderCards = new ArrayList<>();		/* Necessary? */
		LeaderCard cardToAdd = null;

		for (int i = 0; i < 16; i++)
		{
			try
			{
				cardToAdd = (LeaderCard) XML_Serialization.deserialize("src/main/resources/xml/leadercards/leadercard" + (i + 1) + ".xml");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			allLeaderCards.add(cardToAdd);
		}
	}

	private void choosePlayerOrder()		/* IDs go from 0 to 3. Need to test if it's actually random */
	{
		System.out.println("Choosing a random first player...");

		List<Player> playersSortedByID = new ArrayList<>();

		int firstPlayer = ThreadLocalRandom.current().nextInt(0, numPlayers);		/* Not numPlayers + 1 because it's zero-indexed */
		players.get(firstPlayer).setMyTurn(true);
		players.get(firstPlayer).setId(0);

		playersSortedByID.add(players.get(firstPlayer));

		if (numPlayers == 2)
		{
			for (int i = 0; i < 2; i++)
			{
				if (!players.get(i).isMyTurn())
				{
					players.get(i).setId(1);
					playersSortedByID.add(players.get(i));
				}
			}
		}

		else
		{
			int[] playerNumbers = new int[numPlayers - 1];

			for (int i = 0; i < numPlayers - 1; i++)
				playerNumbers[i] = 1;

			for (int i = 0; i < numPlayers - 1; i++)
			{
				int randNum = ThreadLocalRandom.current().nextInt(0, numPlayers);

				if (playerNumbers[randNum] != 1 || players.get(randNum).getId() == 0)
					i--;

				else
				{
					players.get(randNum).setId(randNum);
					playersSortedByID.add(players.get(randNum));
					playerNumbers[i]--;
				}
			}
		}

		setPlayers(playersSortedByID);

		for (int i = 0; i < numPlayers; i++)
			System.out.println(players.get(i).getUsername() + " has ID " + players.get(i).getId() + " and isFirstTurn: " + players.get(i).isMyTurn());
	}

	public List<List<LeaderCard>> createLeaderCardsLists()				/* Returns a list of lists of leadercards, 1 for each player, each list has 4 leadercards to choose 2 from */
	{
		int[] cardsToAssign = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		List<List<LeaderCard>> listOfLists = new ArrayList<>();
		List<LeaderCard> tempList;

		for (int i = 0; i < numPlayers; i++)
		{
			tempList = new ArrayList<>();		/* Resets the temp list so we don't end up with a 16-element list */

			for (int j = 0; j < 4; j++)            /* TODO: test!!! */
			{
				int randNum = ThreadLocalRandom.current().nextInt(0, 15 + 1);

				if (cardsToAssign[randNum] != 1)    /* If the leaderCard has already been picked, skip the loop and reset the counter (i) */
					j--;

				else
				{
					tempList.add(allLeaderCards.get(randNum));
					cardsToAssign[randNum]--;
				}
			}

			listOfLists.add(tempList);
		}

		return listOfLists;
	}

	public void vaticanReport(int popeBoxNumber)		/* Called when a player reaches a pope box. Called by match? */
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

					break;

				case 16:        	/* Second pope box */

					track.getPopeTokens()[1].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 12 && track.getRedPawns().get(i) <= 16)
						players.get(i).getPopeTokens()[1].setActive(true);

					break;

				case 24:			/* Third pope box */

					track.getPopeTokens()[2].setDiscarded(true);

					if (track.getRedPawns().get(i) >= 19 && track.getRedPawns().get(i) <= 24)
						players.get(i).getPopeTokens()[2].setActive(true);

					break;

				default:
					System.out.println("Error");
			}
		}
	}

	public boolean isMatchOver()
	{
		for (int i = 0; i < numPlayers; i++)		/* Check for every player */
		{
			if (track.getRedPawns().get(i) >= 24 || players.get(i).getDashboard().getTotalDevCardsNum() == 7)
				return true;
		}

		return false;
	}

	public int calculatePoints(Player player)		/* Gets total points for a player at the end of the match */
	{												/* Should this method go here? in Player? */
		int points = 0;

		List<LeaderCard> leaderCards = player.getLeaderCards();

		for (int i = 0; i < leaderCards.size(); i++)		/* Points from leadercards */
		{
			if (leaderCards.get(i).isDiscarded() == false)		/* Discarded vs activated? */
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

		return points;
	}

	public void endMatch()
	{
		for (int i = 0; i < numPlayers; i++)
		{
			players.get(i).setVictoryPoints(calculatePoints(players.get(i)));
		}
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
}
