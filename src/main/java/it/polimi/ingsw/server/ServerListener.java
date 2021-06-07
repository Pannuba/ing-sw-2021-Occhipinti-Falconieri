package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.FirstPlayerMessage;
import it.polimi.ingsw.server.view.ClientHandler;
import it.polimi.ingsw.util.Ping;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens for incoming connections through the serverSocket and creates a new Match with (0 < n < 5) players
 * @author Giulio Occhipinti
 */

public class ServerListener
{
	private final ServerSocket serverSocket;

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {		/* Runs when the program receives an interrupt signal like CTRL+C */
				shutdown(); } });
	}

	/**
	 * Creates a list of n ClientHandlers (n = the number of players sent by the first client) and passes it to a newly created Match
	 * After creating a match it starts listening for another first player and creates a new Match thread, and so on
	 * @throws IOException if the I/O operation on the server and client sockets fail
	 * @throws ClassNotFoundException if the class of a serialized object cannot be found
	 */

	public void start() throws IOException, ClassNotFoundException
	{
		System.out.println("Server started");

		Socket socket;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		while (!serverSocket.isClosed())
		{
			List<Player> players = new ArrayList<>();
			List<ClientHandler> views = new ArrayList<>();
			List<String> playerNames = new ArrayList<>();

			String username = "";
			int numPlayers = 1;

			for (int i = 0; i < numPlayers; i++)        /* Create lobby */
			{
				System.out.println("Waiting for player " + (i + 1) + "...");

				socket = serverSocket.accept();
				socket.setSoTimeout(20000);

				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());

				System.out.println("Incoming connection: " + socket);
				username = (String) ois.readObject();					/* Client sends username and starts NetworkHandler thread */

				System.out.println("Username: " + username);

				if (isDuplicateUsername(playerNames, username))		/* TODO: send LoginErrorMessage? */
				{
					System.out.println("Duplicate username detected: " + username);
					i--;			/* If the username is duplicate, go back to the top of the loop without increasing i */
					continue;
				}

				playerNames.add(username);

				ClientHandler clientHandler = new ClientHandler(socket, username, ois, oos);		/* Start view thread that listens for commands from client */
				views.add(clientHandler);

				if (i == 0)		/* Get numPlayers from the first player who connects */
				{
					clientHandler.send(new FirstPlayerMessage(true));
					Object inputObj = ois.readObject();

					while (inputObj instanceof Ping)		/* Ignore pings to avoid ClassCastException if client is slow */
						inputObj = ois.readObject();

					numPlayers = Integer.parseInt((String) inputObj);        /* So the loop is repeated numPlayers times to get numPlayers players */
					System.out.println("numPlayers: " + numPlayers);
				}

				else
					clientHandler.send(new FirstPlayerMessage(false));

				new Thread(clientHandler).start();

				players.add(new Player(username));
			}

			Match m = new Match(players, views);		/* Start match, passes players and views */
			new Thread(m).start();
		}
	}

	/**
	 * Checks if the newly connected client has sent a username already picked by a client which connected before
	 * @param playerNames the list of player usernames in the match about to be created
	 * @param username the new username that has to be checked
	 * @return true if the username is already in playerNames, false otherwise
	 */

	private boolean isDuplicateUsername(List<String> playerNames, String username)
	{
		for (int i = 0; i < playerNames.size(); i++)
		{
			if (playerNames.get(i).equals(username))		/* If the received username is already being used by someone else in this match */
				return true;
		}

		return false;
	}

	private void shutdown()
	{
		try
		{
			System.out.println("Shutting down...");
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
