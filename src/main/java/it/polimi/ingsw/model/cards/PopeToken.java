package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/**
 * Pope tokens: use in track
 * @author Giulio Occhipinti
 */

public class PopeToken implements Serializable
{
	/**
	 * @param points if the pope token is active, it gives points at the end of the game
	 * @param isActive true when a player is inside the perimeter when a vatican report is called
	 * @param isDiscarded true when a player is outside the perimeter or when the vatican report for that token is called
	 */

	private int points;
	private boolean isActive;
	private boolean isDiscarded;

	public PopeToken()
	{

	}

	public PopeToken(int points)
	{
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
