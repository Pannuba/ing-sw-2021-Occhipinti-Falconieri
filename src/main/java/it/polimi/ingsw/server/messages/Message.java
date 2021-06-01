package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.ActionExecutor;

public interface Message
{
	void process(ActionExecutor action);
}
