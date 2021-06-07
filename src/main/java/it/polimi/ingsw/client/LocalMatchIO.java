package it.polimi.ingsw.client;

import it.polimi.ingsw.client.localmatch.controller.LocalController;

import java.util.List;

public class LocalMatchIO extends MessageIO
{
	private final LocalController controller;

	public LocalMatchIO(LocalController controller)
	{
		this.controller = controller;
	}

	@Override
	public void send(Object obj)
	{
		controller.parseInput((List<String>) obj);
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

}
