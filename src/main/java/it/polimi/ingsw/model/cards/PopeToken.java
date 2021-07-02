package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/**
 * Pope tokens: used in the track
 * @author Giulio Occhipinti
 */

public class PopeToken implements Serializable
{
	private int points;
	private boolean isActive;
	private boolean isDiscarded;

	/**
	 * Empty constructor required by the persistence advanced functionality
	 */

	public PopeToken()
	{

	}

	/**
	 * Constructor. Creates a pope token
	 * @param points the points given by the pope token when it's flipped, if a player is inside the perimeter around it
	 */

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
