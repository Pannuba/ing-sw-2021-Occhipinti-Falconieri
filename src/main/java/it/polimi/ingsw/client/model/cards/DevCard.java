package it.polimi.ingsw.client.model.cards;

import it.polimi.ingsw.client.model.Resource;

public class DevCard
{
	private int cardNumber;
	private DevCardColor color;
	private int points;
	private int level;
	private Resource[] cost = new Resource[3];
	private Resource[] requirements = new Resource[2];
	private Resource[] product = new Resource[3];

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
