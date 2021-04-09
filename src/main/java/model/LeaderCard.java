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

	public static class SkillTwo extends LeaderCard
	{
		private Resource additionalStorage;

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
		private String whiteMarble;									/* Not sure about type */

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
		private Resource productionCost;
		//private Resource[]product = new Resource[2];		/* Every card gives one RedCross and one product that can be chosen */
		private Resource chosenProduction;
		private Resource faithPoints;

		public Resource getProductionCost()
		{
			return productionCost;
		}

		public void setProductionCost(Resource productionCost)
		{
			this.productionCost = productionCost;
		}

		public Resource getChosenProduction()
		{
			return chosenProduction;
		}

		public void setChosenProduction(Resource chosenProduction)
		{
			this.chosenProduction = chosenProduction;
		}

		public Resource getFaithPoints()
		{
			return faithPoints;
		}

		public void setFaithPoints(Resource faithPoints)
		{
			this.faithPoints = faithPoints;
		}
	}
}
