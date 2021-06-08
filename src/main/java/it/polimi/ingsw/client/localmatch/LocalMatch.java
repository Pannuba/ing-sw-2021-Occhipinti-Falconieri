package it.polimi.ingsw.client.localmatch;

import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.ChooseLeadersMessage;
import it.polimi.ingsw.server.messages.InitialResourcesMessage;
import it.polimi.ingsw.server.messages.MatchStartMessage;


import java.util.List;

public class LocalMatch implements Runnable
{
	private final LocalModel model;
	private final LocalController lc;
	private final View view;

	public LocalMatch(LocalModel model, View view, LocalController lc)
	{
		this.view = view;
		this.lc = lc;
		this.model = model;
		LocalMatchIO messageHandler = new LocalMatchIO(lc);
	}

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		view.update(new MatchStartMessage());

		List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

		view.update(new ChooseLeadersMessage(leaderCardsLists.get(0)));
		view.update(new InitialResourcesMessage(0));
	}

	public LocalController getLc()
	{
		return lc;
	}
}