package model.board;

public class Box
{
	private BoxType category;
	private int position;
	private int victoryPoints;

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
