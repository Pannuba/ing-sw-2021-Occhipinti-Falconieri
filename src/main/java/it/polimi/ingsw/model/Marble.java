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
	private MarbleType type;
	private int quantity;			/* I forgot what quantity does. It's the quantity, bro */

	public Marble()
	{
		quantity = 0;
	}

	public MarbleType getMarbleType()
	{
		return type;
	}

	public void setMarbleType(MarbleType type)
	{
		this.type = type;
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