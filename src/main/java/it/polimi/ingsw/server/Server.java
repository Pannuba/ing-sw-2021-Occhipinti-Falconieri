package it.polimi.ingsw.server;

import it.polimi.ingsw.server.view.ServerListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server
{

	public static void main(String[] args) throws IOException		/* TODO: port number in config file, use ConfigParser */
	{
		final ServerSocket serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));        /* Port has to be > 1024 in order to work without root permissions */
		Runnable r = new ServerListener(serverSocket);
		new Thread(r).start();

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()		/* Also add this to ServerListener? */
			{
				try
				{
					System.out.println("Interrupt signal received!\nClosing server socket...");
					serverSocket.close();
				}
				catch (Exception e)
				{
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		});
	}
}