package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;

import java.util.*;

public class CLI extends Observable implements Observer		/* FIXME: CLI gets stuck at the beginning of singleplayer matches, the first gamestate disappears */
{
	private final Scanner input;
	private final ActionExecutor action;
	private final MessageDecoder messageDecoder;
	private NetworkHandler networkHandler;
	private GameState gameState;
	private String username;

	public CLI()
	{
		input = new Scanner(System.in);
		gameStart();
		new Thread(networkHandler).start();		/* Start listening for messages or gamestate updates from server */
		action = new ActionExecutor(this);		/* Pass CLI to ActionExecutor for the NetworkHandler and input instance, gamestate and username */
		messageDecoder = new MessageDecoder(action);
	}

	private void gameStart()
	{
		System.out.print("Username: ");
		username = input.nextLine();
		System.out.print("Server IP: ");
		//String ip = input.nextLine();		/* For debugging */
		String ip = "127.0.0.1";
		System.out.print("Server port: ");
		//int port = Integer.parseInt(input.nextLine());
		int port = 2000;

		networkHandler = new NetworkHandler(ip, port);
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get the new gamestate */
		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.send(username);

		if (networkHandler.isFirstPlayer())
		{
			System.out.print("Total players: ");
			networkHandler.send(input.nextLine());
		}

		System.out.println("Created network handler");
		networkHandler.waitForPlayers();
		System.out.println("Starting match\n\nMasters of the Renaissance!");
	}
	
	private void chooseAction(int choice)			/* Actions class? */
	{
		switch (choice)
		{
			case 0:
				action.leaderChoice();
				action.buyResources();
				break;

			case 1:
				action.leaderChoice();
				action.buyDevCard();
				break;

			case 2:
				action.leaderChoice();
				action.activateProduction();
				break;

			case 3:
				PrintMethods.printPlayerLeaderCards(gameState.getPlayerByName(username).getLeaderCards());
				PrintMethods.printPlayerDevCards(gameState.getPlayerByName(username).getDashboard().getAllDevCards());
				break;

			case 4:
				PrintMethods.printTrack(gameState.getCurrTrack(), gameState.getCurrPlayers());
				PrintMethods.printBoard(gameState.getPlayerByName(username).getDashboard());
				break;

			case 5:
				PrintMethods.printDevCardsMarket(gameState.getCurrDevCardsMarket());
				PrintMethods.printMarblesMarket(gameState.getCurrMarblesMarket());
				break;

			default:
				System.out.println("Invalid action number");

		}

		if (choice == 3 || choice == 4 || choice == 5)			/* Player can choose again after viewing things */
		{
			System.out.print("What do you want to do now?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");
			chooseAction(Integer.parseInt(input.nextLine()));
		}
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(messageDecoder);		/* Calls method in cli specified in the message */

		if (obj instanceof GameState)		/* TODO: check if match is over, or make server send "match over" message */
		{
			this.gameState = (GameState) obj;        /* Gamestate is needed in game loop, not during setup */
			//System.out.println(gameState.getCurrPlayers().get(0).getUsername() + " is active? " + gameState.getCurrPlayers().get(0).isMyTurn());
			//System.out.println(gameState.getCurrPlayers().get(1).getUsername() + " is active? " + gameState.getCurrPlayers().get(1).isMyTurn());

			if (gameState.getCurrPlayerName() != null)
			{
				if (gameState.getPlayerByName(username).isMyTurn())
				{
					System.out.println("It's your turn!");
					System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");
					chooseAction(Integer.parseInt(input.nextLine()));
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
