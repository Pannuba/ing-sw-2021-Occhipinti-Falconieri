package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.MessageIO;

/**
 * Abstract View class from which CLI and GUI inherit the instance variables and the update method
 * Used by the NetworkHandler and local game classes to access the update method
 */

public abstract class View
{
	protected MessageExecutor action;

	protected MessageIO messageHandler;

	/**
	 * Processes the object sent by the server.
	 * @param obj the message received from the server or local controller. Either a GameState or a Message
	 */

	public abstract void update(Object obj);
}
