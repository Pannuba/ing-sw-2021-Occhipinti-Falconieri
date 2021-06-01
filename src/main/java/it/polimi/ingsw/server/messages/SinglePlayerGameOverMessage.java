package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionCLI;

import java.io.Serializable;

/*	Sent when a lose condition for the singleplayer match has been verified. This message has priority over GameOverMessage,
	so when this is sent the client will immediately stop the game and exit.
	If a singleplayer match doesn't lose, a MatchOverMessage will be sent instead and the player will see their score.
 */

public class SinglePlayerGameOverMessage implements Message, Serializable
{
	private final String message;

	public SinglePlayerGameOverMessage(String message)
	{
		this.message = message;
	}

	@Override
	public void process(ActionCLI action)
	{
		action.singlePlayerGameOver(message);
	}
}
