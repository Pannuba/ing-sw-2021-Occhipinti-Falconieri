package it.polimi.ingsw.model.cards;

public abstract class ActionToken
{
	private int quantity;
	private boolean isActive;

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean active)
	{
		isActive = active;
	}
}
