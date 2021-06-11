package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

/**
 * @author Giulio Occhipinti
 */

public class DiscardedResourcesMessage implements Message
{
	private final int discardedResNum;
	private final String playerWhoDiscarded;

	public DiscardedResourcesMessage(int discardedResNum, String playerWhoDiscarded)
	{
		this.discardedResNum = discardedResNum;
		this.playerWhoDiscarded = playerWhoDiscarded;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.getDiscardedResources(discardedResNum, playerWhoDiscarded);
	}
}
