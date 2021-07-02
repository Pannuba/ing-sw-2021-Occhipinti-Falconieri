package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Main server class, creates the server's socket and starts a ServerListener thread
 * @author Giulio Occhipinti
 */

public class Server
{
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		int port = 2000;

		if (args.length == 0)
			System.out.println("Port number not specified, defaulting to 2000");

		else if (args.length == 2 && args[0].equals("-p"))
		{
			try
			{
				port = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid port number!");
				return;
			}
		}

		System.out.println("Starting server on port " + port);
		final ServerSocket serverSocket = new ServerSocket(port);
		ServerListener serverListener = new ServerListener(serverSocket);
		serverListener.start();
	}
}
