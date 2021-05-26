package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionExecutor		/* Has methods that perform actions such as buying resources, to avoid cluttering the CLI */
{
	private final Scanner input;
	private final CLI cli;
	private final NetworkHandler networkHandler;
	private final List<String> command;

	public ActionExecutor(CLI cli)
	{
		this.cli = cli;
		this.input = cli.getInput();
		this.networkHandler = cli.getNetworkHandler();
		command = new ArrayList<>();
	}

	public void chooseLeaderCards(List<LeaderCard> fourLeaderCards)
	{
		for (int i = 0; i < 4; i++)
		{
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));
			System.out.print("\n");
		}

		System.out.print(	"Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
				", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ": ");

		String cardChoice1 = input.nextLine();					/* Has to be the leadercard number */
		System.out.print("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();

		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);

		System.out.println("Notifying observers (network handler)");
		networkHandler.sendCommand(command);
		command.clear();		/* So the same command List<String> instance can be used in all functions */
	}

	public void chooseResources()			/* 1st player: nothing; 2nd: 1 resource; 3rd: 1 resource + 1 faithPoint; 4th: 2 resources + 1 faithPoint */
	{
		GameState gameState = networkHandler.getGameState();
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
		command.clear();
	}

	public void leaderChoice()
	{
		System.out.print("Do you want to activate or discard a leader? A/D: ");

		switch (input.nextLine().toUpperCase())
		{
			case "A":
				activateLeader();
				break;

			case "D":
				discardLeader();
				break;
		}
	}

	private void activateLeader()		/* Sends command to activate a leader, server checks resources and replies with another message */
	{
		List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
		PrintMethods.printPlayerLeaderCards(leaderCards);
		System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
		String chosenCard = input.nextLine();

		command.add("ACTIVATE_LEADER");
		command.add(chosenCard);
		networkHandler.sendCommand(command);
	}

	private void discardLeader()
	{
		List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
		PrintMethods.printPlayerLeaderCards(leaderCards);
		System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
		String chosenCard = input.nextLine();

		command.add("DISCARD_LEADER");
		command.add(chosenCard);
		networkHandler.sendCommand(command);
	}

	public void buyResources()
	{
		System.out.print("This is the current marbles market:\n\n");
		PrintMethods.printMarblesMarket(cli.getGameState().getCurrMarblesMarket());

		String rowOrCol = "", rowOrColNum = "";
		System.out.print("\nSelect a row (1) or column(2)? ");
		int choice = Integer.parseInt(input.nextLine());

		if (choice == 1)
		{
			rowOrCol = "ROW";
			System.out.print("Insert row #: ");
			rowOrColNum = input.nextLine();
		}

		if (choice == 2)
		{
			rowOrCol = "COLUMN";
			System.out.print("Insert column #: ");
			rowOrColNum = input.nextLine();
		}

		command.add("BUY_RESOURCES");
		command.add(rowOrCol);
		command.add(rowOrColNum);
		cli.getNetworkHandler().sendCommand(command);
		command.clear();
	}

	public void buyDevCard()
	{
		int devCardAreaIndex, targetAreaLayer;
		System.out.print("These are your current dev card areas:\n\n");
		PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
		System.out.print("Which dev card area do you want to put the new card in? ");
		devCardAreaIndex = Integer.parseInt(input.nextLine());		/* 1, 2 or 3			- 1 because arrays are zero-indexed */
		targetAreaLayer = cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();

		while (targetAreaLayer == 3)
		{
			System.out.print("This dev card area already has three cards!\nWhich dev card area do you want to put the new card in? ");
			devCardAreaIndex = Integer.parseInt(input.nextLine());		/* 1, 2 or 3			- 1 because arrays are zero-indexed */
			targetAreaLayer = cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();
		}

		/* Print devcards of level targetAreaLayer + 1 (layer = level of top devcard. New card has to have a higher level) */
		PrintMethods.printDevCardsMarketLevel(cli.getGameState().getCurrDevCardsMarket(), targetAreaLayer + 1);

		System.out.print("Insert the card number you want to buy: ");

		command.add("BUY_DEVCARD");
		command.add(input.nextLine());
		cli.getNetworkHandler().sendCommand(command);
		command.clear();

	/*	Check for resources. Here or server? SERVER		LocalModel class? NO
		If client-side check, send new vault, storage and devcard market. If server-side check, send messages. I think server side is better. YES	*/
	}

	public void activateProduction()		/* How many times can a production be repeated? See rules */
	{
		System.out.println("This is your current storage and vault:");
		PrintMethods.printStorage(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getStorage());
		PrintMethods.printVault(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getVault());
		System.out.print("Do you want to use the default production (1), a devcard (2) or a SkillProduction leader card (3)? ");

		switch (input.nextLine())
		{
			case "1":
				String resourceToConvert = "", resourceToMake = "";
				System.out.print("Insert the resource type you want to convert (B/G/Y/P): ");
				resourceToConvert = input.nextLine();
				System.out.print("Insert the resource type you want to make (B/G/Y/P): ");
				resourceToMake = input.nextLine();

				command.add("ACTIVATE_PRODUCTION");
				command.add("DEFAULT");
				command.add(resourceToConvert);
				command.add(resourceToMake);
				break;

			case "2":
				String cardNumber = "";
				PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
				System.out.print("Insert the devcard's # you want to use: ");
				cardNumber = input.nextLine();

				command.add("ACTIVATE_PRODUCTION");
				command.add("DEVCARD");
				command.add(cardNumber);
				break;

			case "3":
				List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
				List<LeaderCard> activeCardsWithProdSkill = new ArrayList<>();
				String chosenCardNum = "";

				for (int i = 0; i < leaderCards.size(); i++)
				{
					if (leaderCards.get(i) instanceof SkillProduction && leaderCards.get(i).isActive())
						activeCardsWithProdSkill.add(leaderCards.get(i));
				}

				if (activeCardsWithProdSkill.isEmpty())
					System.out.println("You don't have any active leaders that have a production skill!");

				else
				{
					for (int i = 0; i < activeCardsWithProdSkill.size(); i++)			/* For the rare chance that the player has both cards SkillProduction */
						PrintMethods.printLeaderCard(activeCardsWithProdSkill.get(i));

					System.out.println("Which card do you want to use?");
					chosenCardNum = input.nextLine();

					command.add("ACTIVATE_PRODUCTION");
					command.add("SKILLPRODUCTION");
					command.add(chosenCardNum);
				}
		}

		cli.getNetworkHandler().sendCommand(command);
		command.clear();
	}
}