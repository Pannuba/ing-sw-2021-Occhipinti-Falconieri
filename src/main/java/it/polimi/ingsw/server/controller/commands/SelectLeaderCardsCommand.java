package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class SelectLeaderCardsCommand implements Command		/* "SELECT_LEADERCARDS", "x", "y" */
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
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
