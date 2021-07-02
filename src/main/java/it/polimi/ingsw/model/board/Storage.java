package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;

import java.io.Serializable;

/**
 * Storage of each dashboard. Has the resources bought from the marbles market
 *
 *	   Y			shelves[0]
 *	_______
 *	 X   0			shelves[1]
 *	_______
 *	Z  Z  0			shelves[2]
 *	_______
 *
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class Storage implements Serializable
{
	private Shelf[] shelves = new Shelf[3];

	/**
	 * Creates storage and initialize shelves passing i + 1 as shelf size
	 */

	public Storage()
	{
		for (int i = 0; i < 3; i++)
			shelves[i] = new Shelf(i + 1);
	}

	/**
	 * Checks that all shelves have the right amount of resources and that a shelf doesn't have the same type of resource of another
	 * @return true if the shelves pass the checks, false otherwise
	 */

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

		if	((shelves[0].getShelfResourceType() == null && shelves[1].getShelfResourceType() == null) ||
			( shelves[1].getShelfResourceType() == null && shelves[2].getShelfResourceType() == null) ||
			( shelves[0].getShelfResourceType() == null && shelves[2].getShelfResourceType() == null))
			return true;		/* Otherwise checkShelves returns false at the start of the match */

		if (shelves[0].getShelfResourceType() == shelves[1].getShelfResourceType()	||
			shelves[1].getShelfResourceType() == shelves[2].getShelfResourceType()	||
			shelves[0].getShelfResourceType() == shelves[2].getShelfResourceType()	)
		{
			System.out.println("Shelf has the same type of resource of another shelf");
			return false;
		}

		else
			return true;
	}

	/**
	 * Add one resource to a specified shelf, called by addResourceSmart
	 * @param resourceToAdd the single resource that has to be added to the storage
	 * @param shelfNumber the shelf number where the resource is going to be put
	 * @return true if the resource is added successfully, false otherwise
	 */

	public boolean addResource(ResourceType resourceToAdd, int shelfNumber)
	{
		Shelf destinationShelf = convertIndexToShelf(shelfNumber);
		System.out.println("addResource: adding 1 " + resourceToAdd + " to shelf " + shelfNumber);

		if (checkShelves() && (destinationShelf.getShelfSize() - destinationShelf.getShelfResourceQuantity()) >= 1)
		{
			if (resourceToAdd == destinationShelf.getShelfResourceType() || destinationShelf.getShelfResourceType() == null)
			{
				if (destinationShelf.getShelfResourceType() == null)
					destinationShelf.getShelfResource().setResourceType(resourceToAdd);

				destinationShelf.getShelfResource().setQuantity(destinationShelf.getShelfResourceQuantity() + 1);
				System.out.println("addResource: destinationShelf = " + destinationShelf.getShelfResourceQuantity() + " " + destinationShelf.getShelfResourceType());

				assignToLocalShelves(destinationShelf);		/* One of the old shelves is replaced by the local shelf created in this function */
				return true;
			}

			else
			{
				System.out.println("Storage addResource: destination shelf and resource to add have different resource types");
				return false;
			}
		}

		else
		{
			System.out.println("Storage addResource: not enough space on the destination shelf");
			return false;
		}
	}

	/**
	 * Adds a resource without having to specify the shelf number but only the type.
	 * Discards the resource only if there is no more available space
	 * @param resourceToAdd the resource that's going to be added
	 * @return the resource if it cannot be added to storage, else null
	 */

	public ResourceType addResourceSmart(ResourceType resourceToAdd)
	{
		checkShelves();
		System.out.println("addResoruceSmart: adding " + resourceToAdd);
		ResourceType excessResource = null;

		if	(shelves[2].isEmpty() && shelves[0].getShelfResourceType() == resourceToAdd && shelves[1].getShelfResourceType() != resourceToAdd)
		{
			moveResources(1, 3);
			addResource(resourceToAdd, 3);
		}

		else if (shelves[2].isEmpty() && shelves[1].getShelfResourceType() == resourceToAdd && shelves[0].getShelfResourceType() != resourceToAdd)
		{
			moveResources(2, 3);
			addResource(resourceToAdd, 3);
		}

		else if (shelves[2].isEmpty() && shelves[1].getShelfResourceType() != resourceToAdd && shelves[0].getShelfResourceType() != resourceToAdd)
		{
			addResource(resourceToAdd, 3);
		}

		else if (!shelves[2].isFull() && shelves[2].getShelfResourceType() == resourceToAdd && shelves[0].getShelfResourceType() != resourceToAdd && shelves[1].getShelfResourceType() != resourceToAdd)
		{
			addResource(resourceToAdd, 3);
		}

		else if (shelves[1].isEmpty() && shelves[0].getShelfResourceType() == resourceToAdd && shelves[2].getShelfResourceType() != resourceToAdd)
		{
			moveResources(1, 2);		/* First check if shelves can be optimized by moving the same resource type from a smaller shelf to a bigger one */
			addResource(resourceToAdd, 2);
		}

		else if (shelves[1].isEmpty() && shelves[2].getShelfResourceType() != resourceToAdd && shelves[0].getShelfResourceType() != resourceToAdd)
		{
			addResource(resourceToAdd, 2);
		}

		else if (!shelves[1].isFull() && shelves[1].getShelfResourceType() == resourceToAdd && shelves[0].getShelfResourceType() != resourceToAdd && shelves[2].getShelfResourceType() != resourceToAdd)
		{
			addResource(resourceToAdd, 2);
		}

		else if	(shelves[0].isEmpty() && shelves[1].getShelfResourceType() != resourceToAdd && shelves[2].getShelfResourceType() != resourceToAdd)
		{
			addResource(resourceToAdd, 1);
		}

		else if (shelves[0].isEmpty() && shelves[1].getShelfResourceQuantity() == 2 && shelves[2].getShelfResourceQuantity() == 2 && shelves[1].getShelfResourceType() == resourceToAdd)
		{
			ResourceType tempShelfResource = shelves[1].getShelfResourceType();
			shelves[1].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
			shelves[2].getShelfResource().setResourceType(tempShelfResource);
			addResource(resourceToAdd, 3);
		}

		else if (shelves[1].isEmpty() && shelves[0].getShelfResourceQuantity() == 1 && shelves[2].getShelfResourceQuantity() == 1 && shelves[0].getShelfResourceType() == resourceToAdd)
		{
			ResourceType tempShelfResource = shelves[0].getShelfResourceType();
			shelves[0].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
			shelves[2].getShelfResource().setResourceType(tempShelfResource);
			addResource(resourceToAdd, 3);
		}

		else if (shelves[2].isEmpty() && shelves[0].getShelfResourceQuantity() == 1 && shelves[1].getShelfResourceQuantity() == 1 && shelves[0].getShelfResourceType() == resourceToAdd)
		{
			ResourceType tempShelfResource = shelves[0].getShelfResourceType();
			shelves[0].getShelfResource().setResourceType(shelves[1].getShelfResourceType());
			shelves[1].getShelfResource().setResourceType(tempShelfResource);
			addResource(resourceToAdd, 2);
		}

		else if (shelves[0].isEmpty() && shelves[2].getShelfResourceQuantity() == 1 && shelves[1].getShelfResourceQuantity() == 2 && shelves[1].getShelfResourceType() == resourceToAdd)
		{
			ResourceType tempShelfResource = shelves[1].getShelfResourceType();
			int tempQuantity = shelves[1].getShelfResourceQuantity();
			shelves[1].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
			shelves[2].getShelfResource().setResourceType(tempShelfResource);
			shelves[1].getShelfResource().setQuantity(shelves[2].getShelfResourceQuantity());
			shelves[2].getShelfResource().setQuantity(tempQuantity);
			addResource(resourceToAdd, 3);
		}

		else if (!shelves[2].isEmpty() && !shelves[1].isEmpty() && !shelves[0].isEmpty())
		{
			if (shelves[2].getShelfResourceQuantity() == 1 && shelves[0].getShelfResourceType() == resourceToAdd)
			{
				ResourceType tempShelfResource = shelves[0].getShelfResourceType();
				shelves[0].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
				shelves[2].getShelfResource().setResourceType(tempShelfResource);
				addResource(resourceToAdd, 3);
			}

			else if (shelves[1].getShelfResourceQuantity() == 1 && shelves[0].getShelfResourceType() == resourceToAdd)
			{
				ResourceType tempShelfResource = shelves[0].getShelfResourceType();
				shelves[0].getShelfResource().setResourceType(shelves[1].getShelfResourceType());
				shelves[1].getShelfResource().setResourceType(tempShelfResource);
				addResource(resourceToAdd, 2);
			}

			else if (shelves[2].getShelfResourceQuantity() == 2 && shelves[1].getShelfResourceQuantity() == 2 && shelves[1].getShelfResourceType() == resourceToAdd)
			{
				ResourceType tempShelfResource = shelves[1].getShelfResourceType();
				shelves[1].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
				shelves[2].getShelfResource().setResourceType(tempShelfResource);
				addResource(resourceToAdd, 3);
			}

			else if (shelves[2].getShelfResourceQuantity() == 1 && shelves[1].getShelfResourceQuantity() == 2 && shelves[1].getShelfResourceType() == resourceToAdd)
			{
				ResourceType tempShelfResource = shelves[1].getShelfResourceType();
				int tempQuantity = shelves[1].getShelfResourceQuantity();
				shelves[1].getShelfResource().setResourceType(shelves[2].getShelfResourceType());
				shelves[1].getShelfResource().setQuantity(shelves[2].getShelfResourceQuantity());
				shelves[2].getShelfResource().setResourceType(tempShelfResource);
				shelves[2].getShelfResource().setQuantity(tempQuantity);
				addResource(resourceToAdd, 3);
			}

			else
				excessResource = resourceToAdd;
		}

		else
			excessResource = resourceToAdd;

		return excessResource;
	}

	/**
	 * Removes multiple resources of the same type one at a time
	 * @param resourceToRemove the resource that is going to be removed
	 * @return the number of removed resources
	 */

	public int removeResource(Resource resourceToRemove)
	{
		int removedResNum = 0;

		for (int i = 0; i < resourceToRemove.getQuantity(); i++)
		{

			if (shelves[0].getShelfResourceType() == resourceToRemove.getResourceType() && shelves[0].getShelfResourceQuantity() == 1)	/* Second check is redundant */
			{
				shelves[0].getShelfResource().setQuantity(0);
				shelves[0].getShelfResource().setResourceType(null);
				removedResNum++;
			}

			else if (shelves[1].getShelfResourceType() == resourceToRemove.getResourceType() && shelves[1].getShelfResourceQuantity() >= 1)
			{
				shelves[1].getShelfResource().setQuantity(shelves[1].getShelfResourceQuantity() - 1);

				if (shelves[1].getShelfResourceQuantity() == 0)
					shelves[1].getShelfResource().setResourceType(null);

				removedResNum++;
			}

			else if (shelves[2].getShelfResourceType() == resourceToRemove.getResourceType() && shelves[2].getShelfResourceQuantity() >= 1)
			{
				shelves[2].getShelfResource().setQuantity(shelves[2].getShelfResourceQuantity() - 1);

				if (shelves[2].getShelfResourceQuantity() == 0)
					shelves[2].getShelfResource().setResourceType(null);

				removedResNum++;
			}
		}

		System.out.println("Storage removeResource: removed " + removedResNum + " " + resourceToRemove.getResourceType());
		return removedResNum;
	}

	/**
	 * Moves the resources contained in the source shelf to the destination shelf, if possible
	 * @param shelfFromNum the source shelf's number
	 * @param shelfToNum the destination shelf's number
	 * @return true if the resources have been succesfully moved, false otherwise
	 */

	public boolean moveResources(int shelfFromNum, int shelfToNum)
	{
		Shelf shelfFrom = convertIndexToShelf(shelfFromNum);
		Shelf shelfTo = convertIndexToShelf(shelfToNum);
		int amount = shelfFrom.getShelfResourceQuantity();
		System.out.println("moveResources: moving from shelf " + shelfFromNum + " to shelf " + shelfToNum);

		if (shelfTo.getShelfResourceQuantity() != 0)
		{
			System.out.println("Storage moveResources: destination shelf is not empty");
			return false;
		}

		if (checkShelves() && shelfTo.getShelfSize() >= amount)			/* If there's enough space to move the resource(s) */
		{
			shelfTo.getShelfResource().setResourceType(shelfFrom.getShelfResource().getResourceType());
			shelfTo.getShelfResource().setQuantity(amount);

			shelfFrom.getShelfResource().setQuantity(0);
			shelfFrom.getShelfResource().setResourceType(null);			/* Set the resource type to null because the source shelf is now empty */

			assignToLocalShelves(shelfFrom);
			assignToLocalShelves(shelfTo);
			return true;
		}

		else
		{
			System.out.println("Storage moveResources: not enough space on destination shelf");
			return false;
		}
	}

	/**
	 * Used to check the cards' requirements
	 * @param resourceToFind the resource to check the presence of
	 * @return the number of resources of passed type
	 */

	public int findResourceByType(ResourceType resourceToFind)
	{
		int counter = 0;

		for (int i = 0; i < 3; i++)
		{
			if (shelves[i].getShelfResourceType() == resourceToFind)
				counter += shelves[i].getShelfResourceQuantity();
		}

		return counter;
	}

	/**
	 * Used to calculate the final score, to activate a leaderCard or to purchase a devCard
	 * @return the total number of resources in the storage
	 */

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < shelves.length; i++)
			totalResources += shelves[i].getShelfResourceQuantity();

		return totalResources;
	}

	/**
	 * Returns the shelf corresponding to the passed index
	 * @param shelfNumber the shelf's index (1-indexed, so 1, 2 or 3)
	 * @return the shelf corresponding to the passed index
	 */

	public Shelf convertIndexToShelf(int shelfNumber)
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

	/**
	 * Replaces one shelf with a temporary one made in addResource or moveResources
	 * @param shelf the temporary shelf that is going to replace one of the three shelves
	 */

	private void assignToLocalShelves(Shelf shelf)
	{
		for (int i = 0; i < shelves.length; i++)
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
