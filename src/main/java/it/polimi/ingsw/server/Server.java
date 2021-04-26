package it.polimi.ingsw.server;

import server.controller.Controller; //!!!!!!!!!!!!!!

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;


public class Server
{
	public static void main(String args[]) throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(2000, 0, InetAddress.getByName(null));        /* Port has to be > 1024 in order to work without root permissions */
		//DataInputStream dis = new DataInputStream(socket.getInputStream());
		//DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		Runnable r = new ServerListener(serverSocket);
		new Thread(r).start();


		/* Start server CLI/GUI */

		//serverSocket.close();
	}
}