package it.polimi.ingsw.client.model.cards;

import it.polimi.ingsw.client.model.Resource;

public class SkillProduction extends LeaderCard
{
	private Resource productionCost;
	//private Resource[]product = new Resource[2];		/* Every card gives one RedCross and one product that can be chosen */
	private Resource chosenProduction;
	private Resource faithPoints;

	public Resource getProductionCost()
	{
		return productionCost;
	}

	public void setProductionCost(Resource productionCost)
	{
		this.productionCost = productionCost;
	}

	public Resource getChosenProduction()
	{
		return chosenProduction;
	}

	public void setChosenProduction(Resource chosenProduction)
	{
		this.chosenProduction = chosenProduction;
	}

	public Resource getFaithPoints()
	{
		return faithPoints;
	}

	public void setFaithPoints(Resource faithPoints)
	{
		this.faithPoints = faithPoints;
	}
}
