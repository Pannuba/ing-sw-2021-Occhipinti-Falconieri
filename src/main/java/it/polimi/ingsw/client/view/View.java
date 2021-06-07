package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.MessageIO;

import java.util.Observable;
import java.util.Observer;

public abstract class View		/* Add username */
{
	protected MessageExecutor action;

	protected MessageIO messageHandler;

	public abstract void update(Object obj);
}
