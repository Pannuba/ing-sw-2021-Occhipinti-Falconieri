package it.polimi.ingsw.client.view.localserver;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.FirstPlayerMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*	When a new clients connects to the server it sends the username and then either NEW_GAME or JOIN_GAME.
	After that, another thread is created for each socket, waiting for messages coming from that user.
*/


public class LocalServerListener
{
	private final ServerSocket serverSocket;

	/* Static method to get name/choice from client? So Match can use them */

	public LocalServerListener(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {		/* Runs when the program receives an interrupt signal like CTRL+C */
				shutdown(); } });
	}

	public void start()
	{
		System.out.println("Server started");

		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		List<Player> players = new ArrayList<>();
		List<ClientHandler> views = new ArrayList<>();
		String username = "";

		try
		{
			socket = serverSocket.accept();
			socket.setSoTimeout(20000);

			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

			System.out.println("Incoming connection: " + socket);

			username = (String) ois.readObject();
			System.out.println("Username: " + username);

			oos.writeObject(new FirstPlayerMessage(false));

		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		players.add(new Player(username));

		ClientHandler clientHandler = new ClientHandler(socket, username, ois, oos);		/* Start view thread that listens for commands from client */
		views.add(clientHandler);
		new Thread(clientHandler).start();

		LocalMatch m = new LocalMatch(players, views);
		new Thread(m).start();
	}

	public void shutdown()
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
