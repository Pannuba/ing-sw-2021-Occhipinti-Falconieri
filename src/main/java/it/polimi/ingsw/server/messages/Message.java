package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;

public interface Message       /* TODO: BuyResourcesMessage implements Message... ... ... ? */
{
	Object payload = null;		/* The Object that is sent to the client, like bought resources, or the bought devcard... */
	void process(CLI cli);
}
