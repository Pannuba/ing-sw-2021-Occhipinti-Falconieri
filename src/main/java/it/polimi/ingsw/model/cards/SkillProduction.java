package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;

public class SkillProduction extends LeaderCard		/* Always 1 requirement, 1 output resource (chosen by user), 1 faith point */
{
	private Resource requirement;
	private Resource product;
	private final int faithPoints = 1;
	private DevCardColor cost;					/* Requires 1 level 2 devcard */
	private final int costLevel = 2;			/* Not really necessary, can be hardcoded when doing the card check in controller or whatever */

	public SkillProduction()
	{
		requirement = new Resource();
		requirement.setQuantity(1);

		product = new Resource();
		product.setQuantity(1);
	}

	public Resource getRequirement()
	{
		return requirement;
	}

	public void setRequirement(Resource requirement)
	{
		this.requirement = requirement;
	}

	public Resource getProduct()
	{
		return product;
	}

	public void setProduct(Resource product)
	{
		this.product = product;
	}

	public DevCardColor getCost()
	{
		return cost;
	}

	public void setCost(DevCardColor cost)
	{
		this.cost = cost;
	}

	public int getCostLevel()
	{
		return costLevel;
	}

	public int getFaithPoints()
	{
		return faithPoints;
	}
}
