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
	private List<Player> players = new ArrayList<Player>();
	private Model model;
	private Controller controller;

	public Match(int numPlayers, List<Player> players)
	{
		this.numPlayers = numPlayers;
		this.players = players;
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
	}
}
