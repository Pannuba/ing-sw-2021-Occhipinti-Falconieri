package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;

import java.util.List;

public class PrintMethods			/* Static methods so we can avoid "PrintMethods printMethods = new PrintMethods()" in CLI.java */
{

	public static void printBoard(Track track, Dashboard board)		/* Could get both from gamestate but this is simpler */
	{
		printTrack(track);
		printVault(board.getVault());
		printStorage(board.getStorage());
		printDevCardAreas(board.getDevCardAreas());
	}

	public static void printLeaderCard(Object card)		/* Can't access skill variables from LeaderCard because it's abstract */
	{
		switch(card.getClass().getSimpleName())
		{
			case "SkillDiscount":
				System.out.println("Leadercard skill: discount");
				System.out.println("Discounted resource: " + ((SkillDiscount)card).getDiscountedResource());
				break;

			case "SkillMarble":
				System.out.println("Leadercard skill: white marble");
				System.out.println("White marble resource: " + ((SkillMarble)card).getWhiteMarble());
				break;

			case "SkillProduction":
				System.out.println("Leadercard skill: additional production");
				System.out.println("Requirement type: " + ((SkillProduction)card).getRequirement().getResourceType());
				System.out.println("Requirement amount: 1");
				System.out.println("Product type: " + ((SkillProduction)card).getProduct().getResourceType());		/* Null because it's chosen by the player */
				System.out.println("Product amount: 1");
				break;

			case "SkillStorage":
				System.out.println("Leadercard skill: additional storage");
				System.out.println("Additional storage resource: " + ((SkillStorage)card).getAdditionalStorage().getShelfResource().getResourceType());
				break;
		}

		System.out.println("Card number: " + ((LeaderCard)card).getCardNumber());
		System.out.println("Points: " + ((LeaderCard)card).getPoints());
		System.out.println("Discarded: " + ((LeaderCard)card).isDiscarded());
	}

	public static void printPlayerLeaderCards(List<LeaderCard> leaderCards)
	{
		System.out.println("Player leader cards:");

		for (int i = 0; i < leaderCards.size(); i++)
		{
			printLeaderCard(leaderCards.get(i));
		}
	}

	public static void printPlayerDevCards(List<DevCard> devCards)
	{
		System.out.println("Player dev cards:\n\n");

		for (int i = 0; i < devCards.size(); i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			printDevCard(devCards.get(i));
		}
	}

	public static void printDevCardsMarket(DevCardsMarket market)		// market size??
	{
		System.out.println("Dev cards market:\n\n");

		for (int i = 0; i < market.getCardsInMarket(); i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			printDevCard(market.getDevCards().get(i));
		}
	}

	private static void printDevCard(DevCard devCard)
	{
		System.out.println("Points: " + devCard.getPoints());
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}

	public static void printMarblesMarket(MarblesMarket market)		/* private? */
	{
		Marble[][] board = market.getMarblesBoard();

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				System.out.print(convertMarbleToString(board[i][j]) + "  ");
			}
			System.out.print(Color.RESET + "\n");
		}
	}

	private static void printTrack(Track track)
	{
		System.out.println("[ | | | | | | | | | | | | | | | | | | | | | | | | | ]");
	}

	private static void printVault(Vault vault)			/* Use ANSI color codes */
	{
		System.out.print(	"Your vault currently has:\n\n" + vault.getResourceAmounts()[0] + " blue\n" + vault.getResourceAmounts()[1] + " grey\n"
							+ vault.getResourceAmounts()[2] + " yellow\n" + vault.getResourceAmounts()[3] + "purple\n\n"
							+ "For a total of " + vault.getTotalResources() + " resources");

	}

/*	Y
		_______
		 O   X			shelves[1]
		_______
		Z  Z  Z			shelves[2]
		_______
*/

	private static void printStorage(Storage storage)		/* Finish other shelves */
	{
		String topShelf = "", middleShelf = "", bottomShelf = "";

		if (storage.getShelves()[0].getShelfResource().getQuantity() == 0)
			topShelf = "       ";

		else if (storage.getShelves()[0].getShelfResource().getQuantity() == 1)
			topShelf = "   " + convertResourceToString(storage.getShelves()[0].getShelfResource()) + "   ";

		System.out.print(	"Storage:\n\n"	+
							topShelf + "\n"	+
							"_______\n"		+
							" Y   Y \n"		+
							"_______\n"		+
							"Y  Y  Y\n"		+
							"_______\n"		);

	}

	private static void printDevCardAreas(DevCardArea[] devCardAreas)
	{

	}

	private static String convertMarbleToString(Marble marble)
	{
		final String marbleSymbol = "\u2B24";

		switch (marble.getMarbleType())
		{
			case YELLOW:
				return Color.YELLOW + marbleSymbol + Color.RESET;

			case PURPLE:
				return Color.PURPLE + marbleSymbol + Color.RESET;

			case WHITE:
				return Color.WHITE + marbleSymbol + Color.RESET;

			case GREY:
				return Color.GREY + marbleSymbol + Color.RESET;

			case BLUE:
				return Color.BLUE + marbleSymbol + Color.RESET;

			case RED:
				return Color.RED + marbleSymbol + Color.RESET;
		}

		return null;
	}

	private static String convertResourceToString(Resource resource)
	{
		final String resourceSymbol = "\u2B1B";

		switch(resource.getResourceType())
		{
			case YELLOW:
				return Color.YELLOW + resourceSymbol + Color.RESET;

			case PURPLE:
				return Color.PURPLE + resourceSymbol + Color.RESET;

			case GREY:
				return Color.GREY + resourceSymbol + Color.RESET;

			case BLUE:
				return Color.BLUE + resourceSymbol + Color.RESET;
		}
		
		return null;
	}
}
