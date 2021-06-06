package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;

import java.util.Scanner;

public class Main		/* Starting point from which the user chooses to start the CLI or the GUI */
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String choice = "";

		System.out.print("Start with CLI (1) or GUI (2)? ");
		choice = input.nextLine();

		while (!choice.equals("1") && !choice.equals("2"))
		{
			System.out.print("Insert 1 or 2: ");
			choice = input.nextLine();
		}

		switch (choice)
		{
			case "1":
				new CLI(input);
				break;

			case "2":
				new GUI();
				break;
		}
	}
}
