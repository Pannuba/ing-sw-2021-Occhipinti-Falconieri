package it.polimi.ingsw.model;

import java.io.Serializable;

public class Shelf implements Serializable
{
	private int shelfSize;
	private Resource shelfResource;

	public Shelf(int shelfSize)
	{
		this.shelfSize = shelfSize;
		shelfResource = new Resource();			/* Quantity is set to 0 in constructor */
	}

	public int getShelfSize()
	{
		return shelfSize;
	}

	public void setShelfSize(int shelfSize)
	{
		this.shelfSize = shelfSize;
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
