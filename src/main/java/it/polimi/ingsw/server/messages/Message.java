package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;

public interface Message
{
	void process(MessageDecoder decoder);
}
