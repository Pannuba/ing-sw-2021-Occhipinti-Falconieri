package it.polimi.ingsw.model;

import java.io.Serializable;

public class Shelf implements Serializable
{
	private int shelfSize;
	private Resource shelfResource;			/* shelfResource.getQuantity() can't be higher than shelfSize */

	public Shelf()		/* Used by SkillStorage */
	{
		shelfResource = new Resource();
	}

	public Shelf(int shelfSize)
	{
		this.shelfSize = shelfSize;
		shelfResource = new Resource();			/* Quantity is set to 0 in Resource constructor */
	}

	public boolean isEmpty()
	{
		if (shelfResource.getQuantity() == 0 && shelfResource.getResourceType() == null)
			return true;

		else
			return false;
	}

	public boolean isFull()
	{
		if (shelfResource.getQuantity() == shelfSize)
		{
			System.out.println("Shelf " + shelfSize + " is full!");
			return true;
		}

		else
			return false;
	}

	public int getShelfSize()
	{
		return shelfSize;
	}

	public void setShelfSize(int shelfSize)
	{
		this.shelfSize = shelfSize;
	}

	public ResourceType getShelfResourceType()
	{
		return shelfResource.getResourceType();
	}

	public int getShelfResourceQuantity()
	{
		return shelfResource.getQuantity();
	}

	public Resource getShelfResource()
	{
		return shelfResource;
	}

	public void setShelfResource(Resource shelfResource)
	{
		this.shelfResource = shelfResource;
	}
}
