package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*	Idea: make ActionExecutor an abstract class in the client package, with methods like "abstract void firstPlayer(boolean isFirstPlayer)"...
	then make ActionCLI and ActionGUI classes that extend ActionExecutor in the cli and gui package respectively, and put the @Override methods there
	MessageDecoder has an instance variable of ActionExecutor, it should still work.

	On a separate note, I should put the code for each action in its own class...
*/

public class ActionExecutor		/* Has methods that perform actions such as buying resources, to avoid cluttering the CLI. Interface? */
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

	public void firstPlayer(boolean isFirstPlayer)
	{
		if (isFirstPlayer)
		{
			System.out.print("Total players: ");
			networkHandler.send(input.nextLine());
		}
	}

	public void startMatch()
	{
		System.out.println("Starting match\n\nMasters of the Renaissance!");
	}

	public void chooseAction()
	{
		GameState gameState = cli.getGameState();
		System.out.print("What do you want to do?\nBuy from market (0), buy devcards (1), activate production (2), view cards (3), view board (4), view markets (5): ");

		String choice = input.nextLine();

		switch (choice)
		{
			case "0":
				leaderChoice();
				buyResources();
				break;

			case "1":
				leaderChoice();
				buyDevCard();
				break;

			case "2":
				leaderChoice();
				activateProduction();
				break;

			case "3":
				PrintMethods.printPlayerLeaderCards(gameState.getPlayerByName(cli.getUsername()).getLeaderCards());
				PrintMethods.printPlayerDevCards(gameState.getPlayerByName(cli.getUsername()).getDashboard().getAllDevCards());
				break;

			case "4":
				PrintMethods.printTrack(gameState.getCurrTrack(), gameState.getCurrPlayers());
				PrintMethods.printBoard(gameState.getPlayerByName(cli.getUsername()).getDashboard());
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

	public void chooseLeaderCards(List<LeaderCard> fourLeaderCards)
	{
		for (int i = 0; i < 4; i++)
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));

		System.out.print(	"Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
							", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ": ");

		String cardChoice1 = input.nextLine();					/* Has to be the leadercard number */
		System.out.print("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();

		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);

		System.out.println("Notifying observers (network handler)");
		networkHandler.send(command);
		command.clear();		/* So the same command List<String> instance can be used in all functions */
	}

	public void chooseResources(int playerID)			/* 1st player: nothing; 2nd: 1 resource; 3rd: 1 resource + 1 faithPoint; 4th: 2 resources + 1 faithPoint */
	{
		String chosenResources = "";			/* Will be converted to ResourceType in server controller */
		String initialFaithPoints = "";

		switch (playerID)
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
				initialFaithPoints = "1";			/* Add server-side check? */
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
		networkHandler.send(command);
		command.clear();
	}

	public void leaderChoice()
	{
		List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
		List<LeaderCard> inactiveLeaders = new ArrayList<>();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isDiscarded())
			{
				leaderCards.remove(i);		/* Discarded cards can't be activated nor discarded, so remove them */
				i--;
			}

			if (!leaderCards.get(i).isActive())
				inactiveLeaders.add(leaderCards.get(i));
		}

		if (leaderCards.isEmpty())
			return;			/* If the player has no leadercards or they are both discarded, skip the choice */

		System.out.print("Do you want to activate or discard a leader? A/D: ");

		switch (input.nextLine().toUpperCase())
		{
			case "A":		/* TODO: check if inactiveLeaders is empty */
				activateLeader(inactiveLeaders);		/* Only pass cards that can be activated */
				break;

			case "D":
				discardLeader(leaderCards);				/* Only pass cards that can be discarded */
				break;
		}
	}

	private void activateLeader(List<LeaderCard> leaderCards)		/* Sends command to activate a leader, server checks resources and replies with another message */
	{
		PrintMethods.printPlayerLeaderCards(leaderCards);
		System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
		String chosenCard = input.nextLine();

		command.add("ACTIVATE_LEADER");
		command.add(chosenCard);
		networkHandler.send(command);
		command.clear();
	}

	private void discardLeader(List<LeaderCard> leaderCards)
	{
		PrintMethods.printPlayerLeaderCards(leaderCards);
		System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
		String chosenCard = input.nextLine();

		command.add("DISCARD_LEADER");
		command.add(chosenCard);
		networkHandler.send(command);
		command.clear();
	}

	public void buyResources()
	{
		String rowOrCol, rowOrColNum, whiteMarbleRes = "", choice;

		System.out.print("This is the current marbles market:\n\n");
		PrintMethods.printMarblesMarket(cli.getGameState().getCurrMarblesMarket());

		System.out.print("\nSelect a row (1) or column(2)? (Q = choose another action) ");
		choice = input.nextLine();

		switch (choice)
		{
			case "1":
				rowOrCol = "ROW";
				System.out.print("Insert row #: ");
				rowOrColNum = input.nextLine();
				break;

			case "2":
				rowOrCol = "COLUMN";
				System.out.print("Insert column #: ");
				rowOrColNum = input.nextLine();
				break;

			default:
				chooseAction();
				return;		/* Without return it still sends a command after the recursion(?) which leads to chaos */
		}

		List<SkillMarble> marbleLeaders = cli.getGameState().getPlayerByName(cli.getUsername()).getMarbleLeaders();

		if (marbleLeaders.size() > 1)
		{
			System.out.print("You have " + marbleLeaders.size() + " leaders with a white marble skill!\nInsert the resource type you want to convert (G/Y/B/P): ");
			whiteMarbleRes = input.nextLine();
		}

		command.add("BUY_RESOURCES");
		command.add(rowOrCol);
		command.add(rowOrColNum);
		command.add(whiteMarbleRes);
		networkHandler.send(command);
		command.clear();
	}

	public void getBoughtResources(List<Resource> boughtResources)
	{
		System.out.print("Received the following resources: " + PrintMethods.convertResListToString(boughtResources) + "\n");
	}

	public void buyDevCard()
	{
		int devCardAreaIndex, targetAreaLayer;
		String choice, cardToBuyNum;

		System.out.print("These are your current dev card areas:\n\n");
		PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
		System.out.print("Insert the dev card area # you want to put the new card in (1/2/3) (Q = choose another action) ");
		choice = input.nextLine();

		if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3"))
		{
			chooseAction();
			return;
		}

		devCardAreaIndex = Integer.parseInt(choice);											/* - 1 because arrays are zero-indexed */
		targetAreaLayer = cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();

		while (targetAreaLayer == 3)
		{
			System.out.print("This dev card area already has three cards!\nWhich dev card area do you want to put the new card in? ");
			devCardAreaIndex = Integer.parseInt(input.nextLine());		/* 1, 2 or 3			- 1 because arrays are zero-indexed */
			targetAreaLayer = cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas()[devCardAreaIndex - 1].getLayer();
		}

		/* Print devcards of level targetAreaLayer + 1 (layer = level of top devcard. New card has to have a higher level) */
		PrintMethods.printDevCardsMarket(cli.getGameState().getCurrDevCardsMarket());

		System.out.print("Insert the card number you want to buy: ");
		cardToBuyNum = input.nextLine();

		command.add("BUY_DEVCARD");
		command.add(cardToBuyNum);
		command.add(Integer.toString(devCardAreaIndex));
		networkHandler.send(command);
		command.clear();

	/*	Check for resources. Here or server? SERVER		LocalModel class? NO
		If client-side check, send new vault, storage and devcard market. If server-side check, send messages. I think server side is better. YES */
	}

	public void getBoughtDevCard(DevCard boughtCard)
	{
		System.out.print("Received the following devcard:\n");
		PrintMethods.printDevCard(boughtCard);
	}

	public void activateProduction()		/* How many times can a production be repeated? See rules */
	{
		System.out.println("This is your current storage and vault:");
		PrintMethods.printStorage(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getStorage());
		PrintMethods.printVault(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getVault());
		System.out.print("Do you want to use the default production (1), a devcard (2) or a SkillProduction leader card (3)? (Q = choose another action) ");

		switch (input.nextLine())
		{
			case "1":

				String resourceToConvert, resourceToMake;
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

				String cardNumber;
				PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
				System.out.print("Insert the devcard's # you want to use: ");
				cardNumber = input.nextLine();

				command.add("ACTIVATE_PRODUCTION");
				command.add("DEVCARD");
				command.add(cardNumber);
				break;

			case "3":	/* Checks are both server side (to prevent cheating) and client side (to avoid useless server load and message exchange) */

				List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
				List<LeaderCard> activeCardsWithProdSkill = new ArrayList<>();
				String chosenCardNum, resourceToMakeLeader;

				for (int i = 0; i < leaderCards.size(); i++)
				{
					if (leaderCards.get(i) instanceof SkillProduction && leaderCards.get(i).isActive())
						activeCardsWithProdSkill.add(leaderCards.get(i));
				}

				if (activeCardsWithProdSkill.isEmpty())
				{
					System.out.println("You don't have any active leaders that have a production skill!");
					chooseAction();
					return;
				}

				else
				{
					for (int i = 0; i < activeCardsWithProdSkill.size(); i++)			/* For the rare chance that the player has both cards SkillProduction */
						PrintMethods.printLeaderCard(activeCardsWithProdSkill.get(i));

					System.out.print("Which card do you want to use? ");
					chosenCardNum = input.nextLine();

					System.out.print("Insert the resource you want to make (B/G/Y/P)");
					resourceToMakeLeader = input.nextLine();

					command.add("ACTIVATE_PRODUCTION");
					command.add("LEADER_SKILL");
					command.add(chosenCardNum);
					command.add(resourceToMakeLeader);
				}

				break;

			default:
				chooseAction();
				return;
		}

		networkHandler.send(command);
		command.clear();
	}

	public void getOperationResultMessage(String message, boolean isFailed)
	{
		System.out.println(message);

		if (isFailed)
			chooseAction();		/* Client can repeat the round for failed actions */
	}

	public void getActionToken(ActionToken token)
	{
		System.out.print("Received action token: ");
		PrintMethods.printActionToken(token);
	}

	public void vaticanReport(int popeBoxNum, List<Player> players)
	{
		Player localPlayer = new Player("temp");		/* Otherwise IntelliJ gives an annoying warning */

		System.out.println("Vatican report on box #" + popeBoxNum + "!");

		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getUsername().equals(cli.getUsername()))
				localPlayer = players.get(i);
		}

		switch (popeBoxNum)
		{
			case 8:

				if (localPlayer.getPopeTokens()[0].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[0].getPoints() + " victory points!");

				else
					System.out.println("Discarded first pope token");

				break;

			case 16:

				if (localPlayer.getPopeTokens()[1].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[1].getPoints() + " victory points!");

				else
					System.out.println("Discarded second pope token");

				break;

			case 24:

				if (localPlayer.getPopeTokens()[2].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[2].getPoints() + " victory points!");

				else
					System.out.println("Discarded third pope token");

				break;

			default:
				System.out.println("This should never ever ever happen");
		}
	}

	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{
		if (!playerWhoDiscarded.equals(cli.getUsername()))
			System.out.println(playerWhoDiscarded + " discarded " + discardedResNum + " resources, so you gained " + discardedResNum + " faith points!");

		else
			System.out.println(discardedResNum + " resources couldn't fit in the storage, so they have been discarded");
	}

	public void singlePlayerGameOver(String message)		/* When the player has lost, print the game over message and close the client */
	{
		System.out.println(message);

		System.out.print("Do you want to play again? (Y/N) ");			/* TODO: test this */

		if (input.nextLine().equalsIgnoreCase("Y"))
		{
			networkHandler.stop();
			new CLI(cli.getInput());
		}

		else
			networkHandler.shutdown();
	}

	public void matchOver(String winner, List<Player> players)
	{
		if (cli.getUsername().equals(winner))
			System.out.print("You win! ");

		else
			System.out.print("Game over! The winner is " + winner);

		for (int i = 0; i < players.size(); i++)
			System.out.println(players.get(i).getUsername() + ": " + players.get(i).getVictoryPoints() + " points");

		System.out.print("Do you want to play again? (Y/N) ");

		if (input.nextLine().equalsIgnoreCase("Y"))
		{
			networkHandler.stop();
			new CLI(cli.getInput());
		}

		else
			networkHandler.shutdown();
	}
}
