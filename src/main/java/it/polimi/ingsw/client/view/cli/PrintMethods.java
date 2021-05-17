package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;

import java.util.List;

public class PrintMethods			/* Static methods so we can avoid "PrintMethods printMethods = new PrintMethods()" in CLI.java */
{

	public static void printBoard(Dashboard board)		/* Could get both from gamestate but this is simpler */
	{
		printVault(board.getVault());
		printStorage(board.getStorage());
		printDevCardAreas(board.getDevCardAreas());
	}

	public static void printLeaderCard(Object card)		/* Can't access skill variables from LeaderCard because it's abstract */
	{
		System.out.println("Card number: " + ((LeaderCard)card).getCardNumber());
		System.out.println("Points: " + ((LeaderCard)card).getPoints());
		System.out.print("Discarded: " + ((LeaderCard)card).isDiscarded());

		switch(card.getClass().getSimpleName())
		{
			case "SkillDiscount":
				System.out.println("Leadercard skill: discount");
				System.out.println("Discounted resource: " + convertResourceTypeToString(((SkillDiscount)card).getDiscountedResource()));
				break;

			case "SkillMarble":
				System.out.println("Leadercard skill: white marble");
				System.out.println("White marble resource: " + convertResourceTypeToString(((SkillMarble)card).getWhiteMarble()));
				break;

			case "SkillProduction":
				System.out.println("Leadercard skill: additional production");
				System.out.println("Cost type: " + convertResourceTypeToString(((SkillProduction)card).getCost().getResourceType()));
				System.out.println("Cost amount: 1");
				/* Product is null because it's chosen by the player */
				System.out.println("Product amount: 1");
				break;

			case "SkillStorage":
				System.out.println("Leadercard skill: additional storage");
				System.out.println("Additional storage resource: " + convertResourceTypeToString(((SkillStorage)card).getAdditionalStorage().getShelfResource().getResourceType()));
				break;
		}

		System.out.print("\n\n");
	}

	public static void printPlayerLeaderCards(List<LeaderCard> leaderCards)
	{
		System.out.print("Player leader cards:\n\n");

		for (int i = 0; i < leaderCards.size(); i++)
		{
			printLeaderCard(leaderCards.get(i));
		}

		System.out.print("\n");
	}

