package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
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
			if (shelves[i].getShelfResourceQuantity() > shelves[i].getShelfSize())
			{
				System.out.println("Shelf " + (i + 1) + " has incorrect amount of resources");
				return false;
			}
		}

		if (shelves[0].getShelfResource().getResourceType() == null && shelves[1].getShelfResource().getResourceType() == null &&
			shelves[2].getShelfResource().getResourceType() == null)
			return true;		/* Otherwise checkShelves returns false at the start of the match */

		if (shelves[0].getShelfResource().getResourceType() == shelves[1].getShelfResource().getResourceType()   ||
			shelves[1].getShelfResource().getResourceType() == shelves[2].getShelfResource().getResourceType()   ||
			shelves[0].getShelfResource().getResourceType() == shelves[2].getShelfResource().getResourceType()	 )
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

		if (checkShelves() == true && (destinationShelf.getShelfSize() - destinationShelf.getShelfResourceQuantity()) >= resourceToAdd.getQuantity())
		{
			if (resourceToAdd.getResourceType() == destinationShelf.getShelfResource().getResourceType() || destinationShelf.getShelfResource().getResourceType() == null)
			{
				if (destinationShelf.getShelfResource().getResourceType() == null)
					destinationShelf.getShelfResource().setResourceType(resourceToAdd.getResourceType());

				destinationShelf.getShelfResource().setQuantity(destinationShelf.getShelfResourceQuantity() + resourceToAdd.getQuantity());

				assignToLocalShelves(destinationShelf);
			}

			else
				System.out.println("Storage addResource: destination shelf and resource to add have different resource types");
		}

		else
			System.out.println("Storage addResource: not enough space on the destination shelf");
	}

	public void addResourceSmart(Resource resourceToAdd)	/* Adds a resource without having to specify the shelf number. Checks the smaller shelf first */
	{

		switch(resourceToAdd.getQuantity())
		{
			case 1:
				if 		(shelves[0].isEmpty())
					addResource(resourceToAdd, 1);

				else if (shelves[1].isEmpty() && shelves[0].getShelfResourceType() == resourceToAdd.getResourceType())
				{
					moveResources(1, 2);		/* First check if shelves can be optimized by moving the same resource type from a smaller shelf to a bigger one */
					addResource(resourceToAdd, 2);
				}

				else if (shelves[2].isEmpty() && shelves[1].getShelfResourceType() == resourceToAdd.getResourceType())
				{
					moveResources(2, 3);
					addResource(resourceToAdd, 3);
				}

				else if (shelves[1].isEmpty())						/* Otherwise just move them to the next shelf with enough space */
					addResource(resourceToAdd, 2);

				else if (shelves[2].isEmpty())
					addResource(resourceToAdd, 3);

				break;

			case 2:
				if 		(shelves[1].isEmpty())
					addResource(resourceToAdd, 2);

				else if (shelves[2].isEmpty() && shelves[1].getShelfResourceType() == resourceToAdd.getResourceType() && shelves[1].getShelfResourceQuantity() < 2)
				{
					moveResources(2, 3);
					addResource(resourceToAdd, 3);
				}

				break;

			case 3:
				if (shelves[2].isEmpty())
					addResource(resourceToAdd, 3);

			/* Is it possible to add 4 or more resources at the same time? */

		}
	}

	public void moveResources(int shelfFromNum, int shelfToNum)				/* Can't have 2 shelves with the same resource according to the rules, */
	{																		/* so this function can only move resources to an empty shelf */
		Shelf shelfFrom = convertIndexToShelf(shelfFromNum);				/* and the source shelf has to be empty afterwards */
		Shelf shelfTo = convertIndexToShelf(shelfToNum);
		int amount = shelfFrom.getShelfResourceQuantity();

		if (shelfTo.getShelfResourceQuantity() != 0)
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

	public int findResourceByType(ResourceType resourceToFind)		/* Returns the number of resources of passed type. Used to check cards requirements */
	{
		int counter = 0;

		for (int i = 0; i < 3; i++)
		{
			if (shelves[i].getShelfResourceType() == resourceToFind)
				counter += shelves[i].getShelfResourceQuantity();
		}

		return counter;
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < shelves.length; i++)
			totalResources += shelves[i].getShelfResourceQuantity();

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
