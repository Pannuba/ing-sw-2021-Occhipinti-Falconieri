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
		final int port;

		if (args[0].equals("-p") && args.length == 2)
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

		else
		{
			System.out.println("Port number not specified, defaulting to 2000");
			port = 2000;
		}

		System.out.println("Starting server on port " + port);
		final ServerSocket serverSocket = new ServerSocket(port);
		ServerListener serverListener = new ServerListener(serverSocket);
		serverListener.start();
	}
}
