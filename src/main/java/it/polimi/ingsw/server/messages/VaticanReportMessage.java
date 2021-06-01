package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.List;

/* Send popeToken # (int) or the PopeToken itself? If int, client has to perform checks... */

public class VaticanReportMessage implements Message, Serializable
{
	private final int popeBoxNum;
	private final List<Player> players;

	public VaticanReportMessage(int popeBoxNum, List<Player> players)
	{
		this.popeBoxNum = popeBoxNum;
		this.players = players;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.vaticanReport(popeBoxNum, players);
	}
}
