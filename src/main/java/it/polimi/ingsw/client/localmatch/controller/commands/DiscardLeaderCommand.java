package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class DiscardLeaderCommand			/* "DISCARD_LEADER", "13" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		String message = "";
		boolean isFailed = false;

		int cardToDiscardNum = Integer.parseInt(command.get(1));

		LeaderCard cardToDiscard = model.getPlayer().getLeaderCardByNumber(cardToDiscardNum);

		if (!cardToDiscard.isActive())
		{
			model.getPlayer().getLeaderCardByNumber(cardToDiscardNum).setDiscarded(true);
			controller.updatePlayerPosition(model.getPlayer().getId(), 1);
			message = "Leadercard " + cardToDiscard.getCardNumber() + " discarded successfully! Gained 1 faith point";
			isFailed = false;
		}

		else
		{
			message = "Failed to discard leadercard";
			isFailed = true;
		}

		controller.getView().update(new OperationResultMessage(message, isFailed));

		return isFailed;
	}
}
