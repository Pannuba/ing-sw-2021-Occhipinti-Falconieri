package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.cli.actions.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*	Idea: make ActionExecutor an abstract class in the client package, with methods like "abstract void firstPlayer(boolean isFirstPlayer)"...
	then make ActionCLI and ActionGUI classes that extend ActionExecutor in the cli and gui package respectively, and put the @Override methods there
	MessageDecoder has an instance variable of ActionExecutor, it should still work.

	Put everything here or only the functions called by server messages? Make MessageCLI and ActionCLI
*/

public class ActionCLI extends MessageExecutor    /* Has methods that perform actions such as buying resources, to avoid cluttering the CLI. Interface? */
{
	private final Scanner input;
	private final CLI cli;
	private final NetworkHandler networkHandler;
	private final List<String> command;

	public ActionCLI(CLI cli)		/* TODO: tidy parameters passed from CLI and to other actions */
	{
		this.cli = cli;
		this.input = cli.getInput();
		this.networkHandler = cli.getNetworkHandler();
		command = new ArrayList<>();
	}

	public void leaderChoice()
	{
		new LeaderChoice(input, command, networkHandler, cli);
	}

	public void buyResources()
	{
		new BuyResources(input, command, networkHandler, cli);
	}

	public void buyDevCard()
	{
		new BuyDevCard(input, command, networkHandler, cli);
	}

	public void activateProduction()		/* How many times can a production be repeated? See rules */
	{
		new ActivateProduction(input, command, networkHandler, cli);
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)
	{
		if (isFirstPlayer)
		{
			System.out.print("Total players: ");
			networkHandler.send(input.nextLine());
		}
	}

	@Override
	public void startMatch()		/* Simple actions like this one don't need their own class */
	{
		System.out.println("Starting match\n\nMasters of the Renaissance!");
	}

	@Override
	public void chooseLeaderCards(List<LeaderCard> fourLeaderCards)
	{
		new ChooseLeaderCards(fourLeaderCards, input, command, networkHandler);
	}

	@Override
	public void chooseResources(int playerID)			/* 1st player: nothing; 2nd: 1 resource; 3rd: 1 resource + 1 faithPoint; 4th: 2 resources + 1 faithPoint */
	{
		new ChooseResources(playerID, input, command, networkHandler);
	}

	@Override
	public void getBoughtResources(List<Resource> boughtResources)
	{
		System.out.print("Received the following resources: " + PrintMethods.convertResListToString(boughtResources) + "\n");
	}

	@Override
	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{
		if (!playerWhoDiscarded.equals(cli.getUsername()))
			System.out.println(playerWhoDiscarded + " discarded " + discardedResNum + " resources, so you gained " + discardedResNum + " faith points!");

		else
			System.out.println(discardedResNum + " resources couldn't fit in the storage, so they have been discarded");
	}

	@Override
	public void getBoughtDevCard(DevCard boughtCard)
	{
		System.out.print("Received the following devcard:\n");
		PrintMethods.printDevCard(boughtCard);
	}

	@Override
	public void getOperationResultMessage(String message, boolean isFailed)
	{
		System.out.println(message);

		if (isFailed)
			cli.chooseAction();		/* Client can repeat the round for failed actions */
	}

	@Override
	public void getActionToken(ActionToken token)
	{
		System.out.print("Received action token: ");
		PrintMethods.printActionToken(token);
	}

	@Override
	public void vaticanReport(int popeBoxNum, List<Player> players)
	{
		new VaticanReport(popeBoxNum, players, cli);
	}

	@Override
	public void singlePlayerGameOver(String message)		/* When the player has lost, print the game over message and close the client */
	{
		System.out.println(message);

		System.out.print("Do you want to play again? (Y/N) ");			/* TODO: not working if Y */

		if (input.nextLine().equalsIgnoreCase("Y"))
		{
			networkHandler.stop();
			new CLI(new Scanner(System.in));
		}

		else
			networkHandler.shutdown();
	}

	@Override
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