package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;

public class SkillProduction extends LeaderCard		/* Every card gives one faith point and one product that can be chosen */
{
	private final Resource cost;			/* Always 1 requirement, 1 output resource (chosen by user), 1 faith point */
	private final Resource product;
	private final int faithPoints = 1;

	public SkillProduction()		/* ResourceType of cost and product is assigned by the xmls */
	{
		cost = new Resource();
		cost.setQuantity(1);

		product = new Resource();
		product.setQuantity(1);
	}

	public Resource getCost()
	{
		return cost;
	}

	public Resource getProduct()
	{
		return product;
	}

	public int getFaithPoints()
	{
		return faithPoints;
	}
}
