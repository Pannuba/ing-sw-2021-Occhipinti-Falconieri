package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.util.Pair;

public class SkillProduction extends LeaderCard		/* Every card gives one faith point and one product that can be chosen */
{
	private Pair<DevCardColor, Integer> requirements;		/* Pair of	(devcard color : devcard level) */
	private Resource cost;									/* In base game, always 1 cost, 1 output resource (chosen by user), 1 faith point */
	private Resource product;
	private int faithPoints;

	public SkillProduction()		/* ResourceType of cost and product is assigned by the xmls */
	{
		cost	= new Resource();
		product	= new Resource();
	}

	public Pair<DevCardColor, Integer> getRequirements()
	{
		return requirements;
	}

	public void setRequirements(Pair<DevCardColor, Integer> requirements)
	{
		this.requirements = requirements;
	}

	public Resource getCost()
	{
		return cost;
	}

	public void setCost(Resource cost)
	{
		this.cost = cost;
	}

	public Resource getProduct()
	{
		return product;
	}

	public void setProduct(Resource product)
	{
		this.product = product;
	}

	public int getFaithPoints()
	{
		return faithPoints;
	}

	public void setFaithPoints(int faithPoints)
	{
		this.faithPoints = faithPoints;
	}
}
