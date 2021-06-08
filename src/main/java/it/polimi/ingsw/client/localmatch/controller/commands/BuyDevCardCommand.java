package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.SkillDiscount;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class BuyDevCardCommand	       /* "BUY_DEVCARD", "card#", "devCardArea#" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		String message; boolean isFailed;
		int cardToBuyNum = Integer.parseInt(command.get(1));
		int devCardAreaIndex = Integer.parseInt(command.get(2)) - 1;		/* If the client chooses the area #3, it's #2 because 0-indexed */
		DevCard cardToBuy = model.getDevCardsMarket().getDevCardByNumber(cardToBuyNum);
		List<Resource> requirements = cardToBuy.getRequirements();			/* The resources to spend to buy the devcard */

		for (int i = 0; i < requirements.size(); i++)		/* Apply the discount of SkillDiscount cards, if any */
		{
			SkillDiscount discountLeader = model.getPlayer().getDiscountLeader(requirements.get(i).getResourceType());

			if (discountLeader != null)		/* No need to worry about cards being used multiple times because of resource type in requirements */
				requirements.get(i).setQuantity(requirements.get(i).getQuantity() - discountLeader.getDiscountNum());
		}

		if (model.getPlayer().getDashboard().getDevCardAreas()[devCardAreaIndex].getLayer() != (cardToBuy.getLevel() - 1))
		{
			message = "Couldn't buy devcard: target area not compatible";
			isFailed = true;
		}

		else
		{
			if (controller.checkResourceAmounts(model.getPlayer(), requirements))    /* If player has enough resources */
			{
				controller.spendResources(requirements);

				model.getDevCardsMarket().buyCardFromMarket(cardToBuyNum);        /* Finally buy the card and send it to the client */
				model.getPlayer().getDashboard().addDevCardToArea(cardToBuy, devCardAreaIndex);

				message = "Card # " + cardToBuy.getCardNumber() + " bought successfully!";
				isFailed = false;
			}

			else
			{
				message = "Couldn't buy devcard: requirements not satisfied";
				isFailed = true;
			}
		}

		controller.getView().update(new OperationResultMessage(message, isFailed));
		return isFailed;
	}
}
