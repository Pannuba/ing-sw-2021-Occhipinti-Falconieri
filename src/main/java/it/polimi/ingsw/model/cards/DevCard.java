package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Development cards, the parameters are taken from the xml files
 * @author Giulio Occhipinti
 */

public class DevCard implements Serializable
{
	private int cardNumber;			/* To identify cards. If a devcard is bought from the market, the list size changes so it's impossible to order cards based on their index */
	private DevCardColor color;
	private int points;
	private int level;
	private boolean usedForProduction;			/* To prevent clients from using the same card more than once in the same round */
	private List<Resource> requirements;		/* At the top of each card */
	private List<Resource> cost;				/* Left side */
	private List<Resource> product;				/* Right side. faithpoints are here */

	/**
	 * Constructor. Creates a dev card
	 */

	public DevCard()
	{
		usedForProduction = false;

		requirements = new ArrayList<>();
		cost = new ArrayList<>();
		product = new ArrayList<>();
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

	public boolean isUsedForProduction()
	{
		return usedForProduction;
	}

	public void setUsedForProduction(boolean usedForProduction)
	{
		this.usedForProduction = usedForProduction;
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
