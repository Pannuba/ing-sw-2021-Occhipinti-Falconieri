package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class SkillDiscount extends LeaderCard
{
	private final int discount = -1;
	private ResourceType discountedResource;
	private DevCardColor[] cost = new DevCardColor[2];			/* Static array because it always requires 2 devcards of different color */

	public int getDiscount()
	{
		return discount;
	}

	public ResourceType getDiscountedResource()
	{
		return discountedResource;
	}

	public void setDiscountedResource(ResourceType discountedResource)
	{
		this.discountedResource = discountedResource;
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
