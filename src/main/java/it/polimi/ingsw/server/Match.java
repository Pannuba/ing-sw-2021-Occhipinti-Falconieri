package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.ChooseLeadersMessage;
import it.polimi.ingsw.server.messages.InitialResourcesMessage;
import it.polimi.ingsw.server.messages.MatchStartMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.List;

/**
 * Match thread created by the ServerListener
 * @author Giulio Occhipinti
 */

public class Match implements Runnable
{
	private final boolean isRecoveredMatch;
	private final int numPlayers;
	private final List<ClientHandler> views;
	private final Model model;

	/**
	 * Constructor. Creates the match's Model and Controller
	 * @param players the list of players created in ServerListener, used to initialize the Model
	 * @param views ClientHandler list created in ServerListener, they observe the model to get GameStates and messages, and are observed by the controller to get the clients' commands
	 */

	public Match(List<Player> players, List<ClientHandler> views)
	{
		isRecoveredMatch = false;
		this.numPlayers = players.size();
		this.views = views;

		model = new Model(players);
		Controller controller = new Controller(model);

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);		/* Controller observes the views to get the command */
			model.addObserver(views.get(i));			/* Views observe the model to send the client the new gamestate */
		}
	}

	public Match(Model recoveredModel, List<ClientHandler> views)
	{
		isRecoveredMatch = true;
		this.numPlayers = recoveredModel.getNumPlayers();
		this.views = views;
		model = recoveredModel;
		Controller controller = new Controller(recoveredModel);

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);		/* Controller observes the views to get the command */
			recoveredModel.addObserver(views.get(i));			/* Views observe the model to send the client the new gamestate */
		}

		model.update();
	}

	/**
	 * Thread started in the ServerListener, sends the initial messages (MatchStart, ChooseLeaders, InitialResources) to the views
	 */

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		if (!isRecoveredMatch)
		{

			for (int i = 0; i < numPlayers; i++)			/* When all players are connected */
				views.get(i).send(new MatchStartMessage());

			List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

			for (int i = 0; i < numPlayers; i++)
			{
				views.get(i).send(new ChooseLeadersMessage(leaderCardsLists.get(i)));
				views.get(i).send(new InitialResourcesMessage(i));			/* Because the player list in Model is sorted by ID. players[0] has ID 0 and so on */
			}
		}
	}

	public void stop()		/* If I create a Match list in ServerListener and call stop() on every match on shutdown, it doesn't work */
	{
		for (int i = 0; i < views.size(); i++)		/* Close views in this match */
			views.get(i).close();
	}

	public Model getModel()
	{
		return model;
	}
}
