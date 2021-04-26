package server.model.cards;

import server.model.Resource;

public class SkillDiscount extends LeaderCard
{
	private int discount;
	private Resource discountedResource;

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
	}

	public Resource getDiscountedResource()
	{
		return discountedResource;
	}

	public void setDiscountedResource(Resource discountedResource)
	{
		this.discountedResource = discountedResource;
	}
}
