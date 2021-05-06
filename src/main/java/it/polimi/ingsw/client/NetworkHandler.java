package it.polimi.ingsw.client;

import java.io.IOException;
import java.util.Observable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Observer;

public class NetworkHandler implements Runnable, Observer
{
	private final Socket clientSocket;
	private DataInputStream dis;
	private DataOutputStream dos;

	public NetworkHandler(Socket clientSocket)
	{
		this.clientSocket = clientSocket;

		try
		{
			dos = new DataOutputStream(clientSocket.getOutputStream());
			dis = new DataInputStream(clientSocket.getInputStream());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		while(!clientSocket.isClosed())
		{
			String message = "";

			try
			{
				message = dis.readUTF();			/* Send gamestate to another class, which is observed by the view? */
			}
			catch (IOException e)
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

	@Override
	public void update(Observable observable, Object o)
	{

	}
}
