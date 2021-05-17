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

	public NetworkHandler(String username, String ip, int port)
	{
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
				GameState gameState = (GameState) ois.readObject();
				setChanged();
				notifyObservers(gameState);
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
			dos = new DataOutputStream(clientSocket.getOutputStream());		/* Send strings to server */
			oos = new ObjectOutputStream(clientSocket.getOutputStream());	/* Send commands (list of strings) to server */
			ois = new ObjectInputStream(clientSocket.getInputStream());		/* Receive gamestate from server (object) */
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Connection failed to server");
			shutdown();
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

				if (message.equals("START"))
					break;

				else
					System.out.println("Message received is not START");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Server closed connection!");
				shutdown();
			}
		}
	}

	public GameState getGameState()		/* Manually get gamestate without using observer/observable */
	{
		GameState gameState = (GameState)receive();
		return gameState;
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

	public void shutdown()
	{
		System.out.println("Shutting down...");

		try
		{
			dos.flush();
			oos.flush();
			dos.close();
			oos.close();

			clientSocket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.exit(1);
	}

	@Override
	public void update(Observable observable, Object o)		/* Send command (List) to server */
	{
		System.out.println("Sending command " + ((List<String>)o).get(0) + " to server");
		sendCommand((List<String>)o);
	}
}
