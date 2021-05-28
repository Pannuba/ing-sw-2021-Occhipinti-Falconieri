package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;
import it.polimi.ingsw.util.Pair;

import java.io.Serializable;
import java.util.List;

public class MatchOverMessage implements Message, Serializable		/* Sent to client when isMatchOver() returns true */
{
	private final String winnerName;
	private final List<Pair<String, Integer>> pointsTable;		/* username : victoryPoints */

	public MatchOverMessage(String winnerName, List<Pair<String, Integer>> pointsTable)
	{
		this.winnerName = winnerName;
		this.pointsTable = pointsTable;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.matchOver(winnerName, pointsTable);
	}
}
