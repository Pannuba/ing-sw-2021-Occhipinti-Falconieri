package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable
{
	private final Socket clientSocket;
	private final String username;

	public ClientHandler(Socket clientSocket, String username)
	{
		this.clientSocket = clientSocket;
		this.username = username;
	}

	public void run()
	{
		DataInputStream dis = null;
		DataOutputStream dos = null;
		String message = null;

		System.out.println("Created thread for: " + clientSocket);

		try
		{
			dis = new DataInputStream(clientSocket.getInputStream());
			dos = new DataOutputStream(clientSocket.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		while (true)
		{
			try
			{
				message = (String) dis.readUTF();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			/* translate message, then call respective function in controller */
		}
	}
}
