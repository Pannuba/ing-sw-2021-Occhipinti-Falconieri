package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;

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
