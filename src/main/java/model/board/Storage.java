package model.board;

import model.Resource;

public class Storage
{
	private Resource shelfOne;
	private Resource shelfTwo;
	private Resource shelfThree;

	/* Logic methods to check resources in shelf here? Or in controller?? */

	public boolean checkShelves()
	{
		if (shelfOne.getQuantity() != 1 || shelfTwo.getQuantity() != 2 || shelfThree.getQuantity() != 3)
		{
			System.out.println("error");		/* TODO: logging funcition */
			return false;
		}

		if (shelfOne.getCategory() == shelfTwo.getCategory() || shelfTwo.getCategory() == shelfThree.getCategory() || shelfOne.getCategory() == shelfThree.getCategory())
		{
			System.out.println("error");
			return false;
		}

		else
			return true;
	}

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
