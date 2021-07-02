package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.localmatch.LocalMatchIO;
import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.localmatch.LocalMatch;
import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.cli.actions.ActivateProduction;
import it.polimi.ingsw.client.view.cli.actions.BuyDevCard;
import it.polimi.ingsw.client.view.cli.actions.BuyResources;
import it.polimi.ingsw.client.view.cli.actions.LeaderChoice;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.Message;

import java.util.*;

/**
 * Main CLI class
 */

public class CLI extends View
{
	private final Scanner input;
	private GameState gameState;
	private String username;

	/**
	 * Constructor, creates a Scanner instance and starts the game
	 */

	public CLI()
	{
		this.input = new Scanner(System.in);

		gameStart();

		action = new ActionCLI(this);		/* Pass CLI to ActionExecutor for the NetworkHandler and input instance, gamestate and username */
	}

	private void gameStart()
	{
		String ip;
		int port;

		System.out.print("Username: ");
		username = input.nextLine();

		System.out.print("Singleplayer local match? (Y/N) ");

		if (input.nextLine().equalsIgnoreCase("Y"))
		{
			Player player = new Player(username);
			LocalModel model = new LocalModel(player, this);
			LocalController lc = new LocalController(model, this);
			messageHandler = new LocalMatchIO(lc);
			LocalMatch match = new LocalMatch(model, this);		/* M, V, C */
			new Thread(match).start();
		}

		else
		{
			System.out.print("Server IP: ");
			ip = input.nextLine();
			System.out.print("Server port: ");
			port = Integer.parseInt(input.nextLine());
			messageHandler = new NetworkHandler(this, ip, port);
			System.out.println("Created network handler");
			/* Try/catch if connection fails? */
			messageHandler.connect();
			new Thread((Runnable) messageHandler).start();		/* Start listening for messages or gamestate updates from server */
			System.out.println("Sending username to server...");
			messageHandler.send(username);
		}
	}

	public void chooseAction()			/* Here or ActionCLI? */
	{
		System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5), activate/discard a leader (6): ");

		String choice = input.nextLine();

		List<String> command = new ArrayList<>();

		switch (choice)
		{
			case "0":
				command = new BuyResources(input, this).run();
				break;

			case "1":
				command = new BuyDevCard(input, this).run();
				break;

			case "2":
				command = new ActivateProduction(input, this).run();
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

			case "6":
				new LeaderChoice(input, messageHandler, this);		/* Pass the messageHandler in case the user wants to activate/discard twice */
				break;

			default:
				System.out.println("Invalid action number");
				chooseAction();
				return;
		}

		if (choice.equals("3") || choice.equals("4") || choice.equals("5"))			/* Player can choose again after viewing things */
			chooseAction();

		else if (command != null)
			messageHandler.send(command);
	}

	public synchronized void update(Object obj)
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

	public MessageIO getMessageHandler()
	{
		return messageHandler;
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
