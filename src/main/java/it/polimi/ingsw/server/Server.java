package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server
{

	public static void main(String[] args) throws IOException, ClassNotFoundException		/* TODO: port number in config file, use ConfigParser */
	{
		//final ServerSocket serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));		/* Port has to be > 1024 in order to work without root permissions */
		final ServerSocket serverSocket = new ServerSocket(2000);
		ServerListener serverListener = new ServerListener(serverSocket);
		serverListener.start();
	}
}
