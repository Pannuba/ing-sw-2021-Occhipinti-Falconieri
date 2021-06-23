package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Player;

public class ProductionResultMessage implements Message
{
	private final Player player;		/*	(Only send storage?) new player with the updated isUsedForProduction on cards and without the used and produced resources
											because they can't use the resources hey just produced to perform another production */
	/* This message is always sent after every production action, successful or not. Onyl when the player chooses to stop the production the rounds will continue */

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
