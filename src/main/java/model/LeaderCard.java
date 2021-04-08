package model;

public class LeaderCard		/* abstract? interface? enum????? */
{
	private int points;
	private String requirements;

	public LeaderCard()				/* For some reason, XML_Serialization throws exceptions without this */
	{

	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public String getRequirements()
	{
		return requirements;
	}

	public void setRequirements(String requirements)
	{
		this.requirements = requirements;
	}

	public static class SkillOne extends LeaderCard
	{
		private int discount;									/* SKILL_ONE */
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

	public static class SkillTwo extends LeaderCard
	{
		private Resource additionalStorage;		/* SKILL_TWO (specify type see cards) */

		public Resource getAdditionalStorage()
		{
			return additionalStorage;
		}

		public void setAdditionalStorage(Resource additionalStorage)
		{
			this.additionalStorage = additionalStorage;
		}
	}

	public static class SkillThree extends LeaderCard
	{
		private String whiteMarble;									/* SKILL_THREE, not sure about type */

		public String getWhiteMarble()
		{
			return whiteMarble;
		}

		public void setWhiteMarble(String whiteMarble)
		{
			this.whiteMarble = whiteMarble;
		}
	}

	public static class SkillFour extends LeaderCard
	{
		private Resource cost;										/* SKILL_FOUR */
		private Resource[]product = new Resource[2];

		public Resource getCost()
		{
			return cost;
		}

		public void setCost(Resource cost)
		{
			this.cost = cost;
		}

		public Resource[] getProduct()
		{
			return product;
		}

		public void setProduct(Resource[] product)
		{
			this.product = product;
		}
	}
}
