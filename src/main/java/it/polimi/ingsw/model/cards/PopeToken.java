package it.polimi.ingsw.model.cards;

public class PopeToken
{
	private int points;
	private boolean isUsed;

	public PopeToken(int points)
	{
		isUsed = false;
		this.points = points;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public boolean isUsed()
	{
		return isUsed;
	}

	public void setUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}
}
