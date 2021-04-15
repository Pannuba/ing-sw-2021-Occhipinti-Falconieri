package model;

import model.board.Dashboard;

public class GameState		/* Remove GameState? main function in GameState? */
{
	private int numPlayers;
	//private Dashboard currPlayer;		/* ??? */
	//private Dashboard[] players = new Dashboard[numPlayers];
	private Player[] players;		/* array size is decided later, so don't put = new Player[numPlayers] */

	/* What if GameState has no instance variables, just a main methods that creates the different players? */

	public GameState(int numPlayers)
	{
		this.numPlayers = numPlayers;
		players = new Player[numPlayers];

		for(int i = 0; i < numPlayers; i++)
			players[i] = new Player();		/* Array of object, need "new" keyword for each array element */

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
