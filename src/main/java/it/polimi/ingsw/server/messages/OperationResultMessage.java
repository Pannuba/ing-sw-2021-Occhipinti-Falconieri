package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;

import java.io.Serializable;

public class OperationResultMessage implements Message, Serializable
{
	private String message;

	public OperationResultMessage(String message)
	{
		this.message = message;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.getOperationResultMessage(message);
	}
}
