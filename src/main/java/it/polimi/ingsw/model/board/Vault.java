package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;
import java.util.HashMap;

public class Vault implements Serializable		/* Has resources obtained through production */
{
	private HashMap<ResourceType, Integer> resourceAmounts;

	public Vault()
	{
		resourceAmounts = new HashMap<ResourceType, Integer>();

		resourceAmounts.put(ResourceType.BLUE,	 0);
		resourceAmounts.put(ResourceType.GREY,	 0);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 0);
	}

	public void addResource(ResourceType resourceToAdd)
	{
		resourceAmounts.put(resourceToAdd, resourceAmounts.get(resourceToAdd) + 1);
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		totalResources += resourceAmounts.get(ResourceType.BLUE);
		totalResources += resourceAmounts.get(ResourceType.GREY);
		totalResources += resourceAmounts.get(ResourceType.YELLOW);
		totalResources += resourceAmounts.get(ResourceType.PURPLE);

		return totalResources;
	}

	public HashMap<ResourceType, Integer> getResourceAmounts()
	{
		return resourceAmounts;
	}

	public void setResourceAmounts(HashMap<ResourceType, Integer> resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}
}
