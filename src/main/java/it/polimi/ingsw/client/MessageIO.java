package it.polimi.ingsw.client;

public abstract class MessageIO
{
	public abstract void send(Object obj);

	public abstract void connect();

	public abstract void stop();

	public abstract void shutdown();
}
