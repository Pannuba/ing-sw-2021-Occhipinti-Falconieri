package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.OperationResultMessage;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class ActivateLeaderCommand implements Command		/* "ACTIVATE_LEADER", "4" */
{
	private final Model model;
	private final Controller controller;
	private final String username;

	public ActivateLeaderCommand(Controller controller)
	{
		this.controller = controller;
		model = controller.getModel();
		username = controller.getUsername();
	}

	@Override
	public boolean run(List<String> command)
	{
		String message = "";
		boolean isFailed = false;

		int cardToActivateNum = Integer.parseInt(command.get(1));

		LeaderCard cardToActivate = model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum);

		if (cardToActivate.checkRequirements(model.getPlayerByUsername(username)))
		{
			if (!cardToActivate.isDiscarded())			/* Discarded leadercards can't be activated */
			{
				model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).setActive(true);
				message = "Leadercard " + model.getPlayerByUsername(username).getLeaderCardByNumber(cardToActivateNum).getCardNumber() + " activated successfully!";
				isFailed = false;
			}

			/* Else? should never be possible I think */
		}

		else
		{
			message = "Couldn't activate leader: requirements not satisfied";
			isFailed = false;		/* isFailed is false because the CLI will desync otherwise when it reaches the next input, messages will queue up and mess everything up */
		}

		controller.getView().send(new OperationResultMessage(message, isFailed));
		return isFailed;
	}
}
