package it.polimi.ingsw.server.view;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientHandler extends Observable implements Runnable, Observer		/* Observes model waiting for changes, observed by controller */
{
	private final Socket clientSocket;
	private final String username;
	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;

	public ClientHandler(Socket clientSocket, String username, ObjectInputStream ois, ObjectOutputStream oos)
	{
		System.out.println("Created client handler for: " + username);

		this.clientSocket = clientSocket;
		this.username = username;
		this.ois = ois;			/* Can't create a I/O stream for a socket that already has one (created in ServerListener) */
		this.oos = oos;

		TimerTask timerTask = new TimerTask()
		{
			public void run()
			{
				send("ping");		/* TODO: send Ping object, not string */
			}
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 5000, 10000);		/* Start heartbeat after 5 seconds, sends ping every timeout/2 seconds */
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
				command = (List<String>) ois.readObject();				/* TODO: if (obj instanceof ... like in CLI to tell apart commands and pings */
				System.out.println("Received " + command + " from " + username);
				setChanged();
				notifyObservers(command);		/* Sends command to controller */
			}
			catch (Exception e)				/* EOFException when client disconnects */
			{
				e.printStackTrace();
				System.out.println(username + " disconnected!");
				break;
			}
		}
	}

	public void send(Object message)
	{
		try
		{
			System.out.println("Sending " + message.getClass().getSimpleName() + " to " + username);
			oos.writeObject(message);
			oos.reset();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getUsername()
	{
		return username;
	}

	@Override
	public void update(Observable obs, Object obj)			/* Send gamestate received from model */
	{
		send(obj);
	}
}
