package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

public interface Message
{
	void process(MessageExecutor action);
}
