package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionExecutor;

public interface Message
{
	void process(ActionExecutor action);
}
