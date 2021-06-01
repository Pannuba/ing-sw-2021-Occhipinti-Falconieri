package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionCLI;


import java.io.Serializable;

public class MatchStartMessage implements Message, Serializable
{

	@Override
	public void process(ActionCLI action)
	{
		action.startMatch();
	}
}
