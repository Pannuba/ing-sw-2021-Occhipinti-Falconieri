package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;
import java.util.Scanner;

public class ChooseLeaderCards
{
	public ChooseLeaderCards(List<LeaderCard> fourLeaderCards, Scanner input, List<String> command, NetworkHandler networkHandler)
	{
		for (int i = 0; i < 4; i++)
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));

		System.out.print(	"Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
							", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ": ");

		String cardChoice1 = input.nextLine();

		System.out.print("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();

		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);

		System.out.println("Notifying observers (network handler)");
		networkHandler.send(command);
		command.clear();		/* So the same command List<String> instance can be used in all functions */
	}
}
