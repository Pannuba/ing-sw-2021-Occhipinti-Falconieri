package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * Server view class. Receives commands sent by the clients and sends them to the Controller, and sends Messages and GameStates to the clients
 * @author Giulio Occhipinti
 */

public class ClientHandler extends Observable implements Runnable, Observer		/* Observes model waiting for changes, observed by controller */
{
	private final Socket clientSocket;
	private final String username;
	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	private final Timer heartbeat;
	private final TimerTask sendPing;

	/**
	 * Constructor. Starts the heartbeat that sends pings every 20 seconds using a Timer and TimerTask
	 * @param clientSocket the socket created in ServerListener that sends and receives game data
	 * @param username the username used by the socket's client. Used for debugging
	 * @param ois the socket's input stream created in ServerListener
	 * @param oos the socket's output stream created in ServerListener
	 */

	public ClientHandler(Socket clientSocket, String username, ObjectInputStream ois, ObjectOutputStream oos)
	{
		System.out.println("Created client handler for: " + username);

		this.clientSocket = clientSocket;
		this.username = username;
		this.ois = ois;			/* Can't create a I/O stream for a socket that already has one (created in ServerListener) */
		this.oos = oos;

		sendPing = new TimerTask() {
			public void run() {
				send(new Ping()); } };

		heartbeat = new Timer();
		heartbeat.scheduleAtFixedRate(sendPing, 5000, 10000);		/* Start heartbeat after 5 seconds, sends ping every timeout/2 seconds */
	}

	/**
	 * Thread waiting for new commands from the clientSocket. Forwards the messages received to the Controller through the Observer pattern
	 */

	public void run()		/* Activates after the setup phase has ended */
	{
		System.out.println("Started " + username + "'s clienthandler thread");
		Object inputObj;

		while (!clientSocket.isClosed())		/* Translate message, then call respective function in controller */
		{
			try
			{
				//System.out.println(username + " waiting for message from client");
				inputObj = ois.readObject();		/* Throws SocketException: Socket closed when shutting down server */

				if (!(inputObj instanceof Ping))		/* Don't care about pings */
				{
					//System.out.println("Received " + inputObj + " from " + username);		/* Prints the command */
					setChanged();
					notifyObservers(inputObj);		/* Sends command to controller */
				}
			}
			catch (IOException | ClassNotFoundException e)				/* EOFException when client disconnects. Catch it? */
			{
				e.printStackTrace();
				System.out.println(username + " disconnected!");
				close();
			}
		}
	}

	/**
	 * Used by other server classes to send object to clients
	 * @param obj the object to send to the client. Either a Ping, a Message, or a GameState
	 */

	public void send(Object obj)
	{
		if (!obj.getClass().getSimpleName().equals("Ping"))		/* Pings are really annoying */
			System.out.println("Sending " + obj.getClass().getSimpleName() + " to " + username);
		try
		{
			oos.writeObject(obj);
			oos.reset();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Executed when the socket's timeout expires or an exception happens. Stops the heartbeat, closes the I/O streams and the socket
	 */

	public void close()
	{
		System.out.println("Closing view of player " + username);

		try
		{
			sendPing.cancel();
			heartbeat.cancel();
			heartbeat.purge();

			ois.close();
			oos.flush();
			oos.close();

			clientSocket.close();
			Thread.currentThread().interrupt();		/* Order of things? */
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable obs, Object obj)			/* Send gamestate, match over and vatican report messages received from model */
	{
		send(obj);
	}

	public String getUsername()			/* Used by controller */
	{
		return username;
	}
}
