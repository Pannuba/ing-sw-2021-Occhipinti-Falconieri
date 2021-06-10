package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ActivateProduction
{
	private final Scanner input;
	private final CLI cli;

	public ActivateProduction(Scanner input, CLI cli)
	{
		this.input = input;
		this.cli = cli;
	}

	public List<String> run()
	{
		List<String> command;
		System.out.println("This is your current storage and vault:");
		PrintMethods.printStorage(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getStorage());
		PrintMethods.printVault(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getVault());
		System.out.print("Do you want to use the default production (1), a devcard (2) or a SkillProduction leader card (3)? (Q = choose another action) ");

		switch (input.nextLine())
		{
			case "1":
				command = defaultProduction();
				break;

			case "2":
				command = devCardProduction();
				break;

			case "3":	/* Checks are both server side (to prevent cheating) and client side (to avoid useless server load and message exchange) */
				command = leaderProduction();
				break;

			default:
				cli.chooseAction();
				return null;
		}

		return command;
	}

	public List<String> defaultProduction()
	{
		String firstResourceToConvert, secondResourceToConvert, resourceToMake;

		System.out.print("Insert the first resource type you want to convert (B/G/Y/P): ");
		firstResourceToConvert = input.nextLine();
		System.out.print("Insert the second resource type you want to convert (B/G/Y/P): ");
		secondResourceToConvert = input.nextLine();
		System.out.print("Insert the resource type you want to make (B/G/Y/P): ");
		resourceToMake = input.nextLine();

		return Arrays.asList("ACTIVATE_PRODUCTION", "DEFAULT", firstResourceToConvert, secondResourceToConvert, resourceToMake);
	}

	public List<String> devCardProduction()
	{
		String cardNumber;
		PrintMethods.printDevCardAreas(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getDevCardAreas());
		/* TODO: check if the player has no devcards */
		System.out.print("Insert the devcard's # you want to use: ");
		cardNumber = input.nextLine();

		return Arrays.asList("ACTIVATE_PRODUCTION", "DEVCARD", cardNumber);
	}

	public List<String> leaderProduction()
	{
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
			cli.chooseAction();
			return null;
		}

		else
		{
			for (int i = 0; i < activeCardsWithProdSkill.size(); i++)			/* For the rare chance that the player has both cards SkillProduction */
				PrintMethods.printLeaderCard(activeCardsWithProdSkill.get(i));

			System.out.print("Which card do you want to use? ");
			chosenCardNum = input.nextLine();

			System.out.print("Insert the resource you want to make (B/G/Y/P)");
			resourceToMakeLeader = input.nextLine();

			return Arrays.asList("ACTIVATE_PRODUCTION", "LEADER_SKILL", chosenCardNum, resourceToMakeLeader);
		}
	}
}
