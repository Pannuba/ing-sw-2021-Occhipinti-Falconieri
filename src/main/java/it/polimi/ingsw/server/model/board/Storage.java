package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.Shelf;

import java.io.IOException;

/*	   O
	_______
	 O   O
	_______
	O  O  O
	_______
*/

public class Storage
{
	private Shelf shelfOne;
	private Shelf shelfTwo;
	private Shelf shelfThree;

	/* Logic methods to check resources in shelf here? Or in controller?? */

	public Storage()
	{
		shelfOne = new Shelf(1);
		shelfTwo = new Shelf(2);
		shelfThree = new Shelf(3);
	}

	public boolean checkShelves() throws IOException
	{
		if (shelfOne.getShelfResource().getQuantity() > 1 || shelfTwo.getShelfResource().getQuantity() > 2 || shelfThree.getShelfResource().getQuantity() > 3)
		{
			System.out.println("Shelf has incorrect amount of resources");
			return false;
		}

		if (shelfOne.getShelfResource().getResourceType() == shelfTwo.getShelfResource().getResourceType()   ||
			shelfTwo.getShelfResource().getResourceType() == shelfThree.getShelfResource().getResourceType() ||
			shelfOne.getShelfResource().getResourceType() == shelfThree.getShelfResource().getResourceType()  )
		{
			System.out.println("Shelf has the same type of resource of another shelf");
			return false;
		}

		else
			return true;
	}

	public void moveResources(Shelf shelfFrom, Shelf shelfTo, int amount) throws IOException
	{
		if (checkShelves() == true && (shelfTo.getShelfSize() - shelfTo.getShelfResource().getQuantity()) >= amount)	/* If there's enough space to move the resource(s) */
		{
																/* If the destination shelf is not empty, the two resource types have to be the same */
			if (shelfTo.getShelfResource().getQuantity() == 0 || shelfFrom.getShelfResource().getResourceType() != shelfTo.getShelfResource().getResourceType())
			{
				shelfFrom.getShelfResource().setQuantity(shelfFrom.getShelfResource().getQuantity() - amount);
				shelfTo.getShelfResource().setQuantity(shelfTo.getShelfResource().getQuantity() + amount);

				if (shelfTo.getShelfResource().getQuantity() == 0)		/* If the destination shelf was empty, set the new resource type */
					shelfTo.getShelfResource().setResourceType(shelfFrom.getShelfResource().getResourceType());

				if (shelfFrom.getShelfResource().getQuantity() == 0)	/* If the source shelf is now empty, set the resource type to null */
					shelfFrom.getShelfResource().setResourceType(null);
			}

			switch(shelfFrom.getShelfSize())
			{
				case 1:
					shelfOne = shelfFrom;
					break;

				case 2:
					shelfTwo = shelfFrom;
					break;

				case 3:
					shelfThree = shelfFrom;
					break;

				default:
					System.out.println("Error");
					break;
			}

			switch(shelfTo.getShelfSize())
			{
				case 1:
					shelfOne = shelfTo;
					break;

				case 2:
					shelfTwo = shelfTo;
					break;

				case 3:
					shelfThree = shelfTo;
					break;

				default:
					System.out.println("Error");
					break;
			}
		}
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		totalResources += shelfOne.getShelfResource().getQuantity();
		totalResources += shelfTwo.getShelfResource().getQuantity();
		totalResources += shelfThree.getShelfResource().getQuantity();

		return totalResources;
	}

	public Shelf getShelfOne()
	{
		return shelfOne;
	}

	public void setShelfOne(Shelf shelfOne)
	{
		this.shelfOne = shelfOne;
	}

	public Shelf getShelfTwo()
	{
		return shelfTwo;
	}

	public void setShelfTwo(Shelf shelfTwo)
	{
		this.shelfTwo = shelfTwo;
	}

	public Shelf getShelfThree()
	{
		return shelfThree;
	}

	public void setShelfThree(Shelf shelfThree)
	{
		this.shelfThree = shelfThree;
	}
}
