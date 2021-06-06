package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

public class DiscardLeaderCommand implements Command		/* "DISCARD_LEADER", "13" */
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
	{
		String message = "";
		boolean isFailed = false;

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

		return isFailed;
	}
}
