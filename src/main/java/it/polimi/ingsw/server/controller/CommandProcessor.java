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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* TODO: remove CommandProcessor, make 3 classes for the 3 main actions, SetupCommands and LeaderCommands */

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

		for (int i = 0; i < model.getAllLeaderCards().size(); i++)
		{
			if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1))			||
				String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(2))			)
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
		List<ResourceType> resourcesToAdd = new ArrayList<>();		/* Should either be a hashmap or a list of resourcetypes */

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
					controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), 1);
			}
		}

		/* sort resourcesToAdd to put the resoruces in higher amount first [PURPLE, BLUE, YELLOW, BLUE] --> [BLUE, BLUE, PURPLE, YELLOW]. o(n^2) but so few elements it doesn't matter. (thanks SO) */
		resourcesToAdd.sort(Comparator.comparing(i -> Collections.frequency(resourcesToAdd, i)).reversed());

		List<ResourceType> resourcesToDiscard = new ArrayList<>();

		for (int i = 0; i < resourcesToAdd.size(); i++)
		{																/* Gets the storageLeader compatible with the resource to add */
			SkillStorage storageLeader = model.getPlayerByUsername(username).getStorageLeader(resourcesToAdd.get(i));

			if (storageLeader != null && !storageLeader.getAdditionalStorage().isFull())				/* If possible, add the resources to storage leaders */
				model.getPlayerByUsername(username).getStorageLeader(resourcesToAdd.get(i)).addOneResource();

			else if (resourcesToAdd.get(i) != ResourceType.RED)														/* Otherwise add them to storage */
				resourcesToDiscard.add(model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(resourcesToAdd.get(i)));
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
			if (model.getNumPlayers() == 1)		/* If singleplayer match, give Lorenzo #-discarded-resources points */
			{
				System.out.println("Giving Lorenzo " + resourcesToDiscard.size() + " faithpoints");
				model.getTrack().setBlackPawn(model.getTrack().getBlackPawn() + resourcesToDiscard.size());
			}

			else								/* Otherwise give 1 faith point to everyone who's not the current user */
			{
				for (int i = 0; i < model.getNumPlayers(); i++)
				{
					if (!model.getPlayerByID(i).getUsername().equals(username))
					{
						System.out.println("Giving " + username + " " + resourcesToDiscard.size() + " faithpoints");
						controller.updatePlayerPosition(i, resourcesToDiscard.size());
					}
				}
			}

			model.discardResources(resourcesToDiscard.size(), username);		/* Sends DiscardedResourcesMessage to everyone */
		}

		List<Resource> boughtResources = ResourceType.convertResTypeListToResList(resourcesToAdd);			/* B, B, G -> 2B, 1G */
		controller.getView().send(new BoughtResourcesMessage(boughtResources));		/* Sends a list of resources to the client */
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

	public void activateProduction(List<String> command, String username)		/* TODO: add checks if client says something like AAAAAAAAAAAAA */
	{
		List<Resource> producedResources = new ArrayList<>();
		List<Resource> cost = new ArrayList<>();

		switch (command.get(1))
		{
			case "DEFAULT":				/* "ACTIVATE_PRODUCTION", "DEFAULT", "B", "B", "Y" */

				if (command.get(2).equals(command.get(3)))		/* If the resources to convert are the same */
					cost.add(new Resource(ResourceType.convertStringToResType(command.get(2)), 2));	/* Get value from config for parameter editor! */

				else
				{
					cost.add(new Resource(ResourceType.convertStringToResType(command.get(2)), 1));
					cost.add(new Resource(ResourceType.convertStringToResType(command.get(3)), 1));
				}

				ResourceType productResType = ResourceType.convertStringToResType(command.get(4));
				producedResources.add(new Resource(productResType, 1));		/* Convert ResourceType to Resource */

				break;

			case "DEVCARD":				/* "ACTIVATE_PRODUCTION", "DEVCARD", "5" */

				DevCard devCard = model.getPlayerByUsername(username).getDashboard().getTopDevCardByNumber(Integer.parseInt(command.get(2)));
				cost = devCard.getCost();
				producedResources = devCard.getProduct();

				break;

			case "LEADER_SKILL":		/* "ACTIVATE_PRODUCTION", "LEADER_SKILL", "13", "B" */

				LeaderCard leaderCard = model.getPlayerByUsername(username).getLeaderCardByNumber(Integer.parseInt(command.get(2)));

				if (leaderCard.isActive())		/* Gives "productAmount" of chosen resource and "faithpoints" faith points (values set in xmls, default is 1 for both) */
				{
					producedResources.add(new Resource(ResourceType.convertStringToResType(command.get(3)), ((SkillProduction) leaderCard).getProductAmount()));
					producedResources.add(new Resource(ResourceType.RED, ((SkillProduction) leaderCard).getFaithPoints()));
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

			for (int i = 0; i < producedResources.size(); i++)			/* Add produced faith points to player track */
			{
				if (producedResources.get(i).getResourceType() == ResourceType.RED)
					controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), producedResources.get(i).getQuantity());
			}

			model.getPlayerByUsername(username).getDashboard().getVault().addResourceList(producedResources);	/* Vault checks red resources */
			message = "Production successful!";
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
