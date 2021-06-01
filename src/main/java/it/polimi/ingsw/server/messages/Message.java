package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionCLI;

public interface Message
{
	void process(ActionCLI action);
}
