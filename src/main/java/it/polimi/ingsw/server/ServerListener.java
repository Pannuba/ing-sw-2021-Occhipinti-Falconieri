package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/*	When a new clients connects to the server it sends the username and then either NEW_GAME or JOIN_GAME.
	After that, another thread is created for each socket, waiting for messages coming from that user.
 */


public class ServerListener implements Runnable		/* Thread running listening for incoming connections */
{
	private final Socket clientSocket;
	private final DataInputStream dis;
	private final DataOutputStream dos;

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(Socket clientSocket) throws IOException
	{
		this.clientSocket = clientSocket;
		dis = new DataInputStream(clientSocket.getInputStream());
		dos = new DataOutputStream(clientSocket.getOutputStream());
	}

	public void run()
	{
		String username = "", choice = "";

		try
		{
			username = dis.readUTF();
			System.out.println("Username: " + username);

			choice = dis.readUTF();
			System.out.println("Choice: " + choice);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		if (true)		/* Current lobby is not full */
		{
			int numPlayers = 0;

			try
			{
				numPlayers = Integer.parseInt(dis.readUTF());        /* Need to create model somewhere and pass numPlayers and other vars we get here */
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			Runnable r = new Match(numPlayers, username, clientSocket);
			new Thread(r).start();
		}

		else if (false)		/* Lobby is full, create new one */
		{

		}

	}
}
