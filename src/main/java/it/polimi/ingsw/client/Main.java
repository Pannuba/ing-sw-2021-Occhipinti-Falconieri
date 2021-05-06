package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;

import java.io.IOException;
import java.util.Scanner;

public class Main		/* Starting point from which the user chooses to start the CLI or the GUI */
{
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		String choice = "";

		System.out.print("Start with CLI (1) or GUI (2)? ");
		choice = input.nextLine();

		while(!choice.equals("1") && !choice.equals("2"))
		{
			System.out.println("Insert 1 or 2: ");
			choice = input.nextLine();
		}

		switch(choice)
		{
			case "1":
				CLI cli = new CLI();
				break;

			case "2":
				GUI gui = new GUI();
				break;
		}
	}
}
