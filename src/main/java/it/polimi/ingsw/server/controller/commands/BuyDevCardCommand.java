package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.SkillDiscount;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.BoughtDevCardMessage;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

public class BuyDevCardCommand implements Command
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
	{
		String message; boolean isFailed;
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
		return isFailed;
	}
}
