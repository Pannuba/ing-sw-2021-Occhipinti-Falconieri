package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Resource class
 * @author Giulio Occhipinti
 */

public class Resource implements Serializable
{
	private ResourceType resourceType;				/* Can be null, see DevCard serialization */
	private int quantity;

	/**
	 * Constructor
	 * @param resourceType the resource type of the resource that's going to be created
	 * @param quantity the amount of resources of the same type
	 */
	public Resource (ResourceType resourceType, int quantity)
	{
		this.resourceType = resourceType;
		this.quantity = quantity;
	}

	public Resource()
	{
		resourceType = null;
		quantity = 0;
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
}
