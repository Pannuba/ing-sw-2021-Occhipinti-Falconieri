package it.polimi.ingsw.model.cards;

public abstract class ActionToken
{
	private int quantity;
	private boolean isFlipped;

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public boolean isFlipped()
	{
		return isFlipped;
	}

	public void setFlipped(boolean flipped)
	{
		isFlipped = flipped;
	}
}
