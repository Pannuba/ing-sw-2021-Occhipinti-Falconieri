package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.BoughtResourcesMessage;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.ArrayList;
import java.util.List;

public class ActivateProductionCommand implements Command
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
	{
		String message = "";
		boolean isFailed = false;

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

				if (devCard == null)
				{
					controller.getView().send(new OperationResultMessage("You don't own that dev card, or it's not on the top of your areas!", true));
					return true;
				}

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
					return false;
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

		return isFailed;
	}
}
