package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;

public class SkillStorage extends LeaderCard		/* "final" keyword in instance variables makes serialization not work */
{
	private Resource requirements;			/* Only 1 resource type */
	private final Shelf additionalStorage;

	public SkillStorage()
	{
		requirements = new Resource();			/* shelfSize and resource type are not specified because the value is in the card's xml */
		additionalStorage = new Shelf();
	}

	public void addOneResource()			/* Increases the amount of resources in the additionalStorage by 1. Checks already performed by controller */
	{
		additionalStorage.getShelfResource().setQuantity(additionalStorage.getShelfResourceQuantity() + 1);
	}

	public int removeResources(int resToRemoveNum)
	{
		int removedResNum = 0;

		for (int i = 0; i < resToRemoveNum; i++)
		{
			if (!additionalStorage.isEmpty())		/* To avoid going below zero */
			{
				additionalStorage.getShelfResource().setQuantity(additionalStorage.getShelfResourceQuantity() - 1);
				removedResNum++;
			}
		}

		System.out.println("SkillStorage removeResources: removed " + removedResNum + " " + additionalStorage.getShelfResourceType());
		return removedResNum;
	}

	public Resource getRequirements()
	{
		return requirements;
	}

	public void setRequirements(Resource requirements)
	{
		this.requirements = requirements;
	}

	public Shelf getAdditionalStorage()
	{
		return additionalStorage;
	}
}
