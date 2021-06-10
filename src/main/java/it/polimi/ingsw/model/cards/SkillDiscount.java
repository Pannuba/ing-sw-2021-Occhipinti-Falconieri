package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Specify the skill of the leader card: discount
 * Requires two devCards of different color
 * @author Giulio Occhipinti
 */

public class SkillDiscount extends LeaderCard
{
	private int discountNum;
	private List<DevCardColor> requirements;			/* Requires devcards of different color, any level. 2 in base game but List for scalability */
	private ResourceType discountedResource;

	public SkillDiscount()
	{
		requirements = new ArrayList<>();
	}

	public int getDiscountNum()
	{
		return discountNum;
	}

	public void setDiscountNum(int discountNum)
	{
		this.discountNum = discountNum;
	}

	public List<DevCardColor> getRequirements()
	{
		return requirements;
	}

	public void setRequirements(List<DevCardColor> requirements)
	{
		this.requirements = requirements;
	}

	public ResourceType getDiscountedResource()
	{
		return discountedResource;
	}

	public void setDiscountedResource(ResourceType discountedResource)
	{
		this.discountedResource = discountedResource;
	}
}
