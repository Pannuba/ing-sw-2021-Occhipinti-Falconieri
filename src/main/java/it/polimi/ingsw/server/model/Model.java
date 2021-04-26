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
	//private Dashboard[] boards = new Dashboard[numPlayers];
	private List<Player> players = new ArrayList<Player>();
	private List<LeaderCard> leaderCards = new ArrayList<LeaderCard>();

	/* What if GameState has no instance variables, just a main methods that creates the different players? */

	public Model(int numPlayers) throws IOException    /* Pass usernames? */
	{
		this.numPlayers = numPlayers;
		/*players = new Player[numPlayers];*/

		for(int i = 0; i < numPlayers; i++)
			players.add(new Player());		/* Array of object, need "new" keyword for each array element */

		pickLeaderCards();

	}

	private void pickLeaderCards() throws IOException        /* Creates the 16 leadercards and randomly picks 4 */
	{
		XML_Serialization decoder = new XML_Serialization();
		LeaderCard cardToAdd = null;
		List<LeaderCard> allLeaderCards = new ArrayList<LeaderCard>();
		int cardsToAssign[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

		for (int i = 0; i < 16; i++)
		{
			cardToAdd = (LeaderCard)decoder.deserialize("resources/xml/leadercards/leadercard" + (i+1) + ".xml");
			allLeaderCards.add(cardToAdd);
		}

		for (int i = 0; i < 4; i++)			/* TODO: test!!! */
		{
			int randNum = ThreadLocalRandom.current().nextInt(0, 15+1);

			if (cardsToAssign[randNum] != 1)	/* If the leaderCard has already been picked, skip the loop and reset the counter (i) */
				i--;

			else
			{
				leaderCards.add(allLeaderCards.get(randNum));
				cardsToAssign[randNum]--;
			}
		}
	}

	public void calculatePoints()		/* Should this go in controller? */
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
