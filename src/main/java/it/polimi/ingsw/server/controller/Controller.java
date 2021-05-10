package it.polimi.ingsw.server.controller;

/* TODO: add possible actions the client can do */

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer			/* Observes view to get commands... View observes model right? */
{
	private final Model model;

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
	}

	public void parseInput(String username, List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.print("Command: ");

		for (int i = 0; i < command.size(); i++)		/* Prints command for debugging purposes */
			System.out.print(command.get(i) + " ");

		if (command.get(0).equals("SELECT_LEADERCARDS"))		/* "SELECT_LEADERCARDS", "x", "y" */
		{
			List<LeaderCard> cards = new ArrayList<LeaderCard>();

			for (int i = 0; i < 16; i++)
			{
				if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1)))
					cards.add(model.getAllLeaderCards().get(i));
			}

			for (int i = 0; i < 16; i++)
			{
				if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1)))
					cards.add(model.getAllLeaderCards().get(i));
			}
			model.getPlayerByUsername(username).setLeaderCards(cards);
		}

		if (command.get(0).equals("BUY_DEVCARD"))
		{
			// change model
		}

		if (command.get(0).equals("ACTIVATE_PRODUCTION"))
		{

		}

		if (command.get(0).equals("MARBLE_MARKET"))
		{

		}
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		parseInput(((ClientHandler)obs).getUsername(), (List<String>)obj);
	}
}