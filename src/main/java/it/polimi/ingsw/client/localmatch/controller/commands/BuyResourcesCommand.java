package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.SkillStorage;
import it.polimi.ingsw.server.messages.BoughtResourcesMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BuyResourcesCommand        /* "BUY_RESOURCES", "ROW", "2" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		String message = "";
		boolean isFailed = false;		/* How can this action fail? When resources don't fit? */

		List<MarbleType> boughtMarbles = new ArrayList<>();
		List<ResourceType> resourcesToAdd = new ArrayList<>();		/* Should either be a hashmap or a list of resourcetypes */

		List<ResourceType> whiteMarbleTypes = model.getPlayer().getWhiteMarbleTypes();
		ResourceType whiteMarbleRes = null;

		if (whiteMarbleTypes.size() == 1)
			whiteMarbleRes = whiteMarbleTypes.get(0);

		if (whiteMarbleTypes.size() > 1)
			whiteMarbleRes = ResourceType.convertStringToResType(command.get(3));

		if (command.get(1).equals("ROW"))
			boughtMarbles = model.getMarblesMarket().buyMarblesRow(Integer.parseInt(command.get(2)) - 1);		/* - 1 because 0-indexed, [0, 1, 2] */

		if (command.get(1).equals("COLUMN"))
			boughtMarbles = model.getMarblesMarket().buyMarblesCol(Integer.parseInt(command.get(2)) - 1);

		for (int i = 0; i < boughtMarbles.size(); i++)
		{
			if (boughtMarbles.get(i) == MarbleType.WHITE)
			{
				if (whiteMarbleRes != null)
					resourcesToAdd.add(whiteMarbleRes);

				else
					System.out.println(username + "has no active SkillMarble cards!");
			}

			else		/* If it's not a white marble */
			{
				resourcesToAdd.add(ResourceType.convertMarbleToResType(boughtMarbles.get(i)));

				if (boughtMarbles.get(i) == MarbleType.RED)
					controller.updatePlayerPosition(model.getPlayer().getId(), 1);
			}
		}

		/* sort resourcesToAdd to put the resoruces in higher amount first [PURPLE, BLUE, YELLOW, BLUE] --> [BLUE, BLUE, PURPLE, YELLOW]. o(n^2) but so few elements it doesn't matter. (thanks SO) */
		resourcesToAdd.sort(Comparator.comparing(i -> Collections.frequency(resourcesToAdd, i)).reversed());

		List<ResourceType> resourcesToDiscard = new ArrayList<>();

		for (int i = 0; i < resourcesToAdd.size(); i++)
		{																/* Gets the storageLeader compatible with the resource to add */
			SkillStorage storageLeader = model.getPlayer().getStorageLeader(resourcesToAdd.get(i));

			if (storageLeader != null && !storageLeader.getAdditionalStorage().isFull())				/* If possible, add the resources to storage leaders */
				model.getPlayer().getStorageLeader(resourcesToAdd.get(i)).addOneResource();

			else if (resourcesToAdd.get(i) != ResourceType.RED)														/* Otherwise add them to storage */
				resourcesToDiscard.add(model.getPlayer().getDashboard().getStorage().addResourceSmart(resourcesToAdd.get(i)));
			/* Adds the return value of addResourceSmart to resourcesToAdd */
		}

		for (int i = 0; i < resourcesToDiscard.size(); i++)		/* Remove null elements from resourcesToDiscard */
		{
			if (resourcesToDiscard.get(i) == null)
			{
				resourcesToDiscard.remove(i);
				i--;
			}
		}

		if (!resourcesToDiscard.isEmpty())
		{
			System.out.println("Giving Lorenzo " + resourcesToDiscard.size() + " faithpoints");
			model.getTrack().setBlackPawn(model.getTrack().getBlackPawn() + resourcesToDiscard.size());

			model.discardResources(resourcesToDiscard.size(), username);		/* Sends DiscardedResourcesMessage to everyone */
		}

		List<Resource> boughtResources = ResourceType.convertResTypeListToResList(resourcesToAdd);			/* B, B, G -> 2B, 1G */
		controller.getView().update(new BoughtResourcesMessage(boughtResources));		/* Sends a list of resources to the client */

		return isFailed;
	}
}
