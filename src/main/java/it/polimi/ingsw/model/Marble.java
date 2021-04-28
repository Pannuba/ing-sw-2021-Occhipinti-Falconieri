package it.polimi.ingsw.model;

/*	Stone --> grey ---> 0
	Coin --> yellow --> 1
	Shield --> blue --> 2
	Slave --> purple -> 3
	Cross --> red ----> 4  (faith points)
 */

public class Marble
{
	private MarbleType category;				/* Rename category to type? */
	private int quantity;

	public MarbleType getMarbleType()
	{
		return category;
	}

	public void setMarbleType(MarbleType category)
	{
		this.category = category;
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