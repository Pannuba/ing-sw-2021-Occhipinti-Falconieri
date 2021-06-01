package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.cards.ActionToken;

import java.io.Serializable;

public class ActionTokenMessage implements Message, Serializable
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
