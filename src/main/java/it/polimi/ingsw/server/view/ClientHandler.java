package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.Ping;

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
				send(new Ping());
			}
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 5000, 10000);		/* Start heartbeat after 5 seconds, sends ping every timeout/2 seconds */
	}

	public void run()		/* Activates after the setup phase has ended */
	{
		System.out.println("Started " + username + "'s clienthandler thread");
		Object inputObj;

		while (!clientSocket.isClosed())		/* Translate message, then call respective function in controller */
		{
			try
			{
				System.out.println(username + " waiting for message from client");
				inputObj = ois.readObject();

				if (!(inputObj instanceof Ping))		/* Don't care about pings */
				{
					System.out.println("Received " + inputObj + " from " + username);		/* Prints the command */
					setChanged();
					notifyObservers(inputObj);        /* Sends command to controller */
				}
			}
			catch (Exception e)				/* EOFException when client disconnects */
			{
				e.printStackTrace();
				System.out.println(username + " disconnected!");
				break;
			}
		}
	}

	public void send(Object obj)
	{
		if (!obj.getClass().getSimpleName().equals("Ping"))		/* Pings are really annoying */
			System.out.println("Sending " + obj.getClass().getSimpleName() + " to " + username);
		try
		{
			oos.writeObject(obj);
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
