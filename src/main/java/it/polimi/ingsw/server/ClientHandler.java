package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;				/* TODO: view observes model, send gamestate to client */

public class ClientHandler extends Observable implements Runnable, Observer		/* Observes model waiting for changes, observed by controller */
{
	private final Socket clientSocket;
	private final String username;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ClientHandler(Socket clientSocket, String username, DataInputStream dis, ObjectInputStream ois, ObjectOutputStream oos)
	{
		System.out.println("Created client handler for: " + username);

		this.clientSocket = clientSocket;
		this.username = username;
		this.dis = dis;				/* Can't create a I/O stream for a socket that already has one (created in ServerListener) */
		this.ois = ois;
		this.oos = oos;
	}

	public void run()		/* Activates after the setup phase has ended */
	{
		System.out.println("Started " + username + "'s clienthandler thread");
		List<String> command;

		while (!clientSocket.isClosed())		/* Translate message, then call respective function in controller */
		{
			try
			{
				System.out.println(username + " waiting for message from client");
				command = (List<String>)ois.readObject();		/* EOFException when client disconnects */
				System.out.println("Received " + command + " from " + username);
				setChanged();
				notifyObservers(command);		/* Sends command to controller */
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

	public String getUsername()
	{
		return username;
	}

	public Socket getClientSocket()
	{
		return clientSocket;
	}
}
