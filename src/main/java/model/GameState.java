package model;

public class GameState
{
	private int numPlayer;
	private Dashboard currPlayer;
	private Dashboard[]players = new Dashboard[numPlayer];

	public int getNumPlayer()
	{
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer)
	{
		this.numPlayer = numPlayer;
	}

	public Dashboard getCurrPlayer()
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
	}
}
