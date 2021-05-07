package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class DevCard
{
	private int cardNumber;			/* To identify cards. If a devcard is bought from the market, the list size changes so it's impossible to order cards based on their index */
	private DevCardColor color;
	private int points;
	private int level;
	private int faithPoints;
	private List<Resource> cost;				/* At the top of each card */
	private List<Resource> requirements;		/* Left side */
	private List<Resource> product;			/* Right side */

	public DevCard()
	{
		faithPoints = 0;
		cost = new ArrayList<Resource>();
		requirements = new ArrayList<Resource>();
		product = new ArrayList<Resource>();
	}

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

	public int getFaithPoints()
	{
		return faithPoints;
	}

	public void setFaithPoints(int faithPoints)
	{
		this.faithPoints = faithPoints;
	}

	public List<Resource> getCost()
	{
		return cost;
	}

	public void setCost(List<Resource> cost)
	{
		this.cost = cost;
	}

	public List<Resource> getRequirements()
	{
		return requirements;
	}

	public void setRequirements(List<Resource> requirements)
	{
		this.requirements = requirements;
	}

	public List<Resource> getProduct()
	{
		return product;
	}

	public void setProduct(List<Resource> product)
	{
		this.product = product;
	}
}
