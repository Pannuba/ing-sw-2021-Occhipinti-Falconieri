package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Match implements Runnable		/* Controller?? */
{
	private int numPlayers;
	private String gameCode;
	private List<Player> players = new ArrayList<Player>();

	public Match(int numPlayers, String firstPlayerName, Socket firstPlayerSocket)
	{
		this.numPlayers = numPlayers;

		players.add(new Player());
		players.get(0).setUsername(firstPlayerName);
		Runnable r = new ClientHandler(firstPlayerSocket);
	}
	public void run()
	{
		waitForPlayers();
		/* Start match */

	}

	void waitForPlayers()
	{
		Socket socket = null;
		String username = "";

		for (int i = 1; i < numPlayers; i++)		/* Waits for numPlayers clients to connect. Starts from 1 because one player is already connected */
		{
			try
			{
				//socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				username = dis.readUTF();

				if (dis.readUTF() == "JOIN_GAME")
				{
					String gameCode = dis.readUTF();
				}

				else
					System.out.println("Error, can't create new game now");

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			players.add(new Player());
			players.get(i).setUsername(username);
			Runnable r = new ClientHandler(socket);
		}
	}
}
