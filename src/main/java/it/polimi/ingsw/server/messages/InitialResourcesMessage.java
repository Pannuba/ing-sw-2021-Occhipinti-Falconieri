package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.ActionExecutor;

import java.io.Serializable;

public class InitialResourcesMessage implements Message, Serializable
{
	private final int playerID;		/* Included in message to avoid sending an additional gamestate that screws everything up */

	public InitialResourcesMessage(int playerID)
	{
		this.playerID = playerID;
	}

	@Override
	public void process(ActionExecutor action)
	{
		action.chooseResources(playerID);
	}
}
