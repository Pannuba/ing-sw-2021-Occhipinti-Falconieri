package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

import java.io.Serializable;

/**
 * Each message sent by the server calls a method in the client's MessageExecutor to perform the desired action
 * @author Giulio Occhipinti
 */

public interface Message extends Serializable
{
	void process(MessageExecutor action);
}
