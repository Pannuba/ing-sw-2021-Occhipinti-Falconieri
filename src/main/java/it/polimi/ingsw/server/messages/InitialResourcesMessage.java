package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

/**
 * @author Giulio Occhipinti
 */

public class InitialResourcesMessage implements Message
{
	private final int playerID;		/* Included in message to avoid sending an additional gamestate that screws everything up */

	public InitialResourcesMessage(int playerID)
	{
		this.playerID = playerID;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.initialResources(playerID);
	}
}
