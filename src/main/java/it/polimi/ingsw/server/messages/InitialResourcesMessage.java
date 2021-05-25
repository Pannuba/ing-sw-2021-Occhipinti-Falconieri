package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.MessageDecoder;

import java.io.Serializable;

public class InitialResourcesMessage implements Message, Serializable
{
	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.chooseResources();
	}
}
