package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.MessageIO;

public abstract class View		/* Add username */
{
	protected MessageExecutor action;

	protected MessageIO messageHandler;

	public abstract void update(Object obj);
}
