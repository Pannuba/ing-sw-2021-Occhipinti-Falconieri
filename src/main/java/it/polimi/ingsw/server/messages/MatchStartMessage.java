package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;

import java.io.Serializable;

public class MatchStartMessage implements Message, Serializable
{

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.startMatch();
	}
}
