package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;

/**
 * Specify the skill of the leader card: storage
 * Requires five resources of same type
 * Gives an additional storage for two resources of same type
 * @author Giulio Occhipinti
 */

public class SkillStorage extends LeaderCard		/* "final" keyword in instance variables makes serialization not work */
{
	private Resource requirements;			/* Only 1 resource type */
	private Shelf additionalStorage;

	public SkillStorage()
	{
		requirements = new Resource();			/* shelfSize and resource type are not specified because the value is in the card's xml */
		additionalStorage = new Shelf();
	}

	@Override
	public boolean checkRequirements(Player player)		/* Player has to have x resources of the same type. Resources are not spent */
	{
		Resource storageRequirements = this.getRequirements();
		Storage storage = player.getDashboard().getStorage();
		Vault vault = player.getDashboard().getVault();

		int reqResourceQuantity = 0;
		reqResourceQuantity += storage.findResourceByType(storageRequirements.getResourceType());

		if (player.getStorageLeader(storageRequirements.getResourceType()) != null)
			reqResourceQuantity += player.getStorageLeader(storageRequirements.getResourceType()).getAdditionalStorage().getShelfResourceQuantity();

		reqResourceQuantity += vault.getResourceAmounts().get(storageRequirements.getResourceType());

		if (reqResourceQuantity >= storageRequirements.getQuantity())
			return true;

		else
			return false;
	}

	/**
	 * Increases the amount of resources in the additional storage by one
	 */

	public void addOneResource()			/* Increases the amount of resources in the additionalStorage by 1. Checks already performed by controller */
	{
		additionalStorage.getShelfResource().setQuantity(additionalStorage.getShelfResourceQuantity() + 1);
	}

	/**
	 * Removes resources from the additional storage
	 */

	public int removeResources(int resToRemoveNum)
	{
		int removedResNum = 0;

		for (int i = 0; i < resToRemoveNum; i++)
		{
			if (!additionalStorage.isEmpty())		/* To avoid going below zero */
			{
				additionalStorage.getShelfResource().setQuantity(additionalStorage.getShelfResourceQuantity() - 1);
				removedResNum++;
			}

			else
				System.out.println("SkillStorage: tried to remove a resource when the shelf was empty!");
		}

		System.out.println("SkillStorage removeResources: removed " + removedResNum + " " + additionalStorage.getShelfResourceType());
		return removedResNum;
	}

	public Resource getRequirements()
	{
		return requirements;
	}

	public void setRequirements(Resource requirements)
	{
		this.requirements = requirements;
	}

	public Shelf getAdditionalStorage()
	{
		return additionalStorage;
	}

	public void setAdditionalStorage(Shelf shelf)
	{
		this.additionalStorage = shelf;
	}
}
