package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.Controller;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CLI		/* Is this the view? Controller? Both? */
{
	private static Scanner input;
	private static Socket socket;
	private static Controller controller;
	private static DataInputStream dis;
	private static DataOutputStream dos;

	public static void main(String[] args) throws IOException
	{
		input = new Scanner(System.in);
		socket = new Socket("127.0.0.1", 2000);		/* Add port to config file? */
		controller = new Controller();
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());

		gameSetup();

		dos.flush();
		dos.close();
		socket.close();
	}

	public static void gameSetup() throws IOException
	{
		System.out.println("Masters of the Renaissance!\n\nNew game: 1\tJoin Game: 2");		/* TODO: add error detection (if input is not 1 nor 2) */

		/* Client directly asks for code, numPlayers... No reason to make the server ask because it's a standard process */
		/* ALWAYS use writeUTF(), never writeInt() or anything else */

		switch(input.nextLine())		/* For now there's only one match, no game thread in server */
		{
			case "1":
				dos.writeUTF("NEW_GAME");
				System.out.println("Game code: " + dis.readUTF());
				System.out.println("Insert number of players:" );
				dos.writeUTF(input.nextLine());
				break;

			case "2":
				dos.writeUTF("JOIN_GAME");
				System.out.println("Insert game code: ");
				dos.writeUTF(input.nextLine());

				break;

			default:
				break;
		}

		System.out.println("Insert username: ");
		dos.writeUTF(input.nextLine());
		/* Server tells clients info about the four leadercards */
		System.out.println("Choose leader card 1, 2, 3 or 4: ");
		dos.writeUTF(input.nextLine());
	}

	public void printBoard()
	{
		System.out.println("[ | | | | | | | | | | | | | | | | | | | | | | | | | ]");		/* wow */
	}

	public void printPlayerLeaderCard(LeaderCard hi)
	{

	}

	public void printPlayerDevCards() 		/* Model should be in client in order to decode the DevCard objects, unless the server decodes them before sending them */
	{

	}

	public void printDevCardsMarket()
	{

	}

	public void printMarblesMarket()
	{

	}

}
