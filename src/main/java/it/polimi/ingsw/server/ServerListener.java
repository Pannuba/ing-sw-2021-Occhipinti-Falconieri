package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*	When a new clients connects to the server it sends the username and then either NEW_GAME or JOIN_GAME.
	After that, another thread is created for each socket, waiting for messages coming from that user.
 */


public class ServerListener implements Runnable		/* Thread running listening for incoming connections */
{
	private final ServerSocket serverSocket;

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(ServerSocket serverSocket) throws IOException
	{
		this.serverSocket = serverSocket;
	}

	public void run()
	{
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		while (!serverSocket.isClosed())
		{
			String username = "";
			int numPlayers = 1;
			List<Player> players = new ArrayList<Player>();

			for (int i = 0; i < numPlayers; i++)        /* Create first lobby */
			{
				try
				{
					socket = serverSocket.accept();
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					System.out.println("Incoming connection: " + socket);
					username = dis.readUTF();
					System.out.println("Username: " + username);
					numPlayers = Integer.parseInt(dis.readUTF());		/* So the loop is repeated numPlayers times to get numPlayers players */
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				players.add(new Player());
				players.get(i).setUsername(username);

				Runnable r = new ClientHandler(socket, username);
				new Thread(r).start();
			}

			Runnable r = new Match(numPlayers, players);
			new Thread(r).start();
		}

	}
}
