package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionCLI;

import java.io.Serializable;

public class FirstPlayerMessage implements Message, Serializable
{
	private final boolean isFirstPlayer;

	public FirstPlayerMessage(boolean isFirstPlayer)
	{
		this.isFirstPlayer = isFirstPlayer;
	}

	@Override
	public void process(ActionCLI action)
	{
		action.firstPlayer(isFirstPlayer);
	}
}
