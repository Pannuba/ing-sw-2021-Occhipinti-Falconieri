package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*	When a new clients connects to the server it sends the username and then either NEW_GAME or JOIN_GAME.
	After that, another thread is created for each socket, waiting for messages coming from that user.
 */


public class ServerListener implements Runnable		/* Thread running listening for incoming connections */
{
	private ServerSocket serverSocket;

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
			String username = null, choice = null;

			try
			{
				socket = serverSocket.accept();
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				System.out.println("INCOMING CONNECTION: " + socket);
				username = (String) dis.readUTF();
				choice = (String) dis.readUTF();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			System.out.println("username: " + username + "\nchoice: " + choice);        /* Works */

			if (choice == "NEW_GAME")        /* Will have to divide games in threads for advanced functionality */
			{
				// Ask for player number
				// Give user game code, who then shares it to the other players
			}

			if (choice == "JOIN_GAME") {
				// Ask for password/game code
				// Pass socket and game code to ClientHandler?
			}

			Runnable r = new ClientHandler(socket);
			new Thread(r).start();

		}
	}
}
