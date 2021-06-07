package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

/**
 * Each message sent by the server calls a method in the client's MessageExecutor to perform the desired action
 * @author Giulio Occhipinti
 */

public interface Message
{
	void process(MessageExecutor action);
}
