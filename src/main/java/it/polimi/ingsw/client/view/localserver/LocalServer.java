package it.polimi.ingsw.client.view.localserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class LocalServer implements Runnable
{
	public void run()
	{
		try
		{
			final ServerSocket serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));
			LocalServerListener serverListener = new LocalServerListener(serverSocket);
			serverListener.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
