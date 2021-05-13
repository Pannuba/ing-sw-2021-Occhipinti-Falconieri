package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.*;
import java.util.List;
import java.util.Observable;

import java.net.Socket;
import java.util.Observer;

import static java.lang.Boolean.parseBoolean;

public class NetworkHandler extends Observable implements Observer, Runnable		/* Observes CLI to get input to send to server, observed by CLI to send it the newest gamestate */
{
	private Socket clientSocket;
	private ObjectInputStream ois;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	private final String username, ip;
	private final int port;
	private final CLI cli;

	public NetworkHandler(CLI cli, String username, String ip, int port)
	{
		this.cli = cli;
		//cli.addObserver(this);			/* Observe CLI */

		this.username = username;
		this.ip = ip;
		this.port = port;
	}

	public void run()
	{
		while(!clientSocket.isClosed())
		{
			try
			{
				System.out.println("Waiting for new gamestate");
				GameState gameState = (GameState) ois.readObject();			/* Send gamestate to the view? */
				setChanged();
				notifyObservers(gameState);
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		try					/* When the socket is closed close the output streams, maybe notify the view */
		{
			dos.flush();
			oos.flush();
			dos.close();
			oos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void connect()
	{
		System.out.println("Connecting to server...");

		try
		{
			clientSocket = new Socket(ip, port);
			dos = new DataOutputStream(clientSocket.getOutputStream());		/* Send strings to server */
			oos = new ObjectOutputStream(clientSocket.getOutputStream());	/* Send commands (list of strings) to server */
			ois = new ObjectInputStream(clientSocket.getInputStream());		/* Receive gamestate from server (object) */
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Connection failed to server");
			System.exit(1);
		}
	}

	public boolean isFirstPlayer()
	{
		boolean isFirstPlayer = false;		/* Server only tells true, otherwise it's false by default */

		try
		{
			isFirstPlayer = parseBoolean((String)ois.readObject());		/* Need to use readObject and then cast it */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return isFirstPlayer;
	}

	public void waitForPlayers()
	{
		System.out.println("Waiting for players to connect...");

		String message = "";

		while(!clientSocket.isClosed())
		{
			try
			{
				message = (String)ois.readObject();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if (message.equals("START"))
				break;

			else
				System.out.println("Message received is not START");
		}
	}

	public List<LeaderCard> getFourLeadercards()
	{
		return (List<LeaderCard>)receive();
	}

	public void sendString(String message)
	{
		try
		{
			dos.writeUTF(message);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendCommand(List<String> command)
	{
		try
		{
			oos.writeObject(command);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Object receive()
	{
		Object inputObj = null;

		try
		{
			inputObj = ois.readObject();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return inputObj;
	}

	@Override
	public void update(Observable observable, Object o)		/* Send command (List) to server */
	{
		System.out.println("Sending command " + ((List<String>)o).get(0) + " to server");
		sendCommand((List<String>)o);
	}
}
