package it.polimi.ingsw.client.localserver;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.FirstPlayerMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LocalServer implements Runnable
{
	private ServerSocket serverSocket;

	public LocalServer()
	{
		serverSocket = null;		/* TODO: add shutdownhook? LocalClientHandler? */
	}

	public void run()
	{

		try
		{
			serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

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
