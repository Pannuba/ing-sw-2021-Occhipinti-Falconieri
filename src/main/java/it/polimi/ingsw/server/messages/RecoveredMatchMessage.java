package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

/**
 * Used to make the GUI skip the GameStart scene and directly load the main view
 * Not needed by the CLI because it doesn't require specific messages to go from a scene to another
 * @author Giulio Occhipinti
 */

public class RecoveredMatchMessage implements Message
{
	@Override
	public void process(MessageExecutor action)
	{
		action.startRecoveredMatch();
	}
}
