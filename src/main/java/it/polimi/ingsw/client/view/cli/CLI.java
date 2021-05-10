package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.controller.Controller;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.cards.*;

import java.sql.Array;
import java.util.*;

public class CLI extends Observable implements Observer
{
	private final Scanner input;
	private NetworkHandler networkHandler;
	private final Controller controller;
	private GameState gameState;

	public CLI()
	{
		controller = new Controller();		/* Pass networkHandler instance to controller? Create NH thread in controller? */
		input = new Scanner(System.in);
		gameStart();
		matchSetup();
		//new Thread(networkHandler).start();		/* Start listening for gamestate updates from server. Put this after game setup? Yes. */
	}

	private void gameStart()
	{
		System.out.print("Username: ");
		String username = input.nextLine();
		System.out.print("Server IP: ");
		String ip = input.nextLine();
		System.out.print("Server port: ");
		int port = Integer.parseInt(input.nextLine());

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
		System.out.println("Choose leader card 1, 2, 3 or 4: ");

		for (int i = 0; i < 4; i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));
		}

		String cardChoice1 = input.nextLine();					/* Has to be a number */
		System.out.println("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();
		List<String> action = new ArrayList<String>();
		action.add("SELECT_LEADERCARDS");
		action.add(cardChoice1);
		action.add(cardChoice2);
		notifyObservers(action);		/* Send array of strings to server controller? */

	}

	private void chooseAction(int action)	/* "What do you want to do? buy from market (0), activate production (1), view cards (2), view board (3), view markets (4) */
	{
		switch(action)
		{
			case 0:
				/* Call controller function, or just send the command through the networkhandler */
				break;

			case 1:
				/* Same */
				break;

			case 2:
				//PrintMethods.printPlayerLeaderCards();		/* gamestate.getPlayerByName(username).getLeadercards */
				//PrintMethods.printPlayerDevCards();
				break;

			case 3:
				//PrintMethods.printBoard();
				break;

			case 4:
				//PrintMethods.printDevCardsMarket();
				//PrintMethods.printMarblesMarket();
				break;

			default:
				System.out.println("Invalid action number");

		}
	}

	@Override
	public void update(Observable observable, Object o)
	{
		this.gameState = (GameState)o;		/* Gamestate is needed in game loop, not during setup */
	}
}
