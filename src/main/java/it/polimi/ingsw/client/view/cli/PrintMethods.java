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
		printStorage(board.getStorage());			/* TODO: add SkillStorage */
		printDevCardAreas(board.getDevCardAreas());
	}

	public static void printLeaderCard(Object card)		/* Can't access skill variables from LeaderCard because it's abstract */
	{
		System.out.println("Card number: " + ((LeaderCard) card).getCardNumber());
		System.out.println("Points: " + ((LeaderCard) card).getPoints());

		//if (((LeaderCard) card).isActive())			/* Active cards can't be discarded, and vice versa. Will remove this check for debugging */
			System.out.println("Active: " + ((LeaderCard) card).isActive());

		//else
			System.out.println("Discarded: " + ((LeaderCard) card).isDiscarded());

		switch (card.getClass().getSimpleName())
		{
			case "SkillDiscount":
				System.out.println(	"Leadercard skill: discount"																				+
									"\nDiscounted resource: " + convertResTypeToString(((SkillDiscount) card).getDiscountedResource())			+
									"\nRequirements: " + convertDevColorListToString(((SkillDiscount) card).getRequirements()) + " dev cards"	);
				break;
												/* TODO: separate symbol for devCards/devCardColor? */
			case "SkillMarble":
				System.out.println(	"Leadercard skill: white marble"																		+
									"\nWhite marble resource: " + convertResTypeToString(((SkillMarble) card).getWhiteMarble())				+
									"\nRequirements: " + convertDevColorListToString(((SkillMarble) card).getRequirements()) + " dev cards"	);
				break;

			case "SkillProduction":
				System.out.println(	"Leadercard skill: additional production"																	+
									"\nCost: 1 " + convertResTypeToString(((SkillProduction) card).getCost().getResourceType())					+
									"\nProduct amount: 1"																						+
									"\nRequirements: a " + convertDevCardColorToString(((SkillProduction) card).getRequirements().getObj1())	+
									" lvl " + ((SkillProduction) card).getRequirements().getObj2() + " dev card"								);
				break;

			case "SkillStorage":
				System.out.println(	"Leadercard skill: additional storage"																										+
									"\nAdditional storage resource: " + convertResTypeToString(((SkillStorage) card).getAdditionalStorage().getShelfResource().getResourceType())	+
									"\nRequirements: " + ((SkillStorage) card).getRequirements().getQuantity() + " "																+
									convertResTypeToString(((SkillStorage) card).getRequirements().getResourceType())																);
				break;
		}

		System.out.print("\n");
	}

	public static void printPlayerLeaderCards(List<LeaderCard> leaderCards)
	{
		System.out.print("Player leader cards:\n\n");

		for (int i = 0; i < leaderCards.size(); i++)
			printLeaderCard(leaderCards.get(i));
	}

	public static void printPlayerDevCards(List<DevCard> devCards)		/* Used to print ALL devcards got with getAllDevCards in Dashboard */
	{
		System.out.print("These are your dev cards:\n\n");

		if (devCards.size() == 0)
			System.out.println("Nothing here!");

		else
			for (int i = 0; i < devCards.size(); i++)
				printDevCard(devCards.get(i));
	}

	public static void printDevCardsMarket(DevCardsMarket market)		// market size??
	{
		System.out.println("Dev cards market:\n\n");

		for (int i = 0; i < market.getDevCardStacks().size(); i++)
		{
			if (market.getDevCardStacks().get(i).isEmpty())
				System.out.println("Dev card stack #" + (i + 1) + " is empty!");

			else
			{
				System.out.println("Dev card stack #" + (i + 1) + " has " + market.getDevCardStacks().get(i).size() + " cards");
				printDevCard(market.getDevCardStacks().get(i).get(0));
			}
		}

	}

	public static void printDevCard(DevCard devCard)		/* Cost and requirements are actually the opposite! */
	{
		System.out.print(	"Card number: " + devCard.getCardNumber()										+
							"\nColor: " + convertDevCardColorToString(devCard.getColor())					+
							"\nLevel: " + devCard.getLevel()												+
							"\nVictory points: " + devCard.getPoints()										+
							"\nFaith points: " + devCard.getFaithPoints()									+
							"\nProduct: " + convertResListToString(devCard.getProduct())					+
							"\nCost: " + convertResListToString(devCard.getCost())							+
							"\nRequirements: " + convertResListToString(devCard.getRequirements()) + "\n\n"	);
	}

	public static void printMarblesMarket(MarblesMarket market)		/* private? */
	{
		System.out.print("Marbles market:\n\n");
		Marble[][] board = market.getMarblesBoard();

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
				System.out.print(convertMarbleToString(board[i][j]) + "  ");

			System.out.print(ANSI.RESET + "\n");
		}

		System.out.println("Spare marble: " + convertMarbleToString(market.getSpareMarble()));
	}

	public static void printTrack(Track track, List<Player> players)		/* Players are ordered by ID in model after randomly generating IDs */
	{
		System.out.print("Track:\n");

		for (int i = 0; i < track.getRedPawns().size(); i++)		/* Size of redPawns = numPlayers = size of players list */
			System.out.println(players.get(i).getUsername() + " is at position " + track.getRedPawns().get(i) + "/24");

		if (players.size() == 1)
			System.out.println("Lorenzo the Magnificent is at position " + track.getBlackPawn() + "/24");

		System.out.print("\n");
	}

	public static void printVault(Vault vault)
	{
		System.out.print("Your vault currently has:\n"																						+
						 vault.getResourceAmounts().get(ResourceType.BLUE)	 + " " + convertResTypeToString(ResourceType.BLUE)	 + ", "		+
						 vault.getResourceAmounts().get(ResourceType.GREY)	 + " " + convertResTypeToString(ResourceType.GREY)	 + ", "		+
						 vault.getResourceAmounts().get(ResourceType.YELLOW) + " " + convertResTypeToString(ResourceType.YELLOW) + ", and "	+
						 vault.getResourceAmounts().get(ResourceType.PURPLE) + " " + convertResTypeToString(ResourceType.PURPLE) + " " 		+
						 "for a total of " + vault.getTotalResources() + " resources\n\n" );
	}

	public static void printStorage(Storage storage)
	{
		String topShelf = "", middleShelf = "", bottomShelf = "";

		String topShelfResource    = convertResTypeToString(storage.getShelves()[0].getShelfResourceType());
		String middleShelfResource = convertResTypeToString(storage.getShelves()[1].getShelfResourceType());
		String bottomShelfResource = convertResTypeToString(storage.getShelves()[2].getShelfResourceType());

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
				bottomShelf = bottomShelfResource + "  " + bottomShelfResource + "  " + ANSI.EMPTY;
				break;

			case 3:
				bottomShelf = bottomShelfResource + "  " + bottomShelfResource + "  " + bottomShelfResource;
				break;
		}

		System.out.print(	"Storage:"	+ "\n\n"	+
							topShelf	+ "\n"		+
							"_______"	+ "\n"		+
							middleShelf	+ "\n"		+
							"_______"	+ "\n"		+
							bottomShelf	+ "\n"		+
							"_______"	+ "\n\n"	);
	}

	public static void printDevCardAreas(DevCardArea[] devCardAreas)		/* Used to print usable (aka top) devcards for production */
	{
		if (devCardAreas[0].isEmpty() && devCardAreas[1].isEmpty() && devCardAreas[2].isEmpty())
			System.out.print("You have no dev cards!\n\n");

		else
		{
			for (int i = 0; i < devCardAreas.length; i++)
			{
				System.out.println("Dev card area " + (i + 1) + ":");

				if (!devCardAreas[i].isEmpty())
				{
					System.out.print("Layer: " + devCardAreas[i].getLayer() + ",\n");
					printDevCard(devCardAreas[i].getTopDevCard());
				}

				else
					System.out.print("Empty!\n\n");
			}
		}
	}

	private static String convertMarbleToString(Marble marble)
	{
		if (marble.getMarbleType() == null)
			return "ERR";

		switch (marble.getMarbleType())
		{
			case YELLOW:
				return ANSI.YELLOW + ANSI.MARBLE.toString() + ANSI.RESET;

			case PURPLE:
				return ANSI.PURPLE + ANSI.MARBLE.toString() + ANSI.RESET;

			case WHITE:
				return ANSI.WHITE  + ANSI.MARBLE.toString() + ANSI.RESET;

			case GREY:
				return ANSI.GREY   + ANSI.MARBLE.toString() + ANSI.RESET;

			case BLUE:
				return ANSI.BLUE   + ANSI.MARBLE.toString() + ANSI.RESET;

			case RED:
				return ANSI.RED    + ANSI.MARBLE.toString() + ANSI.RESET;
		}

		return null;
	}

	public static String convertResTypeToString(ResourceType resourceType)
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
				return ANSI.GREY   + ANSI.RESOURCE.toString() + ANSI.RESET;

			case BLUE:
				return ANSI.BLUE   + ANSI.RESOURCE.toString() + ANSI.RESET;
		}

		return null;
	}

	private static String convertDevCardColorToString(DevCardColor color)
	{
		if (color == null)
			return "ERR";

		switch (color)			/* Use a different symbol? */
		{
			case YELLOW:
				return ANSI.YELLOW + ANSI.RESOURCE.toString() + ANSI.RESET;

			case PURPLE:
				return ANSI.PURPLE + ANSI.RESOURCE.toString() + ANSI.RESET;

			case BLUE:
				return ANSI.BLUE   + ANSI.RESOURCE.toString() + ANSI.RESET;

			case GREEN:
				return ANSI.GREEN  + ANSI.RESOURCE.toString() + ANSI.RESET;
		}

		return null;
	}

	public static String convertDevColorListToString(List<DevCardColor> colors)		/* [BLUE, GREEN, BLUE, YELLOW] -> 2B, 1G, 1Y */
	{
		String strColors = "";

		for (int i = 0; i < colors.size(); i++)
		{
			strColors += convertDevCardColorToString(colors.get(i));

			if (i != colors.size() - 1)
				strColors += ", ";
		}

		return strColors;
	}

	public static String convertResListToString(List<Resource> resources)		/* [BLUE, 2], [YELLOW, 3], [PURPLE, 1] */
	{
		String strResources = "";

		for (int i = 0; i < resources.size(); i++)
		{
			strResources += resources.get(i).getQuantity() + " " + convertResTypeToString(resources.get(i).getResourceType());

			if (i != resources.size() - 1)
				strResources += ", ";
		}

		return strResources;
	}

	public static void printActionToken(ActionToken token)
	{
		switch (token.getClass().getSimpleName())
		{
			case "ActionDevCard":

				System.out.println("discard 2 " + convertDevCardColorToString(((ActionDevCard) token).getColor()) + " dev cards");
				/* Print new devCardsMarket? */
				break;

			case "ActionBlack1":
				System.out.println("move black pawn by 1 place, then shuffle the action tokens");
				/* Print new track? Add track to message? */
				break;

			case "ActionBlack2":
				System.out.println("move black pawn by 2 places");
				break;
		}
	}
}
