package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaderChoice
{
	public LeaderChoice(Scanner input, MessageIO messageHandler, CLI cli)
	{
		List<String> command = new ArrayList<>();
		List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
		List<LeaderCard> canBeActivated = new ArrayList<>();
		List<LeaderCard> canBeDiscarded = new ArrayList<>();
		String chosenCard;

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (!leaderCards.get(i).isActive())
				canBeActivated.add(leaderCards.get(i));

			if (!leaderCards.get(i).isDiscarded())
				canBeDiscarded.add(leaderCards.get(i));
		}

		if (canBeActivated.isEmpty() && canBeDiscarded.isEmpty())
			return;			/* If the player has no leaders that can be activated or discarded, skip the choice */

		System.out.print("Do you want to activate or discard a leader? A/D: ");

		switch (input.nextLine().toUpperCase())
		{
			case "A":		/* Only pass cards that can be activated */

				if (canBeActivated.isEmpty())
				{
					System.out.println("You don't have any leaders that can be activated!");
					return;
				}

				PrintMethods.printPlayerLeaderCards(canBeActivated);
				System.out.print("Select card #" + canBeActivated.get(0).getCardNumber() + " or #" + canBeActivated.get(1).getCardNumber() + ": ");
				chosenCard = input.nextLine();

				command.add("ACTIVATE_LEADER");
				command.add(chosenCard);
				messageHandler.send(command);		/* Server checks resources and replies with another message */
				command.clear();
				break;

			case "D":		/* Only pass cards that can be discarded */

				if (canBeDiscarded.isEmpty())
				{
					System.out.println("You don't have any leaders that can be discarded!");
					return;
				}

				PrintMethods.printPlayerLeaderCards(canBeDiscarded);
				System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
				chosenCard = input.nextLine();

				command.add("DISCARD_LEADER");
				command.add(chosenCard);
				messageHandler.send(command);
				command.clear();
				break;

			default:
				return;
		}
		/* - 1 because the player has just performed a successful action */
		if ((canBeActivated.size() - 1) > 0 || (canBeDiscarded.size() - 1) > 0)		/* If there are still card to activate or discard, ask again */
		{
			System.out.print("Activate or discard another leader? (Y/N) ");

			if (input.nextLine().equalsIgnoreCase("Y"))
				new LeaderChoice(input, messageHandler, cli);
		}
	}
}
