package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class ActivateLeaderCommand			/* "ACTIVATE_LEADER", "4" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		String message = "";
		boolean isFailed = false;

		int cardToActivateNum = Integer.parseInt(command.get(1));

		LeaderCard cardToActivate = model.getPlayer().getLeaderCardByNumber(cardToActivateNum);		/* TODO: add check if this action fails */

		if (cardToActivate.checkRequirements(model.getPlayer()))
		{
			if (!cardToActivate.isDiscarded())			/* Discarded leadercards can't be activated */
			{
				model.getPlayer().getLeaderCardByNumber(cardToActivateNum).setActive(true);
				message = "Leadercard " + model.getPlayer().getLeaderCardByNumber(cardToActivateNum).getCardNumber() + " activated successfully!";
				isFailed = false;
			}

			/* else? */
		}

		else
		{
			message = "Couldn't activate leader: requirements not satisfied";
			isFailed = false;
		}

		controller.getView().update(new OperationResultMessage(message, isFailed));
		return isFailed;
	}
}
