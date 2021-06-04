package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.localserver.LocalServer;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;

import java.util.*;

/* Abstract class View, CLI extends view? */

public class CLI extends Observable implements Observer
{
	private final Scanner input;
	private final ActionCLI action;
	private NetworkHandler networkHandler;
	private GameState gameState;
	private String username;

	public CLI(Scanner input)
	{
		this.input = input;

		gameStart();

		new Thread(networkHandler).start();		/* Start listening for messages or gamestate updates from server */
		action = new ActionCLI(this);		/* Pass CLI to ActionExecutor for the NetworkHandler and input instance, gamestate and username */
	}

	private void gameStart()
	{
		String ip = "";
		int port = 0;

		System.out.print("Username: ");
		username = input.nextLine();

		System.out.print("Singleplayer local match? (Y/N) ");

		if (input.nextLine().equalsIgnoreCase("Y"))
		{
			ip = "127.0.0.1";
			port = 2000;

			LocalServer localServer = new LocalServer();
			new Thread(localServer).start();
		}

		else
		{
			System.out.print("Server IP: ");
			ip = input.nextLine();		/* For debugging */
			//ip = "127.0.0.1";
			System.out.print("Server port: ");
			port = Integer.parseInt(input.nextLine());
			//port = 2000;
		}

		networkHandler = new NetworkHandler(ip, port);
		System.out.println("Created network handler");
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get gamestates and messages */
		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.send(username);
	}

	public void chooseAction()			/* Here or ActionCLI? */
	{
		System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");

		String choice = input.nextLine();

		switch (choice)
		{
			case "0":
				action.leaderChoice();
				action.buyResources();
				break;

			case "1":
				action.leaderChoice();
				action.buyDevCard();
				break;

			case "2":
				action.leaderChoice();
				action.activateProduction();
				break;

			case "3":
				PrintMethods.printPlayerLeaderCards(gameState.getPlayerByName(username).getLeaderCards());
				PrintMethods.printPlayerDevCards(gameState.getPlayerByName(username).getDashboard().getAllDevCards());
				break;

			case "4":
				PrintMethods.printTrack(gameState.getCurrTrack(), gameState.getCurrPlayers());
				PrintMethods.printBoard(gameState.getPlayerByName(username).getDashboard());
				break;

			case "5":
				PrintMethods.printDevCardsMarket(gameState.getCurrDevCardsMarket());
				PrintMethods.printMarblesMarket(gameState.getCurrMarblesMarket());
				break;

			default:
				System.out.println("Invalid action number");
				chooseAction();
				return;
		}

		if (choice.equals("3") || choice.equals("4") || choice.equals("5"))			/* Player can choose again after viewing things */
			chooseAction();
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in cli specified in the message */

		if (obj instanceof GameState)
		{
			this.gameState = (GameState) obj;		/* Gamestate is needed in game loop, not during setup */

			if (gameState.getPlayerByName(username).isMyTurn())
			{
				System.out.print("It's your turn! ");
				chooseAction();
			}

			else
				System.out.println("It's " + gameState.getCurrPlayerName() + "'s turn!");
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
