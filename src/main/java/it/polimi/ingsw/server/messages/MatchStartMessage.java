package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionExecutor;


import java.io.Serializable;

public class MatchStartMessage implements Message, Serializable
{

	@Override
	public void process(ActionExecutor action)
	{
		action.startMatch();
	}
}
