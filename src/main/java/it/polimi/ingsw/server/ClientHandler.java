package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler extends Observable implements Runnable, Observer		/* Observes model waiting for changes, observed by controller */
{
	private final Socket clientSocket;
	private final String username;
	private DataInputStream dis;
	private ObjectOutputStream oos;

	public ClientHandler(Socket clientSocket, String username)
	{
		System.out.println("Created thread for: " + clientSocket);

		this.clientSocket = clientSocket;
		this.username = username;

		try
		{
			dis = new DataInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		String message = "";

		while (!clientSocket.isClosed())		/* Translate message, then call respective function in controller */
		{
			try
			{
				message = dis.readUTF();
				notifyObservers(message);		/* Sends command to controller */
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void send(Object message)
	{
		try
		{
			oos.writeObject(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable observable, Object o)
	{
		/* Send gamestate */
	}
}
