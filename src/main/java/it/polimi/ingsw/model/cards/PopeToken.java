package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public class PopeToken implements Serializable
{
	private int points;
	private boolean isActive;			/* True when a player is inside the perimeter when a vatican report is called */
	private boolean isDiscarded;		/* True when a player is outside the perimeter or when the vatican report for that token is called */

	public PopeToken(int points)
	{
		System.out.println("PopeToken: creating token with " + points + " points");
		this.points = points;

		isActive = false;
		isDiscarded = false;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public boolean isDiscarded()
	{
		return isDiscarded;
	}

	public void setDiscarded(boolean discarded)
	{
		isDiscarded = discarded;
	}
}
