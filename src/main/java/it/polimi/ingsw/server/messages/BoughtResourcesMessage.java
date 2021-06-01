package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.ActionExecutor;
import it.polimi.ingsw.model.Resource;

import java.io.Serializable;
import java.util.List;

public class BoughtResourcesMessage implements Message, Serializable
{
	private final List<Resource> boughtResources;

	public BoughtResourcesMessage(List<Resource> boughtResources)
	{
		this.boughtResources = boughtResources;
	}

	@Override
	public void process(ActionExecutor action)
	{
		action.getBoughtResources(boughtResources);
	}
}
