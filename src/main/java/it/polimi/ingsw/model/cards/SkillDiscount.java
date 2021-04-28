package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ResourceType;

public class SkillDiscount extends LeaderCard
{
	private int discount;		/* Always -1 */
	private ResourceType discountedResource;

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
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
