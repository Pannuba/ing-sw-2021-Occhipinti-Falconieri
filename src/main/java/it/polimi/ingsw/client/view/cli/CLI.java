package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.Controller;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CLI		/* Is this the view? Controller? Both? */
{
	private final Scanner input;
	private Socket socket;
	private Controller controller;
	private DataInputStream dis;
	private DataOutputStream dos;

	public CLI() throws IOException
	{
		controller = new Controller();
		input = new Scanner(System.in);

		System.out.println("Masters of the Renaissance!");

		try
		{
			socket = new Socket("127.0.0.1", 2000);		/* Add port to config file? */
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		dos.writeUTF("ping");
		gameSetup();

		dos.flush();
		dos.close();
		socket.close();
	}

	public void gameSetup() throws IOException
	{
		System.out.println("Insert username: ");
		dos.writeUTF(input.nextLine());

		/* Client directly asks for code, numPlayers... No reason to make the server ask because it's a standard process */
		/* ALWAYS use writeUTF(), never writeInt() or anything else */

		/* Server tells clients info about the four leadercards */
		System.out.println("Choose leader card 1, 2, 3 or 4: ");
		dos.writeUTF(input.nextLine());
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

	public void printPlayerDevCards() 		/* Model should be in client in order to decode the DevCard objects, unless the server decodes them before sending them */
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
				System.out.print(board[i][j] + "  ");
			}
			System.out.print("\n");
		}
	}

	public String convertMarbleToString(Marble marble)
	{
		final String marbleSymbol = "\u2B24";

		switch (marble.getMarbleType())
		{
			case YELLOW:
				return Color.YELLOW + marbleSymbol;

			case PURPLE:
				return Color.PURPLE + marbleSymbol;

			case WHITE:
				return Color.WHITE + marbleSymbol;

			case GREY:
				return Color.GREY + marbleSymbol;

			case BLUE:
				return Color.BLUE + marbleSymbol;

			case RED:
				return Color.RED + marbleSymbol;
		}

		return null;
	}



}
