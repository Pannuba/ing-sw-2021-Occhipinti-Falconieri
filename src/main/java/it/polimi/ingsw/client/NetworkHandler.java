package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Observer;

import static java.lang.Boolean.parseBoolean;

public class NetworkHandler extends Observable implements Runnable
{
	private Socket clientSocket;
	private ObjectInputStream ois;
	private DataOutputStream dos;
	private String username, ip;
	private int port;

	public NetworkHandler(String username, String ip, int port)
	{
		this.username = username;
		this.ip = ip;
		this.port = port;
	}

	public void run()
	{
		System.out.println("Waiting for players to connect...");
		waitForPlayers();
		System.out.println("Starting match");

		while(!clientSocket.isClosed())
		{
			GameState gameState = null;

			try
			{
				gameState = (GameState) ois.readObject();			/* Send gamestate to the view? */
				notifyObservers(gameState);
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		try					/* When the socket is closed close the output stream, maybe notify the view */
		{
			dos.flush();
			dos.close();
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
			dos = new DataOutputStream(clientSocket.getOutputStream());		/* Send commands to server as strings */
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

	private void waitForPlayers()
	{
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
		}
	}

	public void send(String message)
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
}
