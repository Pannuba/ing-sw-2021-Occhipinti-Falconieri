package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;

import java.io.Serializable;

public class DiscardedResourcesMessage implements Message, Serializable
{
	private final int discardedResNum;
	private final String playerWhoDiscarded;

	public DiscardedResourcesMessage(int discardedResNum, String playerWhoDiscarded)
	{
		this.discardedResNum = discardedResNum;
		this.playerWhoDiscarded = playerWhoDiscarded;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.getDiscardedResources(discardedResNum, playerWhoDiscarded);
	}
}
