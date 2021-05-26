package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;
import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;
import java.util.List;

public class BoughtResourcesMessage implements Message, Serializable
{
	private final List<ResourceType> boughtResources;

	public BoughtResourcesMessage(List<ResourceType> boughtResources)
	{
		this.boughtResources = boughtResources;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.getBoughtResources(boughtResources);
	}
}
