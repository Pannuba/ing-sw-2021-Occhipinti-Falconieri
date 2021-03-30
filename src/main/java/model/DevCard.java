public class DevCard
{
	private String color;
	private int points;
	private Resource[]requirements = new Resource[2];
	private Resource[]product = new Resource[3];
	private Resource[]cost = new Resource[3];
	private int layer;

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public Resource[] getRequirements()
	{
		return requirements;
	}

	public void setRequirements(Resource[] requirements)
	{
		this.requirements = requirements;
	}

	public Resource[] getProduct()
	{
		return product;
	}

	public void setProduct(Resource[] product)
	{
		this.product = product;
	}

	public Resource[] getCost()
	{
		return cost;
	}

	public void setCost(Resource[] cost)
	{
		this.cost = cost;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
}
