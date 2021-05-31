package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.localserver.LocalServer;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;

import java.util.*;

public class CLI extends Observable implements Observer
{
	private final Scanner input;
	private final ActionExecutor action;
	private final MessageDecoder messageDecoder;
	private NetworkHandler networkHandler;
	private GameState gameState;
	private String username;

	public CLI(Scanner input)
	{
		this.input = input;

		gameStart();

		new Thread(networkHandler).start();		/* Start listening for messages or gamestate updates from server */
		action = new ActionExecutor(this);		/* Pass CLI to ActionExecutor for the NetworkHandler and input instance, gamestate and username */
		messageDecoder = new MessageDecoder(action);
	}

	private void gameStart()
	{
		String ip = "";
		int port = 0;

		System.out.print("Username: ");
		username = input.nextLine();

		System.out.print("Singleplayer local match? (Y/N) ");

		switch (input.nextLine().toUpperCase())
		{
			case "Y":

				ip = "127.0.0.1";
				port = 2000;

				LocalServer localServer = new LocalServer();
				new Thread(localServer).start();
				break;

			case "N":

				System.out.print("Server IP: ");
				//String ip = input.nextLine();		/* For debugging */
				ip = "127.0.0.1";
				System.out.print("Server port: ");
				//int port = Integer.parseInt(input.nextLine());
				port = 2000;
		}

		networkHandler = new NetworkHandler(ip, port);
		System.out.println("Created network handler");
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get gamestates and messages */
		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.send(username);
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(messageDecoder);		/* Calls method in cli specified in the message */

		if (obj instanceof GameState)
		{
			this.gameState = (GameState) obj;		/* Gamestate is needed in game loop, not during setup */
			//System.out.println(gameState.getCurrPlayers().get(0).getUsername() + " is active? " + gameState.getCurrPlayers().get(0).isMyTurn());
			//System.out.println(gameState.getCurrPlayers().get(1).getUsername() + " is active? " + gameState.getCurrPlayers().get(1).isMyTurn());

			if (gameState.getCurrPlayerName() != null)
			{
				if (gameState.getPlayerByName(username).isMyTurn())
				{
					System.out.print("It's your turn! ");
					action.chooseAction();
				}

				else
					System.out.println("It's " + gameState.getCurrPlayerName() + "'s turn!");
			}
		}
	}

	public NetworkHandler getNetworkHandler()
	{
		return networkHandler;
	}

	public Scanner getInput()
	{
		return input;
	}

	public GameState getGameState()
	{
		return gameState;
	}

	public String getUsername()
	{
		return username;
	}
}
