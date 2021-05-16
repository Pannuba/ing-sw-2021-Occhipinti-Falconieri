package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import it.polimi.ingsw.tools.XML_Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class Model extends Observable		/* Observed by the views to create the new gamestate */
{
	private int numPlayers;
	private int activePlayerID;
	private Track track;
	private MarblesMarket marblesMarket;
	private DevCardsMarket devCardsMarket;
	private List<Player> players = new ArrayList<Player>();
	private List<LeaderCard> allLeaderCards = new ArrayList<LeaderCard>();		/* All 16 leadercards, each player picks 2 out of 4 */

	public Model(List<Player> players)
	{
		System.out.println("Creating model...");
		this.players = players;
		numPlayers = players.size();

		createLeaderCards();
		choosePlayerOrder();

		track = new Track(numPlayers);
		marblesMarket = new MarblesMarket();
		devCardsMarket = new DevCardsMarket();
	}

	public void update()			/* Creates the new gamestate and sends it to the views, which send it to the clients */
	{
		System.out.println("Updating gamestate...");
		System.out.println("In this gamestate, " + players.get(0).getUsername() + "has leadercards #" + players.get(0).getLeaderCards().get(0).getCardNumber() + " and #" + players.get(0).getLeaderCards().get(1).getCardNumber());
		System.out.println("In this gamestate, " + players.get(1).getUsername() + "has leadercards #" + players.get(1).getLeaderCards().get(0).getCardNumber() + " and #" + players.get(1).getLeaderCards().get(1).getCardNumber());
		/* Checks out... so it's a client-side bug? */
		setChanged();
		notifyObservers(new GameState(players, track, marblesMarket, devCardsMarket));
	}

	private void createLeaderCards()
	{
		System.out.println("Model: creating leadercards...");
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

			cardToAdd.setCardNumber(i + 1);		/* Here or in xmls? */
			allLeaderCards.add(cardToAdd);
		}
	}

	private void choosePlayerOrder()		/* Need to test if it's actually random */
	{
		System.out.println("Choosing a random first player...");

		int firstPlayer = ThreadLocalRandom.current().nextInt(0, numPlayers);		/* Not numPlayers + 1 because it's zero-indexed */
		players.get(firstPlayer).setMyTurn(true);
		players.get(firstPlayer).setId(0);
		activePlayerID = 0;

		if (numPlayers == 2)
		{
			for (int i = 0; i < 2; i++)
			{
				if (!players.get(i).isMyTurn())
					players.get(i).setId(1);
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
					playerNumbers[i]--;
				}
			}
		}

		for (int i = 0; i < numPlayers; i++)
			System.out.println(players.get(i).getUsername() + " has ID " + players.get(i).getId() + " and isFirstTurn: " + players.get(i).isMyTurn());
	}

	public List<List<LeaderCard>> createLeaderCardsLists()				/* Returns a list of lists of leadercards, 1 for each player, each list has 4 leadercards to choose from */
	{
		int[] cardsToAssign = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		List<List<LeaderCard>> listOfLists = new ArrayList<List<LeaderCard>>();
		List<LeaderCard> tempList;

		for (int i = 0; i < numPlayers; i++)
		{
			tempList = new ArrayList<LeaderCard>();		/* Resets the temp list so we don't end up with a 16-element list */

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

	public int calculatePoints(Player player)		/* Gets total points for a player and sets them */
	{												/* Should this method go here? in Player? */
		int points = 0;

		List<DevCard> devcards = player.getDevCards();
		List<LeaderCard> leadercards = player.getLeaderCards();
		int totalResources = 0;

		for (int i = 0; i < devcards.size(); i++)		/* Points from devcards */
			points += devcards.get(i).getPoints();

		for (int i = 0; i < leadercards.size(); i++)		/* Points from leadercards */
		{
			if (leadercards.get(i).isDiscarded() == false)
				points += leadercards.get(i).getPoints();
		}

		totalResources += player.getDashboard().getVault().getTotalResources();		/* Points from resources */
		totalResources += player.getDashboard().getStorage().getTotalResources();

		points += totalResources / 5;	/* 1 point every 5 resources, dividing integers only keeps the whole part of the number */

		/* Need to calculate popetokens points, but where should vaticanReport be called? */

		return points;
	}

	public Player getPlayerByUsername(String username)		/* 	NullPointerException because  the usernames taken in ServerListener are discarded when a new model is created in Match */
	{
		for (int i = 0; i < numPlayers; i++)
		{
			if (players.get(i).getUsername().equals(username))
				return players.get(i);
		}

		System.out.println("getPlayerByUsername: returning null");
		return null;
	}

	public int getNumPlayers()
	{
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers)
	{
		this.numPlayers = numPlayers;
	}

	public int getActivePlayerID()
	{
		return activePlayerID;
	}

	public void setActivePlayerID(int activePlayerID)
	{
		this.activePlayerID = activePlayerID;
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack(Track track)
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

	public void setDevCardsMarket(DevCardsMarket devCardsMarket)
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

	public List<LeaderCard> getAllLeaderCards()
	{
		return allLeaderCards;
	}

	public void setAllLeaderCards(List<LeaderCard> allLeaderCards)
	{
		this.allLeaderCards = allLeaderCards;
	}
}
