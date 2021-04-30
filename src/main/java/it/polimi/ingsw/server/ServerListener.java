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
	private ServerSocket serverSocket;

	/* Static method to get name/choice from client? So Match can use them */

	public ServerListener(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
	}

	public void run()
	{
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		while (true)
		{
			String username = "", choice = "";

			try
			{
				socket = serverSocket.accept();
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				System.out.println("Incoming connection: " + socket);
				username = dis.readUTF();
				choice = dis.readUTF();
			}
			catch (IOException e)
			{
				e.printStackTrace();		/* Better than println as it tells filename and line number where the exception happened */
			}

			System.out.println("Username: " + username + "\nChoice: " + choice);        /* Works */

			if (choice == "NEW_GAME")        /* Sends game code to client, creates new match thread */
			{
				String gameCode = generateGameCode();    /* gameCodes list? class member? */
				int numPlayers = 0;

				try
				{
					dos.writeUTF(gameCode);
					numPlayers = Integer.parseInt(dis.readUTF());        /* Need to create model somewhere and pass numPlayers and other vars we get here */
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				/* Match thread waits for all players to join, then when everyone is connected ServerListener keeps going */
				/* TODO: use locks, synchronized() to pause this thread until Match has all players connected */
				Runnable r = new Match(numPlayers, gameCode, username, socket, serverSocket);
				new Thread(r).start();
			}

			/*if (choice == "JOIN_GAME")
			{
				try
				{
					String gameCode = dis.readUTF();
				} catch (IOException e)
				{
					e.printStackTrace();
				}

				// Pass socket and game code to ClientHandler?
			}*/

		}
	}

	private String generateGameCode()			/* Randomly generates a 5 character code */
	{
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";		/* TODO: check that the generated code doesn't already exist */
		String code = "";

		for (int i = 0; i < 5; i++)
			code += chars.charAt(ThreadLocalRandom.current().nextInt(0, chars.length() + 1));

		return code;
	}
}
