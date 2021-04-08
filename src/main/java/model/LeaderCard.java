package model;

public class LeaderCard
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
		private String discount;									/* SKILL_ONE */

		public String getDiscount()
		{
			return discount;
		}

		public void setDiscount(String discount)
		{
			this.discount = discount;
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
