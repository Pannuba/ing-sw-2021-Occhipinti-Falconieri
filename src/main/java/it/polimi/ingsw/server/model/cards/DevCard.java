package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;

public class DevCard
{
	private int cardNumber;			/* To identify cards. If a devcard is bought from the market, the list size changes so it's impossible to order cards based on their index */
	private DevCardColor color;
	private int points;
	private int level;
	private Resource[] cost = new Resource[3];				/* At the top of each card */
	private Resource[] requirements = new Resource[2];		/* Left side		TODO: switch to List */
	private Resource[] product = new Resource[3];			/* Right side */

	public int getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(int cardNumber)
	{
		this.cardNumber = cardNumber;
	}

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
