package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Resource;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class BoughtResourcesMessage implements Message
{
	private final List<Resource> boughtResources;

	public BoughtResourcesMessage(List<Resource> boughtResources)
	{
		this.boughtResources = boughtResources;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.getBoughtResources(boughtResources);
	}
}
