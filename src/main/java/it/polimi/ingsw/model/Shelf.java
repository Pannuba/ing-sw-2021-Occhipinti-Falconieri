package it.polimi.ingsw.model;

import java.util.List;

public class Shelf
{
	private int shelfSize;
	private Resource shelfResource;

	public Shelf(int shelfSize)
	{
		this.shelfSize = shelfSize;
		shelfResource = new Resource();
		shelfResource.setQuantity(0);
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
