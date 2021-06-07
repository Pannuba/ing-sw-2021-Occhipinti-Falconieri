package it.polimi.ingsw.client;

public class LocalMatchIO extends MessageIO
{
	@Override
	public void send(Object obj)
	{
		//controller.parseInput((List<String>) obj);
	}

	@Override
	public void connect()
	{

	}

	@Override
	public void stop()
	{

	}

	@Override
	public void shutdown()
	{

	}

	public void sendToClient(Object obj)
	{
		setChanged();
		notifyObservers(obj);
	}
}
