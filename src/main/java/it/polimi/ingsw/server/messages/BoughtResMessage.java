package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;
import java.util.List;

public class BoughtResMessage implements Message, Serializable
{
	private List<ResourceType> boughtResources;

	public BoughtResMessage(List<ResourceType> boughtResources)
	{
		this.boughtResources = boughtResources;
	}

	@Override
	public void process(CLI cli)
	{
		cli.getBoughtResources(boughtResources);
	}
}
