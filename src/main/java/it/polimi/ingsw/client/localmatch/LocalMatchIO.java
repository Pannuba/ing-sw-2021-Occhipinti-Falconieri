package it.polimi.ingsw.client.localmatch;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.localmatch.controller.LocalController;

import java.util.List;

/**
 * Used by the client to send commands to the local controller
 * This class extends MessageIO but only uses the send() because the other methods aren't needed since the client isn't connected to any server
 */

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
