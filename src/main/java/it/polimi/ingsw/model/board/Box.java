package it.polimi.ingsw.model.board;

import java.io.Serializable;

public class Box implements Serializable
{
	private final BoxType boxType;
	private final int position;
	private final int victoryPoints;

	public Box(BoxType boxType, int position, int victoryPoints)
	{
		this.boxType = boxType;
		this.position = position;
		this.victoryPoints = victoryPoints;
	}

	public BoxType getBoxType()
	{
		return boxType;
	}

	public int getPosition()
	{
		return position;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}
}
