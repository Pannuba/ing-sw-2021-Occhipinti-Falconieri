package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.controller.Controller;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.cards.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;

public class CLI extends Observable implements Observer
{
	private final Scanner input;
	private NetworkHandler networkHandler;
	private final Controller controller;
	private GameState gameState;

	public CLI()
	{
		controller = new Controller();
		input = new Scanner(System.in);

		System.out.print("Username: ");
		String username = input.nextLine();
		System.out.print("Server IP: ");
		String ip = input.nextLine();
		System.out.print("Server port: ");
		int port = Integer.parseInt(input.nextLine());

		networkHandler = new NetworkHandler(this, username, ip, port);
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get the new gamestate */
		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.send(username);

		if (networkHandler.isFirstPlayer())
		{
			System.out.print("Total players: ");
			networkHandler.send(input.nextLine());
		}

		System.out.println("Created network handler");
		networkHandler.waitForPlayers();
		System.out.println("Starting match");
		gameSetup();
		//new Thread(networkHandler).start();		/* Start listening for gamestate updates from server. Put this after game setup? Yes. */
	}

	public void gameSetup()
	{
		System.out.println("Masters of the Renaissance!");

		List<LeaderCard> fourLeaderCards = networkHandler.getFourLeadercards();		/* Server tells clients info about the four leadercards */
		System.out.println("Choose leader card 1, 2, 3 or 4: ");

		for (int i = 0; i < 4; i++)
		{
			System.out.println("Card " + (i + 1) + ":");
			printPlayerLeaderCard(fourLeaderCards.get(i));
		}

		networkHandler.send(input.nextLine());
		System.out.println("Choose the second leader card: ");
		networkHandler.send(input.nextLine());

	}
	

	public void printBoard()
	{
		System.out.println("[ | | | | | | | | | | | | | | | | | | | | | | | | | ]");		/* wow */
	}

	public void printPlayerLeaderCard(Object card)		/* Can't access skill variables from LeaderCard because it's abstract */
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

	public void printPlayerDevCards()
	{

	}

	public void printDevCardsMarket()
	{

	}

	public void printMarblesMarket(MarblesMarket market)		/* private? */
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

	public String convertMarbleToString(Marble marble)
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

	@Override
	public void update(Observable observable, Object o)
	{
		this.gameState = (GameState)o;		/* Gamestate is needed in game loop, not during setup */
	}
}
