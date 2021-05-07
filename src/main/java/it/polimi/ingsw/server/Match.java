package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
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
	}

	public void run()		/* Create model and controller, send "start match" message and then start the match */
	{
		System.out.println("Match thread started");

		try
		{
			model = new Model(numPlayers);
			controller = new Controller(model);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			TimeUnit.SECONDS.sleep(2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < numPlayers; i++)
		{
			System.out.println("Sending player " + (i + 1) + " START message");
			views.get(i).send("START");
		}
	}
}
