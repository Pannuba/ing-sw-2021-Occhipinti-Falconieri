package model;

/*	Stone --> grey ---> 0
	Coin --> yellow --> 1
	Shield --> blue --> 2
	Slave --> purple -> 3
 */

public class Resource
{
	private ResourceType category;				/* Rename category to type? */
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
