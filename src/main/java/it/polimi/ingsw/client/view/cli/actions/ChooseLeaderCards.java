package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.cli.PrintMethods;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;
import java.util.Scanner;

public class ChooseLeaderCards
{
	public ChooseLeaderCards(List<LeaderCard> fourLeaderCards, Scanner input, List<String> command, MessageIO messageHandler)
	{
		for (int i = 0; i < 4; i++)
			PrintMethods.printLeaderCard(fourLeaderCards.get(i));

		System.out.print(	"Choose leader card " + fourLeaderCards.get(0).getCardNumber() + ", " + fourLeaderCards.get(1).getCardNumber() +
				", " + fourLeaderCards.get(2).getCardNumber() + ", " + fourLeaderCards.get(3).getCardNumber() + ": ");

		String cardChoice1 = input.nextLine();

		while (!cardChoice1.equals(String.valueOf(fourLeaderCards.get(0).getCardNumber())) && !cardChoice1.equals(String.valueOf(fourLeaderCards.get(1).getCardNumber())) && !cardChoice1.equals(String.valueOf(fourLeaderCards.get(2).getCardNumber())) && !cardChoice1.equals(String.valueOf(fourLeaderCards.get(3).getCardNumber())))
		{
			System.out.println("You entered the incorrect leaderCard");
			System.out.print("Choose the first leader card: ");
			cardChoice1 = input.nextLine();
		}

		System.out.print("Choose the second leader card: ");
		String cardChoice2 = input.nextLine();

		while (!cardChoice2.equals(String.valueOf(fourLeaderCards.get(0).getCardNumber())) && !cardChoice2.equals(String.valueOf(fourLeaderCards.get(1).getCardNumber())) && !cardChoice2.equals(String.valueOf(fourLeaderCards.get(2).getCardNumber())) && !cardChoice2.equals(String.valueOf(fourLeaderCards.get(3).getCardNumber())) || cardChoice1.equals(cardChoice2))
		{
			System.out.println("You entered the incorrect leaderCard");
			System.out.print("Choose the second leader card: ");
			cardChoice2 = input.nextLine();
		}

		command.add("SELECT_LEADERCARDS");
		command.add(cardChoice1);
		command.add(cardChoice2);

		System.out.println("Notifying observers (network handler)");
		messageHandler.send(command);
		command.clear();		/* So the same command List<String> instance can be used in all functions */
	}
}
