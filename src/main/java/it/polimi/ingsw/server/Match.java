package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.ChooseLeadersMessage;
import it.polimi.ingsw.server.messages.InitialResMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.List;

public class Match implements Runnable
{
	private final int numPlayers;
	private final List<Player> players;
	private final List<ClientHandler> views;
	private final Model model;
	private final Controller controller;

	public Match(List<Player> players, List<ClientHandler> views)
	{
		this.players = players;
		this.numPlayers = players.size();
		this.views = views;

		System.out.println("Creating model and controller for new match...");
		model = new Model(players);
		controller = new Controller(model);

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);		/* Controller observes the views to get the command */
			model.addObserver(views.get(i));			/* Views observe the model to send the client the new gamestate */
		}
	}

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		sendToAll("START");		/* When all players are connected */

		List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();		/* Put this in controller? */

		for (int i = 0; i < numPlayers; i++)
		{
			System.out.println("Sending " + players.get(i).getUsername() + " " + leaderCardsLists.get(i));
			views.get(i).send(new ChooseLeadersMessage(leaderCardsLists.get(i)));
		}

		sendToAll(new InitialResMessage());

		/* Round management. controller? Call checkVaticanReport at the end of every round

		while (model.isMatchOver == false)
		{
			... round management
			model.getTrack().checkVaticanReport()
		}

		model.calculatePoints()
		send "MATCH_END" to all players, with score and results


		 */

	}

	private void sendToAll(Object obj)
	{
		for (int i = 0; i < numPlayers; i++)
		{
			System.out.println("Sending " + players.get(i).getUsername() + " " + obj);
			views.get(i).send(obj);
		}
	}
}
