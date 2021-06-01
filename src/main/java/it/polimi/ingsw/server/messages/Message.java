package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.ActionExecutor;
import it.polimi.ingsw.client.view.cli.ActionCLI;

public interface Message
{
	void process(ActionExecutor action);
}
