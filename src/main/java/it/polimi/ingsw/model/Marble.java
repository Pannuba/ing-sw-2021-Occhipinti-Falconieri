package it.polimi.ingsw.model;

import java.io.Serializable;

/*	Stone --> grey ---> 0
	Coin --> yellow --> 1
	Shield --> blue --> 2
	Slave --> purple -> 3
	Cross --> red ----> 4  (faith points)
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