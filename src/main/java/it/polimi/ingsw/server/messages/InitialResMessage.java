package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;

import java.io.Serializable;

public class InitialResMessage implements Message, Serializable
{
	@Override
	public void process(CLI cli)
	{
		cli.chooseResources();
	}
}
