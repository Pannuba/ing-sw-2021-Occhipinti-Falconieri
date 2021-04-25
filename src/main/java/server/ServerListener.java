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
		while(true)
		{
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				System.out.println("INCOMING CONNECTION: " + socket);
				String username = (String)dis.readUTF();
				String choice = (String)dis.readUTF();
				System.out.println("username: " + username + "\nchoice: " + choice);		/* Works */

				if (choice == "NEW_GAME")		/* Will have to divide games in threads for advanced functionality */
				{
					// Ask for player number
					// Give user game code, who then shares it to the other players
				}

				if (choice == "JOIN_GAME")
				{
					// Ask for password/game code
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
