package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.controller.Controller;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;

public class CLI implements Observer
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

		NetworkHandler networkHandler = new NetworkHandler(username, ip, port);
		networkHandler.addObserver(this);		/* CLI observes the networkHandler to get the new gamestate */

		networkHandler.connect();

		System.out.println("Sending username to server...");
		networkHandler.send(username);

		if (networkHandler.isFirstPlayer())
		{
			System.out.print("Total players: ");
			networkHandler.send(input.nextLine());
		}

		new Thread(networkHandler).start();		/* Start listening for gamestate updates from server */
		//networkHandler.send("ping");
	}

	public void gameSetup()
	{
		System.out.println("Masters of the Renaissance!");

		/* Server tells clients info about the four leadercards */
		System.out.println("Choose leader card 1, 2, 3 or 4: ");
		networkHandler.send(input.nextLine());
	}

	public void printBoard()
	{
		System.out.println("[ | | | | | | | | | | | | | | | | | | | | | | | | | ]");		/* wow */
	}

	public void printPlayerLeaderCard(Object card)		/* Can't access skill variables from LeaderCard because it's abstract */
	{
		System.out.println("Points: " + ((LeaderCard)card).getPoints());
		System.out.println("Discarded: " + ((LeaderCard)card).isDiscarded());

		if (card.getClass().getSimpleName() == "SkillDiscount")
		{
			// ...
		}

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