	public static void printPlayerDevCards(List<DevCard> devCards)
	{
		System.out.print("Player dev cards:\n\n");

		if (devCards.size() == 0)
		{
			System.out.println("Nothing here!");
		}

		else
		{
			for (int i = 0; i < devCards.size(); i++)
			{
				System.out.println("Card " + (i + 1) + ":");
				printDevCard(devCards.get(i));
			}
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

		System.out.print("\n\n");
	}

	public static void printDevCardsMarketLevel(DevCardsMarket market, int level)
	{
		System.out.println("Dev cards in market with level = " + level + ":\n\n");

		for (int i = 0; i < market.getCardsInMarket(); i++)
		{
			if (market.getDevCards().get(i).getLevel() == level)
			{
				System.out.println("Card " + (i + 1) + ":");
				printDevCard(market.getDevCards().get(i));
			}
		}

		System.out.print("\n\n");
	}

	private static void printDevCard(DevCard devCard)		/* Cost and requirements are actually the opposite! */
	{
		System.out.print(	"Card number: " + devCard.getCardNumber() +
							"\nColor: " + devCard.getColor() +
							"\nLevel: " + devCard.getLevel() +
							"\nVictory points: " + devCard.getPoints() +
							"\nFaith points: " + devCard.getFaithPoints() +
							"\nProduct: " + devCard.getProduct() +
							"\nCost: " + devCard.getCost() +
							"\nRequirements: " + devCard.getRequirements() + "\n\n"	);
	}

	public static void printMarblesMarket(MarblesMarket market)		/* private? */
	{
		System.out.print("Marbles market:\n\n");
		Marble[][] board = market.getMarblesBoard();

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				System.out.print(convertMarbleToString(board[i][j]) + "  ");
			}
			System.out.print(ANSI.RESET + "\n");
		}
	}

	public static void printTrack(Track track, List<Player> players)		/* Players are ordered by ID in model after randomly generating IDs */
	{
		System.out.print("Track:\n\n");

		for (int i = 0; i < track.getRedPawns().size(); i++)		/* Size of redPawns = numPlayers, to avoid passing gamestate */
		{
			System.out.println(players.get(i).getUsername() + " is at position " + track.getRedPawns().get(i));
		}
	}

	private static void printVault(Vault vault)			/* Use ANSI color codes */
	{
		System.out.print(	"Your vault currently has:\n\n" + vault.getResourceAmounts()[0] + " blue\n" + vault.getResourceAmounts()[1] + " grey\n"
							+ vault.getResourceAmounts()[2] + " yellow\n" + vault.getResourceAmounts()[3] + " purple\n\n"
							+ "For a total of " + vault.getTotalResources() + " resources\n\n");

	}

	private static void printStorage(Storage storage)
	{
		String topShelf = "", middleShelf = "", bottomShelf = "";
		String topShelfResource = convertResourceTypeToString(storage.getShelves()[0].getShelfResource().getResourceType());
		String middleShelfResource = convertResourceTypeToString(storage.getShelves()[1].getShelfResource().getResourceType());
		String bottomShelfResource = convertResourceTypeToString(storage.getShelves()[2].getShelfResource().getResourceType());

		switch (storage.getShelves()[0].getShelfResource().getQuantity())
		{
			case 0:
				topShelf = "   " + ANSI.EMPTY + "   ";
				break;

			case 1:
				topShelf = "   " + topShelfResource + "   ";
				break;
		}

		switch (storage.getShelves()[1].getShelfResource().getQuantity())
		{
			case 0:
				middleShelf = " " + ANSI.EMPTY + "   " + ANSI.EMPTY + " ";
				break;

			case 1:
				middleShelf = " " + middleShelfResource + "   " + ANSI.EMPTY + " ";
				break;

			case 2:
				middleShelf = " " + middleShelfResource + "   " + middleShelfResource + " ";
				break;
		}

		switch (storage.getShelves()[2].getShelfResource().getQuantity())
		{
			case 0:
				bottomShelf = ANSI.EMPTY + "  " + ANSI.EMPTY + "  " + ANSI.EMPTY;
				break;

			case 1:
				bottomShelf = bottomShelfResource + "  " + ANSI.EMPTY + "  " + ANSI.EMPTY;
				break;

			case 2:
				bottomShelf = bottomShelfResource + "  " + topShelfResource + "  " + ANSI.EMPTY;
				break;

			case 3:
				bottomShelf = bottomShelfResource + "  " + topShelfResource + "  " + topShelfResource;
				break;
		}

		System.out.print(	"Storage:" + "\n\n"	+
							topShelf + "\n"		+
							"_______" + "\n"	+
							middleShelf + "\n"	+
							"_______" + "\n"	+
							bottomShelf + "\n"	+
							"_______" + "\n"	);

	}

	public static void printDevCardAreas(DevCardArea[] devCardAreas)
	{
		if (devCardAreas[0].isEmpty() && devCardAreas[1].isEmpty() && devCardAreas[2].isEmpty())
		{
			System.out.println("You have no dev cards!");
			return;
		}

		else
		{
			for (int i = 0; i < 3; i++)		/* There are always 3 devcardareas, no more, no less */
			{
				System.out.println("Dev card area " + (i + 1) + ":");


				if (devCardAreas[i].isEmpty() == false)
				{
					System.out.print("Layer: " + devCardAreas[i].getLayer() + ",\n");
					printDevCard(devCardAreas[i].getTopDevCard());
				}

				else
					System.out.println("Empty!");
			}
		}
	}

	private static String convertMarbleToString(Marble marble)
	{
		switch (marble.getMarbleType())
		{
			case YELLOW:

				return ANSI.YELLOW + ANSI.MARBLE.toString() + ANSI.RESET;

			case PURPLE:
				return ANSI.PURPLE + ANSI.MARBLE.toString() + ANSI.RESET;

			case WHITE:
				return ANSI.WHITE + ANSI.MARBLE.toString() + ANSI.RESET;

			case GREY:
				return ANSI.GREY + ANSI.MARBLE.toString() + ANSI.RESET;

			case BLUE:
				return ANSI.BLUE + ANSI.MARBLE.toString() + ANSI.RESET;

			case RED:
				return ANSI.RED + ANSI.MARBLE.toString() + ANSI.RESET;
		}

		return null;
	}

	private static String convertResourceTypeToString(ResourceType resourceType)
	{
		if (resourceType == null)
			return ANSI.EMPTY.toString();

		switch (resourceType)
		{
			case YELLOW:
				return ANSI.YELLOW + ANSI.RESOURCE.toString() + ANSI.RESET;

			case PURPLE:
				return ANSI.PURPLE + ANSI.RESOURCE.toString() + ANSI.RESET;

			case GREY:
				return ANSI.GREY + ANSI.RESOURCE.toString() + ANSI.RESET;

			case BLUE:
				return ANSI.BLUE + ANSI.RESOURCE.toString() + ANSI.RESET;
		}

		return null;
	}
}
