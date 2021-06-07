package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

import java.io.Serializable;

/**
 * @author Giulio Occhipinti
 */

/*	This message is sent after (nearly?) every action the client performs. If the boolean is true the action has failed, so the
	controller doesn't progress the game by sending a new gamestate and choosing the next player (and flipping the next actiontoken
	for singleplayer matches) so that the client can perform another action in the same round.
 */

public class OperationResultMessage implements Message, Serializable
{
	private final String message;
	private final boolean isFailed;

	public OperationResultMessage(String message, boolean isFailed)
	{
		this.message = message;
		this.isFailed = isFailed;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.getOperationResultMessage(message, isFailed);
	}
}
