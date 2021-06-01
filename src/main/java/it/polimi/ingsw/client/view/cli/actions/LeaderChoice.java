package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaderChoice
{
	public LeaderChoice(Scanner input, List<String> command, NetworkHandler networkHandler, CLI cli)
	{
		List<LeaderCard> leaderCards = cli.getGameState().getPlayerByName(cli.getUsername()).getLeaderCards();
		List<LeaderCard> inactiveLeaders = new ArrayList<>();
		String chosenCard;

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
			case "A":		/* Only pass cards that can be activated. TODO: check if inactiveLeaders is empty */

				PrintMethods.printPlayerLeaderCards(inactiveLeaders);
				System.out.print("Select card #" + inactiveLeaders.get(0).getCardNumber() + " or #" + inactiveLeaders.get(1).getCardNumber() + ": ");
				chosenCard = input.nextLine();

				command.add("ACTIVATE_LEADER");
				command.add(chosenCard);
				networkHandler.send(command);		/* Server checks resources and replies with another message */
				command.clear();
				break;

			case "D":		/* Only pass cards that can be discarded */
				PrintMethods.printPlayerLeaderCards(leaderCards);
				System.out.print("Select card #" + leaderCards.get(0).getCardNumber() + " or #" + leaderCards.get(1).getCardNumber() + ": ");
				chosenCard = input.nextLine();

				command.add("DISCARD_LEADER");		/* TODO: avoid repeating these lines, see other actions */
				command.add(chosenCard);
				networkHandler.send(command);
				command.clear();
				break;
		}
	}
}
