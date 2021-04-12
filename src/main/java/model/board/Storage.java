package model.board;

import model.Resource;
import tools.Logger;

import java.io.IOException;

public class Storage
{
	private Resource shelfOne;
	private Resource shelfTwo;
	private Resource shelfThree;
	private Logger logger;

	/* Logic methods to check resources in shelf here? Or in controller?? */

	public boolean checkShelves() throws IOException
	{
		if (shelfOne.getQuantity() != 1 || shelfTwo.getQuantity() != 2 || shelfThree.getQuantity() != 3)
		{
			logger.log("Shelf has incorrect amount of resources");
			return false;
		}

		if (shelfOne.getCategory() == shelfTwo.getCategory() || shelfTwo.getCategory() == shelfThree.getCategory() || shelfOne.getCategory() == shelfThree.getCategory())
		{
			logger.log("Shelf has the same type of resource of another shelf");
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
