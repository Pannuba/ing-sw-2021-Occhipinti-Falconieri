package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionExecutor		/* Has methods that perform actions such as buying resources, to avoid cluttering the CLI */
{
	private final Scanner input;
	private final CLI cli;

	public ActionExecutor(CLI cli)
	{
		this.cli = cli;
		this.input = cli.getInput();
	}

	public void chooseLeaderCards(List<LeaderCard> fourLeaderCards)
	{
		NetworkHandler networkHandler = cli.getNetworkHandler();

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

	public void chooseResources()			/* 1st player: nothing; 2nd: 1 resource; 3rd: 1 resource + 1 faithPoint; 4th: 2 resources + 1 faithPoint */
	{
		NetworkHandler networkHandler = cli.getNetworkHandler();
		GameState gameState = networkHandler.getGameState();
		List<String> command = new ArrayList<String>();
		String chosenResources = "";			/* Will be converted to ResourceType in server controller */
		String initialFaithPoints = "";

		switch (gameState.getPlayerByName(cli.getUsername()).getId())
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

	public void buyResources()
	{
		List<String> command = new ArrayList<>();
		command.add("BUY_RESOURCES");

		System.out.print("This is the current marbles market:\n\n");
		PrintMethods.printMarblesMarket(cli.getGameState().getCurrMarblesMarket());

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
		cli.getNetworkHandler().sendCommand(command);
	}

	public void buyDevCard()
	{
		List<String> command = new ArrayList<>();
		command.add("BUY_DEVCARD");
		System.out.print("These are your current dev card areas:\n\n");
		PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
		System.out.print("Which dev card area do you want to put the new card in? ");
		int devCardAreaIndex = Integer.parseInt(input.nextLine());		/* 1, 2 or 3			- 1 because arrays are zero-indexed */
		int targetAreaLayer = cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();

		if (targetAreaLayer == 3)
		{
			System.out.println("This dev card area already has three cards!");			/* Get input again */
		}

		else			/* Print devcards of level targetAreaLayer + 1 (layer = level of top devcard. New card has to have a higher level) */
		{
			PrintMethods.printDevCardsMarketLevel(cli.getGameState().getCurrDevCardsMarket(), targetAreaLayer + 1);
		}

		System.out.print("Insert the card number you want to buy: ");

		command.add(input.nextLine());

		//DevCard boughtCard = gameState.getCurrDevCardsMarket().getDevCardByNumber(cardNumberToBuy);

		//checkResources(gameState.getPlayerByName(username).getDashboard(), boughtCard);

																		/*	Check for resources. Here or server? LocalModel class?
																			If client-side check, send new vault, storage and devcard market.
																			If server-side check, send messages. I think server side is better.
																		*/

		cli.getNetworkHandler().sendCommand(command);
	}

	public void activateProduction()
	{

	}
}
