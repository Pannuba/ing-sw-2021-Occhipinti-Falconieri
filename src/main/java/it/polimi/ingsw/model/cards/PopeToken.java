package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public class PopeToken implements Serializable
{
	private int points;
	private boolean isUsed;

	public PopeToken(int points)
	{
		System.out.println("PopeToken: creating token with " + points + " points");
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
