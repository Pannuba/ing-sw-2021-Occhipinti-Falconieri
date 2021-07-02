package it.polimi.ingsw.client.view.cli.actions;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class VaticanReport
{
	public VaticanReport(int popeBoxNum, List<Player> players, CLI cli)
	{
		Player localPlayer = new Player("temp");

		System.out.println("Vatican report on box #" + popeBoxNum + "!");

		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getUsername().equals(cli.getUsername()))
				localPlayer = players.get(i);
		}

		switch (popeBoxNum)
		{
			case 8:

				if (localPlayer.getPopeTokens()[0].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[0].getPoints() + " victory points!");

				else
					System.out.println("Discarded first pope token");

				break;

			case 16:

				if (localPlayer.getPopeTokens()[1].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[1].getPoints() + " victory points!");

				else
					System.out.println("Discarded second pope token");

				break;

			case 24:

				if (localPlayer.getPopeTokens()[2].isActive())
					System.out.println("Gained " + localPlayer.getPopeTokens()[2].getPoints() + " victory points!");

				else
					System.out.println("Discarded third pope token");

				break;

			default:
				System.out.println("This should never ever ever happen");
		}
	}
}
