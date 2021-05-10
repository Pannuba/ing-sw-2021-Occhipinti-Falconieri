package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;

import java.io.Serializable;

/*	   Y			shelves[0]			If I get another Y, I have to swap X and Y so that I have 2 Y in the middle shelf
	_______
	 O   X			shelves[1]
	_______
	Z  Z  Z			shelves[2]
	_______
*/

public class Storage implements Serializable
{
	private Shelf[] shelves = new Shelf[3];		/* Static array because there are always 3 shelves, no more no less */

	public Storage()
	{
		for (int i = 0; i < 3; i++)
			shelves[i] = new Shelf(i + 1);		/* Initialize shelves passing i+1 as shelf size */
	}

	public boolean checkShelves()
	{
		for (int i = 0; i < shelves.length; i++)		/* shelves.length is always 3 */
		{
			if (shelves[i].getShelfResource().getQuantity() > shelves[i].getShelfSize())
			{
				System.out.println("Shelf " + (i + 1) + " has incorrect amount of resources");
				return false;
			}
		}

		if (shelves[0].getShelfResource().getResourceType() == shelves[1].getShelfResource().getResourceType()   ||
			shelves[1].getShelfResource().getResourceType() == shelves[2].getShelfResource().getResourceType()   ||
			shelves[0].getShelfResource().getResourceType() == shelves[2].getShelfResource().getResourceType())
		{
			System.out.println("Shelf has the same type of resource of another shelf");
			return false;
		}

		else
			return true;
	}

	public void addResource(Resource resourceToAdd, int shelfNumber)		/* Called in controller, shelfNumber is 1 for shelves[0] and so on */
	{
		Shelf destinationShelf = convertIndexToShelf(shelfNumber);

		if (checkShelves() == true && (destinationShelf.getShelfSize() - destinationShelf.getShelfResource().getQuantity()) >= resourceToAdd.getQuantity()	&&
			resourceToAdd.getResourceType() == destinationShelf.getShelfResource().getResourceType())
		{
			if (destinationShelf.getShelfResource().getResourceType() == null)
				destinationShelf.getShelfResource().setResourceType(resourceToAdd.getResourceType());

			destinationShelf.getShelfResource().setQuantity(destinationShelf.getShelfResource().getQuantity() + resourceToAdd.getQuantity());

			assignToLocalShelves(destinationShelf);
		}

		else
		{
			System.out.println("Error");
		}
	}

	public void moveResources(int shelfFromNum, int shelfToNum, int amount)	/* Java is pass-by-value, if this is called from another file the shelves will remain unchanged */
	{
		Shelf shelfFrom = convertIndexToShelf(shelfFromNum);
		Shelf shelfTo = convertIndexToShelf(shelfToNum);

		if (checkShelves() == true && (shelfTo.getShelfSize() - shelfTo.getShelfResource().getQuantity()) >= amount)	/* If there's enough space to move the resource(s) */
		{
																/* If the destination shelf is not empty, the two resource types have to be the same */
			if (shelfTo.getShelfResource().getQuantity() == 0 || shelfFrom.getShelfResource().getResourceType() != shelfTo.getShelfResource().getResourceType())
			{
				if (shelfTo.getShelfResource().getQuantity() == 0)		/* If the destination shelf was empty, set the new resource type */
					shelfTo.getShelfResource().setResourceType(shelfFrom.getShelfResource().getResourceType());

				shelfFrom.getShelfResource().setQuantity(shelfFrom.getShelfResource().getQuantity() - amount);
				shelfTo.getShelfResource().setQuantity(shelfTo.getShelfResource().getQuantity() + amount);

				if (shelfFrom.getShelfResource().getQuantity() == 0)	/* If the source shelf is now empty, set the resource type to null */
					shelfFrom.getShelfResource().setResourceType(null);
			}

			assignToLocalShelves(shelfFrom);
			assignToLocalShelves(shelfTo);
		}

		else
		{
			System.out.println("Can't move resources");
		}
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < shelves.length; i++)
			totalResources += shelves[i].getShelfResource().getQuantity();

		return totalResources;
	}

	private Shelf convertIndexToShelf(int shelfNumber)
	{
		switch (shelfNumber)
		{
			case 1:
				return shelves[0];

			case 2:
				return shelves[1];

			case 3:
				return shelves[2];

			default:
				System.out.println("Error");
				return null;
		}
	}

	private void assignToLocalShelves(Shelf shelf)
	{
		for (int i = 0; i < shelf.getShelfSize(); i++)
		{
			if (shelves[i].getShelfSize() == shelf.getShelfSize())
				shelves[i] = shelf;
		}
	}

	public Shelf[] getShelves()
	{
		return shelves;
	}

	public void setShelves(Shelf[] shelves)
	{
		this.shelves = shelves;
	}
}
