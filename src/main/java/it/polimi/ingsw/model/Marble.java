package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Marble class, used by MarblesMarket and BuyResourcesCommand in Controller. Like resources, it only has a type and quantity instance variable
 * @author Giulio Occhipinti
 */

public class Marble implements Serializable
{
	private MarbleType marbleType;
	private int quantity;			/* I forgot what quantity does. It's the quantity, bro */

	public Marble()
	{
		quantity = 0;
		marbleType = null;
	}

	public MarbleType getMarbleType()
	{
		return marbleType;
	}

	public void setMarbleType(MarbleType marbleType)
	{
		this.marbleType = marbleType;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
}