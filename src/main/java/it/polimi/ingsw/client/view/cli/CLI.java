package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CLI extends Observable implements Observer
{
	private final Scanner input;
	private NetworkHandler networkHandler;
	private GameState gameState;
	private String username;

	public CLI()
	{
		input = new Scanner(System.in);
		gameStart();
		matchSetup();
		new Thread(networkHandler).start();		/* Start listening for gamestate updates from server. Put this after game setup? Maybe. */

		//gameLoop();
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

		networkHandler = new NetworkHandler(username, ip, port);
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get the new gamestate */
		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.sendString(username);

		if (networkHandler.isFirstPlayer())
		{
			System.out.print("Total players: ");
			networkHandler.sendString(input.nextLine());
		}

		System.out.println("Created network handler");
		networkHandler.waitForPlayers();
		System.out.println("Starting match");
	}

	private void matchSetup()
	{
		System.out.println("Masters of the Renaissance!");

		chooseLeaderCards();
		chooseResources();

	}

	/*private void gameLoop()
	{
		System.out.println("It's ");
	}*/
	
	private void chooseAction(int action)			/* Actions class? */
	{
		List<String> command = new ArrayList<String>();

		switch(action)
		{
			case 0:
				command.add("BUY_RESOURCES");

				System.out.print("This is the current marbles market:\n\n");
				PrintMethods.printMarblesMarket(gameState.getCurrMarblesMarket());

				String rowOrColNum = "";
				System.out.print("\nSelect a row (1) or column(2)? ");
				int choice = Integer.parseInt(input.nextLine());

				if (choice == 1)
				{
					command.add("ROW");
					System.out.print("Insert row #: ");
					rowOrColNum = input.nextLine();
				}

				if (choice == 2)
				{
					command.add("COLUMN");
					System.out.print("Insert column #: ");
					rowOrColNum = input.nextLine();
				}

				command.add(rowOrColNum);
				networkHandler.sendCommand(command);

				/* Need to wait until QueuedObj changes... */
				try{
					TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
				List<Resource> boughtResources = (List<Resource>)networkHandler.getQueuedObj();		/* Only ask for input if resources have to be discarded */
				System.out.println("Bought the following resources: " + boughtResources);
				break;

			case 1:
				command.add("BUY_DEVCARD");
				System.out.print("These are your current dev card areas:\n\n");
				PrintMethods.printDevCardAreas(gameState.getPlayerByName(username).getDashboard().getDevCardAreas());
				System.out.print("Which dev card area do you want to put the new card in? ");
				int devCardAreaIndex = Integer.parseInt(input.nextLine());		/* 1, 2 or 3			- 1 because arrays are zero-indexed */
				int targetAreaLayer = gameState.getPlayerByName(username).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();

				if (targetAreaLayer == 3)
				{
					System.out.println("This dev card area already has three cards!");			/* Get input again */
				}

				else			/* Print devcards of level targetAreaLayer + 1 (layer = level of top devcard. New card has to have a higher level) */
				{
					PrintMethods.printDevCardsMarketLevel(gameState.getCurrDevCardsMarket(), targetAreaLayer + 1);
				}

				System.out.print("Insert the card number you want to buy: ");

				command.add(input.nextLine());

				//DevCard boughtCard = gameState.getCurrDevCardsMarket().getDevCardByNumber(cardNumberToBuy);

				//checkResources(gameState.getPlayerByName(username).getDashboard(), boughtCard);

																		/*	Check for resources. Here or server? LocalModel class?
																			If client-side check, send new vault, storage and devcard market.
																			If server-side check, send messages. I think server side is better.
																		*/

				networkHandler.sendCommand(command);
				break;

			case 2:
				command.add("ACTIVATE_PRODUCTION");
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

		if (action == 3 || action == 4 || action == 5)			/* Player can choose again after viewing things */
		{
			System.out.print("What do you want to do now?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");
			chooseAction(Integer.parseInt(input.nextLine()));
		}
	}

	private void chooseLeaderCards()			/* TODO: server waits for all players to pick leadercards */
	{
		List<LeaderCard> fourLeaderCards = networkHandler.getFourLeadercards();		/* Server tells clients info about the four leadercards */

		for (int i = 0; i < 4; i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));
			System.out.print("\n");
		}

		System.out.print(	"Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
							", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ": ");

		String cardChoice1 = input.nextLine();					/* Has to be the leadercard number */
		System.out.print("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();

		List<String> command = new ArrayList<String>();
		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);

		System.out.println("Notifying observers (network handler)");
		networkHandler.sendCommand(command);
	}

	private void chooseResources()			/* 1st player: nothing; 2nd: 1 resource; 3rd: 1 resource + 1 faithPoint; 4th: 2 resources + 1 faithPoint */
	{
		gameState = networkHandler.getGameState();
		List<String> command = new ArrayList<String>();
		String chosenResources = "";			/* Will be converted to ResourceType in server controller */
		String initialFaithPoints = "";

		switch (gameState.getPlayerByName(username).getId())
		{
			case 0:
				System.out.println("You're the first player, so you get nothing!");
				break;

			case 1:
				System.out.print("Choose 1 starting resource (B)lue, (G)rey, (Y)ellow, (P)urple: ");		/* Check for input */
				chosenResources += input.nextLine();
				break;

			case 2:
				System.out.print("Choose 1 starting resource (B)lue, (G)rey, (Y)ellow, (P)urple: ");
				chosenResources += input.nextLine();
				initialFaithPoints = "1";
				break;

			case 3:
				System.out.print("Choose 2 starting resources (B)lue, (G)rey, (Y)ellow, (P)urple\nResource 1: ");
				chosenResources += input.nextLine();
				System.out.print("Resource 2: ");
				chosenResources += input.nextLine();
				initialFaithPoints = "2";
				break;

			default:
				System.out.println("Something bad happened");

		}

		command.add("INITIAL_RESOURCES");
		command.add(chosenResources);
		command.add(initialFaithPoints);
		networkHandler.sendCommand(command);
	}

	@Override
	public void update(Observable observable, Object o)		/* Make a (Local)GameState thread that gets the new gamestate? */
	{
		/* Check if match is over */
		this.gameState = (GameState)o;		/* Gamestate is needed in game loop, not during setup */

		if (gameState.getPlayerByName(username).isMyTurn())
		{
			System.out.println("It's your turn!");
			System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");
			chooseAction(Integer.parseInt(input.nextLine()));
		}

		else
		{
			System.out.println("It's " + gameState.getCurrPlayerName() + "'s turn!");
		}
	}
}
