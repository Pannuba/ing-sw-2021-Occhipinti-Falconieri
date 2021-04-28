package it.polimi.ingsw.client.model.cards;

import it.polimi.ingsw.client.model.Resource;

public class SkillStorage extends LeaderCard
{
	private Resource additionalStorage;

	public Resource getAdditionalStorage()
	{
		return additionalStorage;
	}

	public void setAdditionalStorage(Resource additionalStorage)
	{
		this.additionalStorage = additionalStorage;
	}
}
