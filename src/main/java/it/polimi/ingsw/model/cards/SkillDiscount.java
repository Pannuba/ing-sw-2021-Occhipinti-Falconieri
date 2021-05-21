package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class SkillDiscount extends LeaderCard
{
	private int discount;
	private List<DevCardColor> requirements;			/* Requires devcards of different color, any level. 2 in base game but List for scalability */
	private ResourceType discountedResource;

	public SkillDiscount()
	{
		requirements = new ArrayList<>();
	}

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
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
