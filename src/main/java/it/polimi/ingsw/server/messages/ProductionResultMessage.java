package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Player;

public class ProductionResultMessage implements Message
{
	private final Player player;		/*	new player with the updated isUsedForProduction on cards and without the used and produced resources
											because they can't use the resources hey just produced to perform another production */

	public ProductionResultMessage(Player player)
	{
		this.player = player;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.productionResult(player);
	}
}
