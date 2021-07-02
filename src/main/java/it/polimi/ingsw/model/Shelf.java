package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Shelf class used by Storage and SkillStorage leader cards. It has a fixed size and can only store resources of a certain type
 * @author Giulio Occhipinti
 */

public class Shelf implements Serializable
{
	private int shelfSize;
	private Resource shelfResource;			/* shelfResource.getQuantity() can't be higher than shelfSize */

	/**
	 * Constructor used by SkillStorage leaders, the values are set during the card's deserialization
	 */

	public Shelf()
	{
		shelfResource = new Resource();
	}

	/**
	 * Main constructor
	 * @param shelfSize the shelf's size
	 */

	public Shelf(int shelfSize)
	{
		this.shelfSize = shelfSize;
		shelfResource = new Resource();			/* Quantity is set to 0 in Resource constructor */
	}

	/**
	 * Checks if the shelf is empty
	 * @return true if the shelf is empty, false otherwise
	 */

	public boolean isEmpty()
	{
		if (shelfResource.getQuantity() == 0 && shelfResource.getResourceType() == null)
			return true;

		else
			return false;
	}

	/**
	 * Checks if the shelf is full
	 * @return true if the shelf is full, false otherwise
	 */

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
