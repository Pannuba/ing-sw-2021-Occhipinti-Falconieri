package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.controller.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public void run()		/* Start match */
	{
		try
		{
			model = new Model(numPlayers);
			controller = new Controller(model);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < numPlayers; i++)
		{
			views.get(i).send("START");
		}
	}
}
