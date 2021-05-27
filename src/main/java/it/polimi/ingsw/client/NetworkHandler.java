package it.polimi.ingsw.client;

import it.polimi.ingsw.util.Ping;

import java.io.*;
import java.util.*;

import java.net.Socket;

import static java.lang.Boolean.parseBoolean;

public class NetworkHandler extends Observable implements Observer, Runnable		/* Observes CLI to get input to send to server, observed by CLI to send it the newest gamestate */
{
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private final String ip;
	private final int port;

	public NetworkHandler(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	public void run()
	{
		while (!clientSocket.isClosed())
		{
			try
			{
				System.out.println("Waiting for new object from server");
				Object inputObj = ois.readObject();
				System.out.println("Received " + inputObj.getClass().getSimpleName());

				if (!(inputObj instanceof Ping))		/* Don't care if it's a ping */
				{
					setChanged();
					notifyObservers(inputObj);
				}
			}
			catch (IOException | ClassNotFoundException e)
			{
				System.out.println("Lost connection to server!");
				e.printStackTrace();
				break;
			}
		}

		shutdown();
	}

	public void connect()
	{
		System.out.println("Connecting to server...");

		try
		{
			clientSocket = new Socket(ip, port);
			clientSocket.setSoTimeout(20000);								/* 20000 ms = 20 seconds. shutdown() after 3 failed pings? */
			oos = new ObjectOutputStream(clientSocket.getOutputStream());	/* Send commands (list of strings) to server */
			ois = new ObjectInputStream(clientSocket.getInputStream());		/* Receive gamestate from server (object) */
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Connection failed to server");
			shutdown();
		}

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

	public boolean isFirstPlayer()
	{
		boolean isFirstPlayer = false;		/* Server only tells true, otherwise it's false by default */

		try
		{
			isFirstPlayer = parseBoolean((String) ois.readObject());		/* Need to use readObject and then cast it */
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return isFirstPlayer;
	}

	public void waitForPlayers()		/* Remove this and create WaitForPlayersMessage? */
	{
		System.out.println("Waiting for players to connect...");

		String message;

		while (!clientSocket.isClosed())
		{
			try
			{
				message = (String) ois.readObject();		/* Handle if it receives a ping here */

				if (message.equals("START"))
					break;

				else
					System.out.println("Message received is not START");
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				System.out.println("Server closed connection!");	/* Separate error messages for timeout or server crash? */
				shutdown();
			}
		}
	}

	public void send(Object obj)
	{
		try
		{
			oos.writeObject(obj);
			oos.reset();						/* omg if this works i swear to god (it did kinda) */
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void shutdown()
	{
		System.out.println("Shutting down...");

		try
		{
			oos.flush();
			oos.close();

			clientSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		System.exit(0);
	}

	@Override
	public void update(Observable obs, Object obj)		/* Send command (List) to server */
	{
		System.out.println("Sending command " + ((List<String>) obj).get(0) + " to server");
		send((List<String>) obj);
	}
}
