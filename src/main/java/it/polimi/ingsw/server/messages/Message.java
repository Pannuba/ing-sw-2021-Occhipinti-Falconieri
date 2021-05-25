package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;

public interface Message       /* TODO: BuyResourcesMessage implements Message... ... ... ? */
{
	void process(CLI cli);
}
