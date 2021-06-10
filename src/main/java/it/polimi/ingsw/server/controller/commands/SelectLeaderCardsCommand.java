package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class SelectLeaderCardsCommand implements Command		/* "SELECT_LEADERCARDS", "x", "y" */
{
	private final Model model;
	private final Controller controller;
	private final String username;

	public SelectLeaderCardsCommand(Controller controller)
	{
		this.controller = controller;
		model = controller.getModel();
		username = controller.getUsername();
	}

	@Override
	public boolean run(List<String> command)
	{
		String message = "";
		boolean isFailed = false;

		List<LeaderCard> cards = new ArrayList<>();

		for (int i = 0; i < model.getAllLeaderCards().size(); i++)
		{
			if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1))			||
				String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(2))			)
				cards.add(model.getAllLeaderCards().get(i));
		}

		model.getPlayerByUsername(username).setLeaderCards(cards);

		return isFailed;
	}
}
