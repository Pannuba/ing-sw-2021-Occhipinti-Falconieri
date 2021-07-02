package it.polimi.ingsw.client.localmatch;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.ChooseLeadersMessage;
import it.polimi.ingsw.server.messages.InitialResourcesMessage;
import it.polimi.ingsw.server.messages.MatchStartMessage;

import java.util.List;

/**
 * Starts a local match. This class is essentially the same as Match in the server package, but tweaked to only support one player
 * @author Giulio Occhipinti
 */

public class LocalMatch implements Runnable
{
	private final LocalModel model;
	private final View view;

	/**
	 * Constructor. Sets the view and model instance variables that are used in the run method
	 * @param model the local match's model
	 * @param view the player's view
	 */

	public LocalMatch(LocalModel model, View view)
	{
		this.view = view;
		this.model = model;
	}

	/**
	 * Starts a local match
	 */

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		view.update(new MatchStartMessage());

		List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

		view.update(new ChooseLeadersMessage(leaderCardsLists.get(0)));
		view.update(new InitialResourcesMessage(0));
	}
}