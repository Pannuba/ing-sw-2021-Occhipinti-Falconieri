package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Match implements Runnable		/* Controller?? */
{
	private final int numPlayers;
	private final List<Player> players;
	private final List<ClientHandler> views;
	private Model model;
	private Controller controller;

	public Match(int numPlayers, List<Player> players, List<ClientHandler> views)
	{
		this.numPlayers = numPlayers;
		this.players = players;
		this.views = views;

		System.out.println("Creating model and controller for new match...");

		try
		{
			model = new Model(players);
			controller = new Controller(model);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).addObserver(controller);		/* Controller observes the views to get the command */
		}
	}

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		sendToAll("START");

		List<List<LeaderCard>> leaderCardsLists = model.createLeaderCardsLists();

		for (int i = 0; i < numPlayers; i++)
		{
			System.out.println("Sending " + players.get(i).getUsername() + " " + leaderCardsLists.get(i));
			views.get(i).send(leaderCardsLists.get(i));
		}


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
