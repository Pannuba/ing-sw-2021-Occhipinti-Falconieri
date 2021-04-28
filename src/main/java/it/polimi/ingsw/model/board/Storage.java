package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Shelf;

import java.io.IOException;

/*	   O			shelves[0]
	_______
	 O   O			shelves[1]
	_______
	O  O  O			shelves[2]
	_______
*/

public class Storage
{
	private Shelf[] shelves = new Shelf[3];		/* Static array because there are always 3 shelves, no more no less */

	public Storage()
	{
		for (int i = 0; i < 3; i++)
			shelves[i] = new Shelf(i + 1);		/* Initialize shelves passing i+1 as shelf size */
	}

	public boolean checkShelves() throws IOException
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

	public void moveResources(Shelf shelfFrom, Shelf shelfTo, int amount) throws IOException
	{
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

			for (int i = 0; i < shelfFrom.getShelfSize(); i++)
			{
				if (shelves[i].getShelfSize() == shelfFrom.getShelfSize())
					shelves[i] = shelfFrom;
			}

			for (int i = 0; i < shelfTo.getShelfSize(); i++)
			{
				if (shelves[i].getShelfSize() == shelfTo.getShelfSize())
					shelves[i] = shelfTo;
			}
		}
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < shelves.length; i++)
			totalResources += shelves[i].getShelfResource().getQuantity();

		return totalResources;
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
