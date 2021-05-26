package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.BoughtDevCardMessage;
import it.polimi.ingsw.server.messages.BoughtResourcesMessage;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandProcessor        /* Contains the code that runs when a certain command is received */
{
	private final Model model;
	private final Controller controller;		/* To access its functions, command, view and username */
	private String message;

	public CommandProcessor(Model model, Controller controller)
	{
		this.model = model;
		this.controller = controller;
		message = "";
	}

	public void selectLeaderCards(List<String> command, String username)
	{
		List<LeaderCard> cards = new ArrayList<>();

		for (int i = 0; i < 16; i++)
		{
			if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1))			||
					String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(2))		)
				cards.add(model.getAllLeaderCards().get(i));
		}

		model.getPlayerByUsername(username).setLeaderCards(cards);
	}

	public void initialResources(List<String> command, String username)		/* No need to reply with any message */
	{
		if (command.get(1).isEmpty() == false)
		{
			for (int i = 0; i < command.get(1).length(); i++)
			{
				Resource resourceToAdd = new Resource();
				resourceToAdd.setQuantity(1);
				resourceToAdd.setResourceType(ResourceType.convertStringToResType(Character.toString(command.get(1).charAt(i))));
				model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(resourceToAdd);
			}	/* TODO: change to (ResourceType, quantity)! Or pass a new Resource with a new constructor to set ResourceType and quantity */
		}

		if (command.get(2).isEmpty() == false)		/* If there are initial faithpoints, get that player's pawn and move it */
			controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), Integer.parseInt(command.get(2)));
	}

	public void activateLeader(List<String> command, String username)
	{
		int cardToActivateNum = Integer.parseInt(command.get(1));

		LeaderCard cardToActivate = model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum);

		if (controller.checkLeaderRequirements(model.getPlayerByUsername(username).getDashboard(), cardToActivate))
		{
			if (cardToActivate.isDiscarded() == false)			/* Discarded leadercards can't be activated. Remove discarded cards from client choices? */
			{
				model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).setActive(true);
				message = "Leadercard " + model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).getCardNumber() + " activated successfully!";
			}
		}

		else
			message = "Couldn't activate leader: requirements not satisfied";

		controller.getView().send(new OperationResultMessage(message));
	}

	public void discardLeader(List<String> command, String username)	/* Activated leadercards can't be discarded. Remove active cards from client choices? */
	{
		int cardToDiscardNum = Integer.parseInt(command.get(1));

		LeaderCard cardToDiscard = model.getPlayerByUsername(username).getLeaderCardByNumber(cardToDiscardNum);

		if (!cardToDiscard.isActive())
		{
			model.getPlayerByUsername(username).getLeaderCardByNumber(cardToDiscardNum).setDiscarded(true);
			controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), 1);
			message = "Leadercard " + cardToDiscard.getCardNumber() + " discarded successfully! Gained 1 faith point";
		}

		controller.getView().send(new OperationResultMessage(message));
	}

	public void buyResources(List<String> command, String username)
	{
		List<MarbleType> boughtMarbles = new ArrayList<>();
		List<ResourceType> resourcesToAddToStorage = new ArrayList<>();		/* Should either be a hashmap or a list of resourcetypes */

		if (command.get(1).equals("ROW"))
			boughtMarbles = model.getMarblesMarket().buyMarblesRow(Integer.parseInt(command.get(2)) - 1);		/* - 1 because 0-indexed, [0, 1, 2] */

		if (command.get(1).equals("COLUMN"))
			boughtMarbles = model.getMarblesMarket().buyMarblesCol(Integer.parseInt(command.get(2))- 1);

		for (int i = 0; i < boughtMarbles.size(); i++)
		{
			if (boughtMarbles.get(i) == MarbleType.RED)
				controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), 1);

			else if (boughtMarbles.get(i) == MarbleType.WHITE)
			{
				List<ResourceType> whiteMarbleTypes = model.getPlayerByUsername(username).getWhiteMarbleTypes();    /* or model.getWhiteMarbleTypes(username */

				if (whiteMarbleTypes.size() == 1)		/* If there's only 1 active SkillMarble */
					resourcesToAddToStorage.add(whiteMarbleTypes.get(0));

				else if (whiteMarbleTypes.size() > 1)
				{
					/* Ask client which resourcetype they want to pick to convert white marbles */
				}

				else
					System.out.println(username + "has no active SkillMarble cards!");
			}

			else
				resourcesToAddToStorage.add(ResourceType.convertMarbleToResType(boughtMarbles.get(i)));
		}

		/* TODO: add resources to storage. [BLUE PURPLE BLUE]. Sort hashmap? List? */

		HashMap<ResourceType, Integer> resourcesMap = ResourceType.convertResTypeListToHashMap(resourcesToAddToStorage);

		//if (resourcesMap.get(ResourceType.BLUE) != 0)
			//model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(/* new Resource(ResourceType.BLUE, resourcesMap.get(ResourceType.BLUE) */);


		controller.getView().send(new BoughtResourcesMessage(resourcesToAddToStorage));		/* Sends a list of resourceType to the client */
	}

	public void buyDevCard(List<String> command, String username)
	{
		int cardToBuyNum = Integer.parseInt(command.get(1));
		DevCard cardToBuy = model.getDevCardsMarket().getDevCardByNumber(cardToBuyNum);

		if (controller.checkDevCardRequirements(model.getPlayerByUsername(username).getDashboard(), cardToBuy))
		{
			model.getDevCardsMarket().buyCardFromMarket(cardToBuyNum);		/* Returns a DevCard but we already have it... Change to void? */
			controller.getView().send(new BoughtDevCardMessage(cardToBuy));
			/* TODO: remove required resources from storage first, vault if storage doesn't have them */
		}

		else
			controller.getView().send(new OperationResultMessage("Couldn't buy devcard: requirements not satisfied"));
	}
}
