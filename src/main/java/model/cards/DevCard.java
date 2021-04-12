package model.cards;

import model.board.DevCardArea;
import model.Resource;

public class DevCard // extends DevCardArea
{
	private DevCardColor color;			/* Blue = 0, Yellow = 1, Purple = 2, Green = 3. */
	private int points;
	private int level;
	private Resource[] cost = new Resource[3];				/* At the top of each card */
	private Resource[] requirements = new Resource[2];		/* Left side */
	private Resource[] product = new Resource[3];			/* Right side */

	public DevCardColor getColor()
	{
		return color;
	}

	public void setColor(DevCardColor color)
	{
		this.color = color;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public Resource[] getCost()
	{
		return cost;
	}

	public void setCost(Resource[] cost)
	{
		this.cost = cost;
	}

	public Resource[] getRequirements()
	{
		return requirements;
	}

	public void setRequirements(Resource[] requirements)
	{
		this.requirements = requirements;
	}

	public Resource[] getProduct()
	{
		return product;
	}

	public void setProduct(Resource[] product)
	{
		this.product = product;
	}
}
