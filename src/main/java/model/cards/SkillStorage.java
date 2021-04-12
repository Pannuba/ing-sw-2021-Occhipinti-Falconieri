package model.cards;

import model.Resource;

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
