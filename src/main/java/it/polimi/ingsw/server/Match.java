package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.*;
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

	public Match(ServerListener serverListener, List<Player> players, List<ClientHandler> views)
	{
		isRecoveredMatch = false;
		this.numPlayers = players.size();
		this.views = views;

		model = new Model(players);
		Controller controller = new Controller(model, serverListener);

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);
			model.addObserver(views.get(i));
		}
	}

	/**
	 * This constructor is used when the ServerListener detects that the players are reconnecting to a recovered match
	 * It passes a ServerListener instance to the controller to delete the recovered match file once the match is over
	 * @param serverListener the ServerListener instance
	 * @param recoveredModel the recovered model deserialized by the file on the server's disk
	 * @param views the list of views to be added to the match
	 */

	public Match(ServerListener serverListener, Model recoveredModel, List<ClientHandler> views)
	{
		isRecoveredMatch = true;
		this.numPlayers = recoveredModel.getNumPlayers();
		this.views = views;
		model = recoveredModel;
		Controller controller = new Controller(recoveredModel, serverListener);

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);
			recoveredModel.addObserver(views.get(i));
			views.get(i).send(new RecoveredMatchMessage());
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
			for (int i = 0; i < numPlayers; i++)
				views.get(i).send(new MatchStartMessage());

			List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

			for (int i = 0; i < numPlayers; i++)
			{
				views.get(i).send(new ChooseLeadersMessage(leaderCardsLists.get(i)));

				for (int j = 0; j < numPlayers; j++)
				{
					if (model.getPlayerByID(i).getUsername().equals(views.get(j).getUsername()))		/* Check if the view # is the same as the player ID to send InitialResourcesMessages in the right order */
					{
						views.get(j).send(new InitialResourcesMessage(i));					/* Because the player list in Model is sorted by ID. players[0] has ID 0 and so on */
						break;
					}
				}
			}
		}
	}

	public Model getModel()
	{
		return model;
	}
}
