package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUILoader;

/**
 * Main class. If no arguments are specified, start the GUI. Otherwise if the user adds the -cli argument, start the CLI
 */

public class Main
{
	public static void main(String[] args)
	{
		if (args.length == 0)
			new GUILoader();

		else if (args[0].equals("-cli"))
			new CLI();
	}
}
