package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class DiscardLeaderCommand implements Command		/* "DISCARD_LEADER", "13" */
{
	private final Model model;
	private final Controller controller;
	private final String username;

	public DiscardLeaderCommand(Controller controller)
	{
		this.controller = controller;
		model = controller.getModel();
		username = controller.getUsername();
	}

	@Override
	public boolean run(List<String> command)
	{
		String message = "";
		boolean isFailed;

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
