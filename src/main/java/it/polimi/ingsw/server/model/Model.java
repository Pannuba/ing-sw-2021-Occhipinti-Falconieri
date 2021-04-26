package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.Dashboard;
import it.polimi.ingsw.server.model.board.Track;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.tools.XML_Serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model
{
	private int numPlayers;
	private Track track;
	private MarblesMarket marblesMarket;
	private DevCardsMarket devCardsMarket;
	//private Dashboard[] boards = new Dashboard[numPlayers];
	private List<Player> players = new ArrayList<Player>();
	private List<LeaderCard> allLeaderCards = new ArrayList<LeaderCard>();		/* All 16 leadercards, each player picks 2 out of 4 */

	public Model(int numPlayers) throws IOException    /* Pass usernames? */
	{
		this.numPlayers = numPlayers;
		/*players = new Player[numPlayers];*/
		XML_Serialization decoder = new XML_Serialization();
		LeaderCard cardToAdd = null;

		for(int i = 0; i < numPlayers; i++)
			players.add(new Player());		/* Array of object, need "new" keyword for each array element */

		for (int i = 0; i < 16; i++)
		{
			cardToAdd = (LeaderCard)decoder.deserialize("resources/xml/leadercards/leadercard" + (i+1) + ".xml");
			allLeaderCards.add(cardToAdd);
		}

		track = new Track();
		marblesMarket = new MarblesMarket();
		devCardsMarket = new DevCardsMarket();
		//pickLeaderCards();

	}

	private List<List<LeaderCard>> createLeaderCardsLists() throws IOException		/* Returns a list of lists of leadercards, 1 for each player, each list has 4 leadercards to choose from */
	{
		int cardsToAssign[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
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

	public void calculatePoints()		/* Should this go in controller? No */
	{

	}

	public int getNumPlayer()
	{
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers)
	{
		this.numPlayers = numPlayers;
	}

	/*public Dashboard getCurrPlayer()
	{
		return currPlayer;
	}

	public void setCurrPlayer(Dashboard currPlayer)
	{
		this.currPlayer = currPlayer;
	}

	public Dashboard[] getPlayers()
	{
		return players;
	}

	public void setPlayers(Dashboard[] players)
	{
		this.players = players;
	}*/
}
