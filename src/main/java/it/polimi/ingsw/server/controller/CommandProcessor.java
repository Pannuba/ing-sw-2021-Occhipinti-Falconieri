package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.server.messages.BoughtDevCardMessage;
import it.polimi.ingsw.server.messages.BoughtResourcesMessage;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.ArrayList;
import java.util.List;

public class CommandProcessor			/* Contains the code that runs when a certain command is received */
{
	private final Model model;
	private final Controller controller;		/* To access its functions, command, view and username */
	private String message;
	private boolean isFailed;		/* Becomes true if something bad happens while processing the commands */

	public CommandProcessor(Model model, Controller controller)
	{
		this.model = model;
		this.controller = controller;

		message = "";
		isFailed = false;
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
		if (!command.get(1).isEmpty())
		{
			for (int i = 0; i < command.get(1).length(); i++)
			{
				ResourceType resourceToAdd = ResourceType.convertStringToResType(Character.toString(command.get(1).charAt(i)));
				model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(resourceToAdd);
			}
		}

		if (!command.get(2).isEmpty())		/* If there are initial faithpoints, get that player's pawn and move it */
			controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), Integer.parseInt(command.get(2)));
	}

	public void activateLeader(List<String> command, String username)
	{
		int cardToActivateNum = Integer.parseInt(command.get(1));

		LeaderCard cardToActivate = model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum);

		if (cardToActivate.checkRequirements(model.getPlayerByUsername(username).getDashboard()))
		{
			if (!cardToActivate.isDiscarded())			/* Discarded leadercards can't be activated */
			{
				model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).setActive(true);
				message = "Leadercard " + model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).getCardNumber() + " activated successfully!";
				isFailed = false;
			}

			/* else? */
		}

		else
		{
			message = "Couldn't activate leader: requirements not satisfied";
			isFailed = true;
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));
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
			isFailed = false;
		}

		else
		{
			message = "Failed to discard leadercard";
			isFailed = true;
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));
	}

	public void buyResources(List<String> command, String username)
	{
		isFailed = false;		/* How can this action fail? When resources don't fit? */
		List<MarbleType> boughtMarbles = new ArrayList<>();
		List<ResourceType> resourcesToAddToStorage = new ArrayList<>();		/* Should either be a hashmap or a list of resourcetypes */

		List<ResourceType> whiteMarbleTypes = model.getPlayerByUsername(username).getWhiteMarbleTypes();
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
			if (boughtMarbles.get(i) == MarbleType.RED)
				controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), 1);

			else if (boughtMarbles.get(i) == MarbleType.WHITE)
			{
				if (whiteMarbleRes != null)
					resourcesToAddToStorage.add(whiteMarbleRes);

				else
					System.out.println(username + "has no active SkillMarble cards!");
			}

			else
				resourcesToAddToStorage.add(ResourceType.convertMarbleToResType(boughtMarbles.get(i)));
		}

		List<Resource> boughtResourcesList = ResourceType.convertResTypeListToResList(resourcesToAddToStorage);			/* B, B, G -> 2B, 1G */

		for (int i = 0; i < resourcesToAddToStorage.size(); i++)
		{																/* Gets the storageLeader compatible with the resource to add */
			SkillStorage storageLeader = model.getPlayerByUsername(username).getStorageLeader(resourcesToAddToStorage.get(i));

			if (storageLeader != null && !storageLeader.getAdditionalStorage().isFull())				/* If possible, add the resources to storage leaders */
				model.getPlayerByUsername(username).getStorageLeader(resourcesToAddToStorage.get(i)).addOneResource();

			else																						/* Otherwise add them to storage */
				model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(resourcesToAddToStorage.get(i));
		}

		controller.getView().send(new BoughtResourcesMessage(boughtResourcesList));		/* Sends a list of resources to the client */
		/* TODO: ask player to discard resources if they don't fit in the storage */
	}

	public void buyDevCard(List<String> command, String username)
	{
		int cardToBuyNum = Integer.parseInt(command.get(1));
		int devCardAreaIndex = Integer.parseInt(command.get(2)) - 1;		/* If the client chooses the area #3, it's #2 because 0-indexed */
		DevCard cardToBuy = model.getDevCardsMarket().getDevCardByNumber(cardToBuyNum);
		List<Resource> requirements = cardToBuy.getRequirements();			/* The resources to spend to buy the devcard */

		for (int i = 0; i < requirements.size(); i++)		/* Apply the discount of SkillDiscount cards, if any */
		{
			SkillDiscount discountLeader = model.getPlayerByUsername(username).getDiscountLeader(requirements.get(i).getResourceType());

			if (discountLeader != null)		/* No need to worry about cards being used multiple times because of resource type in requirements */
				requirements.get(i).setQuantity(requirements.get(i).getQuantity() - discountLeader.getDiscountNum());
		}

		if (controller.checkResourceAmounts(model.getPlayerByUsername(username).getDashboard(), requirements))	/* If player has enough resources */
		{
			controller.spendResources(requirements);

			model.getDevCardsMarket().buyCardFromMarket(cardToBuyNum);		/* Finally buy the card and send it to the client */
			model.getPlayerByUsername(username).getDashboard().addDevCardToArea(cardToBuy, devCardAreaIndex);	/* TODO: check if layer is compatible */
			controller.getView().send(new BoughtDevCardMessage(cardToBuy));

			message = "Card bought successfully!";
			isFailed = false;
		}

		else
		{
			message = "Couldn't buy devcard: requirements not satisfied";
			isFailed = true;
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));
	}

	public void activateProduction(List<String> command, String username)
	{
		List<Resource> producedResources = new ArrayList<>();
		List<Resource> cost = new ArrayList<>();

		switch (command.get(1))
		{
			case "DEFAULT":				/* "ACTIVATE_PRODUCTION", "DEFAULT", "B", "Y" */

				cost.add(new Resource(ResourceType.convertStringToResType(command.get(2)), 2));	/* Get value from config for parameter editor! */

				ResourceType productResType = ResourceType.convertStringToResType(command.get(3));
				producedResources.add(new Resource(productResType, 1));		/* Convert ResourceType to Resource */

				break;

			case "DEVCARD":				/* "ACTIVATE_PRODUCTION", "DEVCARD", "5" */

				DevCard devCard = model.getPlayerByUsername(username).getDashboard().getTopDevCardByNumber(Integer.parseInt(command.get(2)));
				cost = devCard.getCost();
				producedResources = devCard.getProduct();

				break;

			case "LEADER_SKILL":		/* "ACTIVATE_PRODUCTION", "LEADER_SKILL", "13", "B" */

				LeaderCard leaderCard = model.getPlayerByUsername(username).getLeaderCardByNumber(Integer.parseInt(command.get(2)));

				if (leaderCard.isActive())
				{
					producedResources.add(new Resource(ResourceType.convertStringToResType(command.get(3)), 1));
					cost.add(((SkillProduction) leaderCard).getCost());
				}

				else
				{
					controller.getView().send(new OperationResultMessage("Can't activate production: leader not active", true));
					return;
				}

				break;
		}

		if (controller.checkResourceAmounts(model.getPlayerByUsername(username).getDashboard(), cost))
		{
			controller.spendResources(cost);
			model.getPlayerByUsername(username).getDashboard().getVault().addResourceList(producedResources);
			message = "Production successful!";		/* Send BoughtResourcesMessage before or after OpResult? Another boolean? */
			isFailed = false;
		}

		else
		{
			message = "Couldn't activate production: requirements not satisfied.";
			isFailed = true;
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));

		if (!isFailed)
			controller.getView().send(new BoughtResourcesMessage(producedResources));
	}

	public boolean isFailed()
	{
		return isFailed;
	}
}
