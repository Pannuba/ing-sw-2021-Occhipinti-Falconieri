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

	/*void waitForPlayers()		This should go in a Lobby class
	{
		Socket socket = null;
		String username = "";

		for (int i = 1; i < numPlayers; i++)		/* Waits for numPlayers clients to connect. Starts from 1 because one player is already connected */
		/*{
			try
			{
				socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				username = dis.readUTF();


			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			players.add(new Player());
			players.get(i).setUsername(username);
			Runnable r = new ClientHandler(socket);
		}
	}*/
}
