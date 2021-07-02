package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Vault of each dashboard
 * Has resources obtained through production
 * @author Giulio Ochhipinti
 */

public class Vault implements Serializable
{
	private HashMap<ResourceType, Integer> resourceAmounts;

	/**
	 * Creates the hashMap of all resources and sets the quantity to 0
	 */

	public Vault()
	{
		resourceAmounts = new HashMap<>();

		resourceAmounts.put(ResourceType.BLUE,	 0);
		resourceAmounts.put(ResourceType.GREY,	 0);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 0);
	}

	/**
	 * Adds a resource based on its type
	 * @param resourceToAdd the resource to add to the vault
	 */

	public void addResource(ResourceType resourceToAdd)
	{
		resourceAmounts.put(resourceToAdd, resourceAmounts.get(resourceToAdd) + 1);
	}

	/**
	 * Adds a list of resources by calling the addResource function for each resource to be added. Used by the production action
	 * @param resourcesToAdd the list of resources to add to the vault
	 */

	public void addResourceList(List<Resource> resourcesToAdd)
	{
		for (int i = 0; i < resourcesToAdd.size(); i++)
		{
			if (resourcesToAdd.get(i).getResourceType() != ResourceType.RED)		/* Don't add red resources to vault */
			{
				for (int j = 0; j < resourcesToAdd.get(i).getQuantity(); j++)
					addResource(resourcesToAdd.get(i).getResourceType());
			}
		}
	}

	/**
	 * Removes multiple resources of the same type one at a time
	 * @return number of removed resources
	 */

	public int removeResource(Resource resourceToRemove)
	{
		int removedResNum = 0;

		for (int i = 0; i < resourceToRemove.getQuantity(); i++)
		{
			if (resourceAmounts.get(resourceToRemove.getResourceType()) > 0)
			{
				resourceAmounts.put(resourceToRemove.getResourceType(), resourceAmounts.get(resourceToRemove.getResourceType()) - 1);
				removedResNum++;
			}
		}

		System.out.println("Vault removeResource: removed " + removedResNum + " " + resourceToRemove.getResourceType());
		return removedResNum;
	}

	/**
	 * Gets the total amount of resources stored in the vault.
	 * Used to calculate the final score, to activate a leaderCard or to purchase a devCard
	 * @return the total number of resources
	 */

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
