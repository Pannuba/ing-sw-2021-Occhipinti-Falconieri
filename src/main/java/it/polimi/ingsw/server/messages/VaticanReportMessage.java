package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Player;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class VaticanReportMessage implements Message
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
