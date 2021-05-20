package it.polimi.ingsw.model.board;

import java.io.Serializable;

public class Box implements Serializable
{
	private final BoxType category;
	private final int position;
	private final int victoryPoints;

	public Box(BoxType category, int position, int victoryPoints)
	{
		this.category = category;
		this.position = position;
		this.victoryPoints = victoryPoints;
	}

	public BoxType getCategory()
	{
		return category;
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
