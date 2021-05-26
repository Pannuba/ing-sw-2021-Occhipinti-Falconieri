package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;
import it.polimi.ingsw.model.cards.ActionToken;

import java.io.Serializable;

public class ActionTokenMessage implements Message, Serializable
{
	private ActionToken token;

	public ActionTokenMessage(ActionToken token)
	{
		this.token = token;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.getActionToken(token);
	}
}
