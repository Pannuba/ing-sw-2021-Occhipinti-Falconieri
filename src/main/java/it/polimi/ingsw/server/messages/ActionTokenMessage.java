package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.cards.ActionToken;

/**
 * @author Giulio Occhipinti
 */

public class ActionTokenMessage implements Message
{
	private final ActionToken token;

	public ActionTokenMessage(ActionToken token)
	{
		this.token = token;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.getActionToken(token);
	}
}
