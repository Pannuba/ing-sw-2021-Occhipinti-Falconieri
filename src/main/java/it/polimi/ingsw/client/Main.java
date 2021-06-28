package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUILoader;

public class Main		/* If no launch arguments are specified, start the GUI. Only start the CLI with the -cli arg */
{
	public static void main(String[] args)
	{
		if (args.length == 0)
			new GUILoader();

		else if (args[0].equals("-cli"))
			new CLI();
	}
}
