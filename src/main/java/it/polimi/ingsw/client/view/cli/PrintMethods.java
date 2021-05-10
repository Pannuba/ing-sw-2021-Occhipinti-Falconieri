package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.DevCardsMarket;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;

import java.util.List;

public class PrintMethods			/* Static methods so we can avoid "PrintMethods printMethods = new PrintMethods()" in CLI.java */
{

	public static void printBoard(Track track, Dashboard board)		/* Could get both from gamestate but this is more simple */
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
		for (int i = 0; i < leaderCards.size(); i++)
		{
			printLeaderCard(leaderCards.get(i));
		}
	}

	public static void printPlayerDevCards()
	{

	}

	public static void printDevCardsMarket(DevCardsMarket market)
	{

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

	private static void printVault(Vault vault)
	{

	}

	private static void printStorage(Storage storage)
	{

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
}
