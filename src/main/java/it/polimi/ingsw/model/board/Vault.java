package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Vault implements Serializable		/* Has resources obtained through production */
{
	private HashMap<ResourceType, Integer> resourceAmounts;

	public Vault()
	{
		resourceAmounts = new HashMap<>();

		resourceAmounts.put(ResourceType.BLUE,	 0);
		resourceAmounts.put(ResourceType.GREY,	 0);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 0);
	}

	public void addResource(ResourceType resourceToAdd)
	{
		resourceAmounts.put(resourceToAdd, resourceAmounts.get(resourceToAdd) + 1);
	}

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

	public int removeResource(Resource resourceToRemove)
	{
		int removedResNum = 0;

		for (int i = 0; i < resourceToRemove.getQuantity(); i++)		/* Remove resources one at a time */
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
