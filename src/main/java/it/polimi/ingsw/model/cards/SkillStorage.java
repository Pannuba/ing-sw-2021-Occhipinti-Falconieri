package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;

public class SkillStorage extends LeaderCard		/* "final" keyword in instance variables makes serialization not work */
{
	private Resource requirements;			/* Only 1 resource type */
	private Shelf additionalStorage;

	public SkillStorage()
	{
		requirements = new Resource();
		additionalStorage = new Shelf();		/* shelfSize is not specified because the value is in the card's xml */
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

	public void setAdditionalStorage(Shelf additionalStorage)
	{
		this.additionalStorage = additionalStorage;
	}
}
