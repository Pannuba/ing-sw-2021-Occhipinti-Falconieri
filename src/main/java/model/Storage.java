package model;

public class Storage
{
	private Resource shelfOne;
	private Resource shelfTwo;
	private Resource shelfThree;

	/* Logic methods to check resources in shelf here? Or in controller?? */

	public Resource getShelfOne()
	{
		return shelfOne;
	}

	public void setShelfOne(Resource shelfOne)
	{
		this.shelfOne = shelfOne;
	}

	public Resource getShelfTwo()
	{
		return shelfTwo;
	}

	public void setShelfTwo(Resource shelfTwo)
	{
		this.shelfTwo = shelfTwo;
	}

	public Resource getShelfThree()
	{
		return shelfThree;
	}

	public void setShelfThree(Resource shelfThree)
	{
		this.shelfThree = shelfThree;
	}
}
