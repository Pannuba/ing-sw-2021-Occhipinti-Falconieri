package model;

public class MarblesMarket
{
	private Resource marblesBoard[][] = new Resource[3][4];		/* Why not string? */
	private Resource spareMarble;

	public Resource[][] getMarblesBoard()
	{
		return marblesBoard;
	}

	public void setMarblesBoard(Resource[][] marblesBoard)
	{
		this.marblesBoard = marblesBoard;
	}

	public Resource getSpareMarble()
	{
		return spareMarble;
	}

	public void setSpareMarble(Resource spareMarble)
	{
		this.spareMarble = spareMarble;
	}
}
