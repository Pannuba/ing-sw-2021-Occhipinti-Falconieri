package it.polimi.ingsw.client.model;

public class Resource
{
	private ResourceType category;
	private int quantity;
	private boolean isInVault;

	public ResourceType getResourceType()
	{
		return category;
	}

	public void setResourceType(ResourceType category)
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

	public boolean isInVault()
	{
		return isInVault;
	}

	public void setInVault(boolean inVault)
	{
		isInVault = inVault;
	}
}
