package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;

import java.io.Serializable;			/* Has resources bought from the marbles market */

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
			shelves[i] = new Shelf(i + 1);		/* Initialize shelves passing i + 1 as shelf size */
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

		if (checkShelves() == true && (destinationShelf.getShelfSize() - destinationShelf.getShelfResource().getQuantity()) >= resourceToAdd.getQuantity())
		{
			if (resourceToAdd.getResourceType() == destinationShelf.getShelfResource().getResourceType())
			{
				if (destinationShelf.getShelfResource().getResourceType() == null)
					destinationShelf.getShelfResource().setResourceType(resourceToAdd.getResourceType());

				destinationShelf.getShelfResource().setQuantity(destinationShelf.getShelfResource().getQuantity() + resourceToAdd.getQuantity());

				assignToLocalShelves(destinationShelf);
			}

			else
				System.out.println("Storage addResource: destination shelf and resource to add have different resource types");
		}

		else
			System.out.println("Storage addResource: not enough space on the destination shelf");
	}

	public void moveResources(int shelfFromNum, int shelfToNum)				/* Can't have 2 shelves with the same resource according to the rules, */
	{																		/* so this function can only move resources to an empty shelf */
		Shelf shelfFrom = convertIndexToShelf(shelfFromNum);				/* and the source shelf has to be empty afterwards */
		Shelf shelfTo = convertIndexToShelf(shelfToNum);
		int amount = shelfFrom.getShelfResource().getQuantity();

		if (shelfTo.getShelfResource().getQuantity() != 0)
		{
			System.out.println("Storage moveResources: destination shelf is not empty");
			return;
		}

		if (checkShelves() == true && shelfTo.getShelfSize() >= amount)			/* If there's enough space to move the resource(s) */
		{
			shelfTo.getShelfResource().setResourceType(shelfFrom.getShelfResource().getResourceType());
			shelfTo.getShelfResource().setQuantity(amount);

			shelfFrom.getShelfResource().setQuantity(0);
			shelfFrom.getShelfResource().setResourceType(null);			/* Set the resource type to null because the source shelf is now empty */

			assignToLocalShelves(shelfFrom);
			assignToLocalShelves(shelfTo);
		}

		else
			System.out.println("Storage moveResources: not enough space on destination shelf");
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
