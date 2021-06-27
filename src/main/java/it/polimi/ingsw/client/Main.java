package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUILoader;

public class Main		/* Starting point from which the user chooses to start the CLI or the GUI */
{
	public static void main(String[] args)
	{
		if (args.length == 0)
			new GUILoader();

		else if (args[0].equals("-cli"))
			new CLI();
	}
}
