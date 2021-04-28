package it.polimi.ingsw.client.model;

public class MarblesMarket
{
	private Marble[][] marblesBoard = new Marble[3][4];
	private Marble spareMarble;

	public Marble[][] getMarblesBoard()
	{
		return marblesBoard;
	}

	public void setMarblesBoard(Marble[][] marblesBoard)
	{
		this.marblesBoard = marblesBoard;
	}

	public Marble getSpareMarble()
	{
		return spareMarble;
	}

	public void setSpareMarble(Marble spareMarble)
	{
		this.spareMarble = spareMarble;
	}
}
