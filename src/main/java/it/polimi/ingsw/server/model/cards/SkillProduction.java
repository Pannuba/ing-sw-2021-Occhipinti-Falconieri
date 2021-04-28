package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;

public class SkillProduction extends LeaderCard		/* Every card gives one faith point and one product that can be chosen */
{
	private Resource requirement;			/* Always 1 requirement, 1 output resource, 1 faith point */
	private Resource chosenProduction;
	private int faithPoints;

	public Resource getRequirement()
	{
		return requirement;
	}

	public void setRequirement(Resource requirement)
	{
		this.requirement = requirement;
	}

	public Resource getChosenProduction()
	{
		return chosenProduction;
	}

	public void setChosenProduction(Resource chosenProduction)
	{
		this.chosenProduction = chosenProduction;
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
