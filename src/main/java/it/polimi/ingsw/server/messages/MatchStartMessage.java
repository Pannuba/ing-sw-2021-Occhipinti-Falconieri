package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;


import java.io.Serializable;

public class MatchStartMessage implements Message, Serializable
{

	@Override
	public void process(MessageExecutor action)
	{
		action.startMatch();
	}
}
