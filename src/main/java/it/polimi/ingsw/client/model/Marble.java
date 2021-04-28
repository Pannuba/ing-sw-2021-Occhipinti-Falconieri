package it.polimi.ingsw.client.model;

public class Marble
{
	private MarbleType category;
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