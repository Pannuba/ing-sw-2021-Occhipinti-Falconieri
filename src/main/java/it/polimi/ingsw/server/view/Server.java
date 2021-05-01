package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.ServerListener;
import it.polimi.ingsw.server.controller.Controller; //!!!!!!!!!!!!!!

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;


public class Server
{
	public static void main(String args[]) throws IOException		/* TODO: port number in config file */
	{
		final ServerSocket serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));        /* Port has to be > 1024 in order to work without root permissions */
		Runnable r = new ServerListener(serverSocket);			/* Does this need to be a thread? */
		new Thread(r).start();

		//serverSocket.close();
	}
}