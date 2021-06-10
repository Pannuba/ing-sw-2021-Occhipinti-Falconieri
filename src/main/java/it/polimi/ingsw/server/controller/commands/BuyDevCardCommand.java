package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.SkillDiscount;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class BuyDevCardCommand implements Command		/* "BUY_DEVCARD", "card#", "devCardArea#" */
{
	private final Model model;
	private final Controller controller;
	private final String username;

	public BuyDevCardCommand(Controller controller)
	{
		this.controller = controller;
		model = controller.getModel();
		username = controller.getUsername();
	}

	@Override
	public boolean run(List<String> command)
	{
		String message; boolean isFailed;		/* TODO: check if player wants to buy card # 9327082 */
		int cardToBuyNum = Integer.parseInt(command.get(1));
		int devCardAreaIndex = Integer.parseInt(command.get(2)) - 1;		/* If the client chooses the area #3, it's #2 because 0-indexed */
		DevCard cardToBuy = model.getDevCardsMarket().getDevCardByNumber(cardToBuyNum);
		List<Resource> requirements = cardToBuy.getRequirements();			/* The resources to spend to buy the devcard */

		for (int i = 0; i < requirements.size(); i++)		/* Apply the discount of SkillDiscount cards, if any */
		{
			SkillDiscount discountLeader = model.getPlayerByUsername(username).getDiscountLeader(requirements.get(i).getResourceType());

			if (discountLeader != null)		/* No need to worry about cards being used multiple times because of resource type in requirements */
			{
				System.out.println(username + " is using the active SkillDiscount card's ability!");
				requirements.get(i).setQuantity(requirements.get(i).getQuantity() - discountLeader.getDiscountNum());
			}

			else System.out.println(username + " has no active SkillDiscount cards!");		/* TODO: add in local controller */
		}

		if (model.getPlayerByUsername(username).getDashboard().getDevCardAreas()[devCardAreaIndex].getLayer() != (cardToBuy.getLevel() - 1))
		{
			message = "Couldn't buy devcard: target area not compatible";
			isFailed = true;
		}

		else
		{
			if (controller.checkResourceAmounts(model.getPlayerByUsername(username), requirements))    /* If player has enough resources */
			{
				controller.spendResources(requirements);

				model.getDevCardsMarket().buyCardFromMarket(cardToBuyNum);        /* Finally buy the card and send it to the client */
				model.getPlayerByUsername(username).getDashboard().addDevCardToArea(cardToBuy, devCardAreaIndex);

				message = "Card # " + cardToBuy.getCardNumber() + " bought successfully!";
				isFailed = false;
			}

			else
			{
				message = "Couldn't buy devcard: requirements not satisfied";
				isFailed = true;
			}
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));
		return isFailed;
	}
}
