package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.util.Pair;

import java.util.List;

/**
 * SkillProduction leader cards have the ability to add a source of production
 * When used, the player spends a resource of "cost.getResourceType()" type and created one faith point and another resource of any type chosen by the player
 * The activation requires a dev card of a specific color and level, specified in the requirements Pair
 * @author Giulio Occhipinti
 */

public class SkillProduction extends LeaderCard
{
	private boolean isUsedForProduction;
	private Pair<DevCardColor, Integer> requirements;		/* Pair of	(devcard color : devcard level) */
	private Resource cost;									/* In base game, always 1 cost, 1 output resource (chosen by user), 1 faith point */
	private int productAmount;			/* Set in xmls */
	private int faithPoints;			/* Set in xmls */

	/**
	 * Constructor
	 */

	public SkillProduction()		/* ResourceType of cost is assigned by the xmls */
	{
		isUsedForProduction = false;
		cost = new Resource();
	}

	@Override
	public boolean checkRequirements(Player player)		/* Player has to have 1 devcard of color x and level y */
	{
		List<DevCard> devCards = player.getDashboard().getAllDevCards();
		Pair<DevCardColor, Integer> prodRequirements = this.getRequirements();

		for (int i = 0; i < devCards.size(); i++)
		{
			if (devCards.get(i).getColor() == prodRequirements.getObj1() && devCards.get(i).getLevel() == prodRequirements.getObj2())
				return true;
		}

		return false;
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
