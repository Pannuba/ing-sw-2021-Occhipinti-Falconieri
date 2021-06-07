package it.polimi.ingsw.client;

import java.util.Observable;

public abstract class MessageIO extends Observable
{
	/* observer/observable? synchronized? */
	
	public abstract void send(Object obj);

	public abstract void connect();

	public abstract void stop();

	public abstract void shutdown();
}
