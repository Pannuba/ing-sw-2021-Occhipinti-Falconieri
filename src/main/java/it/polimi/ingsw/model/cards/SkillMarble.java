package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class SkillMarble extends LeaderCard			/* Require 2 + 1 devcards of different color, any level */
{
	private ResourceType whiteMarble;
	private DevCardColor[] cost = new DevCardColor[3];		/* For example, [YELLOW, YELLOW, BLUE] */

	public ResourceType getWhiteMarble()
	{
		return whiteMarble;
	}

	public void setWhiteMarble(ResourceType whiteMarble)
	{
		this.whiteMarble = whiteMarble;
	}

	public DevCardColor[] getCost()
	{
		return cost;
	}

	public void setCost(DevCardColor[] cost)
	{
		this.cost = cost;
	}
}
