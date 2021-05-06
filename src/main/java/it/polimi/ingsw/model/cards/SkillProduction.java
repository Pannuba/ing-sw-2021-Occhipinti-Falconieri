package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;

public class SkillProduction extends LeaderCard		/* Every card gives one faith point and one product that can be chosen */
{
	private Resource requirement;			/* Always 1 requirement, 1 output resource, 1 faith point */
	private Resource product;
	private final int faithPoints = 1;

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

	public int getFaithPoints()
	{
		return faithPoints;
	}
}
