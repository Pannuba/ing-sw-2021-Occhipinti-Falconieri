package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.ResourceType;

public class SkillMarble extends LeaderCard
{
	private ResourceType whiteMarble;									/* Not sure about type */

	public ResourceType getWhiteMarble()
	{
		return whiteMarble;
	}

	public void setWhiteMarble(ResourceType whiteMarble)
	{
		this.whiteMarble = whiteMarble;
	}
}
