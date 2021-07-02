package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * Single track box
 * @author Giulio Occhipinti
 */

public class Box implements Serializable
{
	private final BoxType boxType;
	private final int victoryPoints;

	/**
	 * Constructor
	 * @param boxType to distinguish if it is a pope, normal or points box
	 * @param victoryPoints for points and pope box, used to calculate the points at the end of the game
	 */

	public Box(BoxType boxType, int victoryPoints)
	{
		this.boxType = boxType;
		this.victoryPoints = victoryPoints;
	}

	public BoxType getBoxType()
	{
		return boxType;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}
}
