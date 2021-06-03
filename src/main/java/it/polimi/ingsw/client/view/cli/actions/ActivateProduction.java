package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActivateProduction
{
	public ActivateProduction(Scanner input, List<String> command, NetworkHandler networkHandler, CLI cli)
	{
		System.out.println("This is your current storage and vault:");
		PrintMethods.printStorage(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getStorage());
		PrintMethods.printVault(cli.getGameState().getPlayerByName(cli.getUsername()).getDashboard().getVault());
		System.out.print("Do you want to use the default production (1), a devcard (2) or a SkillProduction leader card (3)? (Q = choose another action) ");

		switch (input.nextLine())
		{
			case "1":

				String firstResourceToConvert, secondResourceToConvert, resourceToMake;

				System.out.print("Insert the first resource type you want to convert (B/G/Y/P): ");
				firstResourceToConvert = input.nextLine();
				System.out.print("Insert the second resource type you want to convert (B/G/Y/P): ");
				secondResourceToConvert = input.nextLine();
				System.out.print("Insert the resource type you want to make (B/G/Y/P): ");
				resourceToMake = input.nextLine();

				command.add("ACTIVATE_PRODUCTION");
				command.add("DEFAULT");
				command.add(firstResourceToConvert);
				command.add(secondResourceToConvert);
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
					cli.chooseAction();
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
				cli.chooseAction();
				return;
		}

		networkHandler.send(command);
		command.clear();
	}
}
