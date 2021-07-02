package it.polimi.ingsw.client;

/**
 * Abstract class used to send messages to the controller, either remote or local
 * Using an abstract class from which NetworkHandler and LocalMatchIO inherit is required, because depending on whether the user
 * chooses a remote or local game the MessageIO instance variable is either initialized as a NetworkHandler or as a LocalMatchIO, respectively
 */

public abstract class MessageIO
{

	/**
	 * Send a message to the local/remote controller
	 * @param obj the message to send. It's either a Ping or a List of strings
	 */

	public abstract void send(Object obj);

	/**
	 * The following methods are only used by the NetworkHandler because they aren't needed by LocalMatchIO since
	 * it's not connected to any servers and doesn't use sockets, heartbeats...
	 */

	public abstract void connect();

	public abstract void stop();

	public abstract void shutdown();
}
