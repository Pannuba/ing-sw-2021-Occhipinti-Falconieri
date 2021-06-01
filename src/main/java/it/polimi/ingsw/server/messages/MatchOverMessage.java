package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.ActionCLI;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.List;

public class MatchOverMessage implements Message, Serializable		/* Sent to client when isMatchOver() returns true */
{
	private final String winnerName;
	private final List<Player> players;

	public MatchOverMessage(String winnerName, List<Player> players)
	{
		this.winnerName = winnerName;
		this.players = players;
	}

	@Override
	public void process(ActionCLI action)
	{
		action.matchOver(winnerName, players);
	}
}
