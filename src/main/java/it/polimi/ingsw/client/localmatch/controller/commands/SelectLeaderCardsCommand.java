package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class SelectLeaderCardsCommand      /* "SELECT_LEADERCARDS", "x", "y" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
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

		model.getPlayer().setLeaderCards(cards);

		return isFailed;
	}
}
