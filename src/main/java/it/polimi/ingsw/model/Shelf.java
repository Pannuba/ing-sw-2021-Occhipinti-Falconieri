package it.polimi.ingsw.model;

import java.io.Serializable;

public class Shelf implements Serializable
{
	private final int shelfSize;
	private Resource shelfResource;			/* shelfResource.getQuantity() can't be higher than shelfSize */

	public Shelf(int shelfSize)
	{
		this.shelfSize = shelfSize;
		shelfResource = new Resource();			/* Quantity is set to 0 in constructor */
	}

	public int getShelfSize()
	{
		return shelfSize;
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
