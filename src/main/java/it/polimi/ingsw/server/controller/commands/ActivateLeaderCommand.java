package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

public class ActivateLeaderCommand implements Command		/* "ACTIVATE_LEADER", "4" */
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
	{
		String message = "";
		boolean isFailed = false;

		int cardToActivateNum = Integer.parseInt(command.get(1));

		LeaderCard cardToActivate = model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum);		/* TODO: add check if this action fails */

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
		return isFailed;
	}
}
