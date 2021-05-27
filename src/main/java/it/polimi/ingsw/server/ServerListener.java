package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.view.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*	When a new clients connects to the server it sends the username and then either NEW_GAME or JOIN_GAME.
	After that, another thread is created for each socket, waiting for messages coming from that user.
*/


public class ServerListener				/* Thread running listening for incoming connections */
{
	private final ServerSocket serverSocket;

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
	}

	public void start()
	{
		System.out.println("Server started");

		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		while (!serverSocket.isClosed())
		{
			List<Player> players = new ArrayList<>();
			List<ClientHandler> views = new ArrayList<>();
			String username = "";
			int numPlayers = 1;

			for (int i = 0; i < numPlayers; i++)        /* Create lobby */
			{
				System.out.println("Waiting for player " + (i + 1) + "...");

				try
				{
					socket = serverSocket.accept();
					socket.setSoTimeout(20000);
					ois = new ObjectInputStream(socket.getInputStream());
					oos = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("Incoming connection: " + socket);
					username = (String) ois.readObject();
					System.out.println("Username: " + username);

					if (i == 0)		/* Get numPlayers from the first player who connects */
					{
						oos.writeObject("true");
						numPlayers = Integer.parseInt((String) ois.readObject());        /* So the loop is repeated numPlayers times to get numPlayers players */
						System.out.println("numPlayers: " + numPlayers);
					}

					else
						oos.writeObject("false");

				}
				catch (IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}

				players.add(new Player(username));

				ClientHandler clientHandler = new ClientHandler(socket, username, ois, oos);		/* Start view thread that listens for commands from client */
				views.add(clientHandler);
				new Thread(clientHandler).start();
			}

			Runnable r = new Match(players, views);			/* Start match, passes players and views */
			new Thread(r).start();
		}
	}
}
