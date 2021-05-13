package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.cards.*;

import java.sql.Array;
import java.util.*;

/* 	FIXME: new gamestate sometimes has no leadercards that were picked
	TODO: put command generation functions in controller, make CLI call them
*/

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
		new Thread(networkHandler).start();		/* Start listening for gamestate updates from server. Put this after game setup? Yes. */
		gameLoop();
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

		networkHandler = new NetworkHandler(this, username, ip, port);
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

		List<LeaderCard> fourLeaderCards = networkHandler.getFourLeadercards();		/* Server tells clients info about the four leadercards */
		System.out.println("Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
							", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ":");

		for (int i = 0; i < 4; i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));
		}

		String cardChoice1 = input.nextLine();					/* Has to be the leadercard number */
		System.out.println("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();
		List<String> command = new ArrayList<String>();
		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);
		System.out.println("Notifying observers (network handler)");
		setChanged();						/* Apparently this is necessary whenever notifyObservers is called */
		notifyObservers(command);		/* Send array of strings to server controller */
	}

	private void gameLoop()
	{
		System.out.println("It's ");
	}
	
	private void chooseAction(int action)			/* Actions class? */
	{
		List<String> command = new ArrayList<String>();

		switch(action)
		{
			case 0:
				System.out.print("This is the current marbles market:\n\n");
				PrintMethods.printMarblesMarket(gameState.getCurrMarblesMarket());
				System.out.print("Select a row and column\nrow: ");
				String row = input.nextLine();		/* String which will be converted to int server-side */
				System.out.println("Column: ");
				String col = input.nextLine();
				command.add("BUY_RESOURCES");
				command.add(row);
				command.add(col);
				networkHandler.sendCommand(command);
				break;

			case 1:
				System.out.print("These are your current dev card areas:\n\n");
				PrintMethods.printDevCardAreas(gameState.getPlayerByName(username).getDashboard().getDevCardAreas());

				command.add("BUY_DEVCARD");
				networkHandler.sendCommand(command);
				break;

			case 2:
				command.add("ACTIVATE_PRODUCTION");		/* Same */
				break;

			case 3:
				PrintMethods.printPlayerLeaderCards(gameState.getPlayerByName(username).getLeaderCards());
				PrintMethods.printPlayerDevCards(gameState.getPlayerByName(username).getDevCards());
				break;

			case 4:
				PrintMethods.printBoard(gameState.getCurrTrack(), gameState.getPlayerByName(username).getDashboard());
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

	@Override
	public void update(Observable observable, Object o)
	{
		System.out.println("Gamestate received");
		this.gameState = (GameState)o;		/* Gamestate is needed in game loop, not during setup */
		System.out.println("It's " + gameState.getCurrPlayerName() + "'s turn!");

		if (gameState.getPlayerByName(username).isMyTurn())
		{
			System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");
			chooseAction(Integer.parseInt(input.nextLine()));
		}

		else
		{

		}
	}
}
