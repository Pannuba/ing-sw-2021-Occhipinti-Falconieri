package it.polimi.ingsw.model;

import java.io.Serializable;

/*	Stone --> grey ---> 0
	Coin --> yellow --> 1
	Shield --> blue --> 2
	Slave --> purple -> 3
 */

public class Resource implements Serializable
{
	private ResourceType resourceType;				/* Can be null, see DevCard serialization */
	private int quantity;
	private boolean isInVault;

	public Resource (ResourceType resourceType, int quantity)
	{
		this.resourceType = resourceType;
		this.quantity = quantity;
	}

	public Resource()
	{
		resourceType = null;
		quantity = 0;
		isInVault = false;
	}

	public ResourceType getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType)
	{
		this.resourceType = resourceType;
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
