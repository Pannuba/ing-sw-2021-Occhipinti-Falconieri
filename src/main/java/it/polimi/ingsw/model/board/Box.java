package it.polimi.ingsw.model.board;

import java.io.Serializable;

public class Box implements Serializable
{
	private final BoxType boxType;
	private final int victoryPoints;

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
