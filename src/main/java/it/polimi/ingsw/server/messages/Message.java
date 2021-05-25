package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.MessageDecoder;

public interface Message       /* TODO: BuyResourcesMessage implements Message... ... ... ? */
{
	void process(MessageDecoder decoder);
}
