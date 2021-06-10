package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.util.Pair;

public class SkillProduction extends LeaderCard		/* Every card gives one faith point and one product that can be chosen */
{
	private boolean isUsedForProduction;
	private Pair<DevCardColor, Integer> requirements;		/* Pair of	(devcard color : devcard level) */
	private Resource cost;									/* In base game, always 1 cost, 1 output resource (chosen by user), 1 faith point */
	private int productAmount;			/* Set in xmls */
	private int faithPoints;			/* Set in xmls */

	public SkillProduction()		/* ResourceType of cost and product is assigned by the xmls */
	{
		isUsedForProduction = false;
		cost = new Resource();
	}

	public boolean isUsedForProduction()
	{
		return isUsedForProduction;
	}

	public void setUsedForProduction(boolean usedForProduction)
	{
		isUsedForProduction = usedForProduction;
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

	public void setProductAmount(int productAmount)
	{
		this.productAmount = productAmount;
	}

	public int getProductAmount()
	{
		return productAmount;
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
