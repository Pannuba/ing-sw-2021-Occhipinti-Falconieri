package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class SkillMarble extends LeaderCard
{
	private ResourceType whiteMarble;
	private List<DevCardColor> requirements;		/* Like SkillDiscount but it's 2 + 1 cards in the base game, like [YELLOW, YELLOW, BLUE] */

	public SkillMarble()
	{
		requirements = new ArrayList<>();
	}

	public ResourceType getWhiteMarble()
	{
		return whiteMarble;
	}

	public void setWhiteMarble(ResourceType whiteMarble)
	{
		this.whiteMarble = whiteMarble;
	}

	public List<DevCardColor> getRequirements()
	{
		return requirements;
	}

	public void setRequirements(List<DevCardColor> requirements)
	{
		this.requirements = requirements;
	}
}
