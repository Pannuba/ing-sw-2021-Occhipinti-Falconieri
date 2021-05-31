package it.polimi.ingsw.client.localserver;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.ChooseLeadersMessage;
import it.polimi.ingsw.server.messages.InitialResourcesMessage;
import it.polimi.ingsw.server.messages.MatchStartMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.List;

public class LocalMatch implements Runnable
{
	private final List<ClientHandler> views;
	private final Model model;

	public LocalMatch(List<Player> players, List<ClientHandler> views)		/* TODO: use Player instead of a List<Player> */
	{
		this.views = views;

		model = new Model(players);
		Controller controller = new Controller(model);

		views.get(0).addObserver(controller);		/* Controller observes the views to get the command */
		model.addObserver(views.get(0));			/* Views observe the model to send the client the new gamestate */
	}

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		views.get(0).send(new MatchStartMessage());

		List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

		views.get(0).send(new ChooseLeadersMessage(leaderCardsLists.get(0)));
		views.get(0).send(new InitialResourcesMessage(0));		/* Because the player list in Model is sorted by ID. players[0] has ID 0 and so on */

		model.update();		/* Send the first gamestate after the setup messages. Putting this here instead of the controller makes everything work */
	}

	public void stop()		/* If I create a Match list in ServerListener and call stop() on every match on shutdown, it doesn't work */
	{
		views.get(0).close();
	}
}
