package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.NetworkHandler;

public abstract class View
{
	protected MessageExecutor action;

	protected NetworkHandler networkHandler;

	public abstract void update(Object obj);
}
