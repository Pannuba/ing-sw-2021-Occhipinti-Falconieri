package it.polimi.ingsw.model.board;

import java.io.Serializable;

public class Box implements Serializable
{
	private BoxType category;
	private int position;
	private int victoryPoints;

	public Box(BoxType category, int position, int victoryPoints)
	{
		this.category = category;
		this.position = position;
		this.victoryPoints = victoryPoints;
	}

	/* Set methods are pretty much useless, but I'll leave them anyway */

	public BoxType getCategory()
	{
		return category;
	}

	public void setCategory(BoxType category)
	{
		this.category = category;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints)
	{
		this.victoryPoints = victoryPoints;
	}
}
