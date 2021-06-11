package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;

/**
 * Sent by the server when a client sends a name that's already been used by another player in the same match
 * @author Giulio Occhipinti
 */

public class DuplicateNameMessage implements Message
{
	@Override
	public void process(MessageExecutor action)
	{
		action.duplicateName();
	}
}
