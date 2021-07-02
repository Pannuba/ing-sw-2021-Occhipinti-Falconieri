package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.LoginFailedMessage;
import it.polimi.ingsw.server.messages.FirstPlayerMessage;
import it.polimi.ingsw.server.view.ClientHandler;
import it.polimi.ingsw.util.Ping;
import it.polimi.ingsw.util.XML_Serialization;

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
	private final List<Model> recoveredMatches;
	private List<Model> totalMatches;		/* totalMatches = recoveredMatches + new matches */

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
		recoveredMatches = new ArrayList<>();
		totalMatches = new ArrayList<>();

		new File("../savedmatches/").mkdir();
		File xmlpath = new File("../savedmatches/");
		int matchcount = 0;

		for (int i = 0; i < xmlpath.list().length; i++)
		{
			if (xmlpath.list()[i].endsWith(".xml"))
				matchcount++;
		}

		System.out.println("there are " + matchcount + " restored matches");

		Model matchToAdd = null;

		for (int i = 0; i < matchcount; i++)
		{
			try
			{
				matchToAdd = (Model) XML_Serialization.deserialize(new FileInputStream("../savedmatches/match" + (i + 1) + ".xml"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			recoveredMatches.add(matchToAdd);
			totalMatches.add(matchToAdd);
		}

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
			Object inputObj;
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

				if (isDuplicateUsername(playerNames, username))
				{
					System.out.println("Duplicate username detected: " + username);
					oos.writeObject(new LoginFailedMessage("This name has already been chosen by someone else! Enter a different name"));
					oos.reset();

					i--;
					continue;
				}

				System.out.println("Username: " + username);

				playerNames.add(username);

				ClientHandler clientHandler = new ClientHandler(socket, username, ois, oos);		/* Start view thread that listens for commands from client */
				views.add(clientHandler);

				if (i == 0)		/* Get numPlayers from the first player who connects */
				{
					clientHandler.send(new FirstPlayerMessage(true));
					inputObj = ois.readObject();

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

			if (arePlayersReconnecting(players) != -1)
			{
				Match recoveredMatch = new Match(this, recoveredMatches.get(arePlayersReconnecting(players)), views);
				new Thread(recoveredMatch).start();
			}

			else
			{
				Match newMatch = new Match(players, views);        /* Start match, passes players and views */
				new Thread(newMatch).start();
				totalMatches.add(newMatch.getModel());
			}
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

	private int arePlayersReconnecting(List<Player> players)
	{
		for (int i = 0; i < recoveredMatches.size(); i++)
		{
			List<Player> recoveredMatchPlayers = recoveredMatches.get(i).getPlayers();
			int counter = 0;

			for (int j = 0; j < recoveredMatchPlayers.size(); j++)
			{
				for (int k = 0; k < players.size(); k++)
				{
					if (recoveredMatchPlayers.get(j).getUsername().equals(players.get(k).getUsername()))
						counter++;
				}

				if (counter == recoveredMatchPlayers.size())
					return i;
			}
		}

		return -1;
	}

	public void deleteRecoveredMatch(Model matchToRemove)
	{
		System.out.println("Deleting match of player " + matchToRemove.getPlayers().get(0).getUsername());
		int removedMatchIndex = totalMatches.indexOf(matchToRemove);
		totalMatches.remove(matchToRemove);

		if (!new File("../savedmatches/match" + (removedMatchIndex + 1) + ".xml").delete())
			System.out.println("Couldn't delete match file!");
	}

	private void shutdown()
	{
		try
		{
			System.out.println("Shutting down...");
			serverSocket.close();

			for (int i = 0; i < totalMatches.size(); i++)
				XML_Serialization.serialize(totalMatches.get(i), "../savedmatches/match" + (i + 1) + ".xml");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
