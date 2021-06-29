package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.commands.Command;

import java.util.List;

/*	When the player has finished the production, all booleans "isDoingProduction" in the player's cards and the one for the default production
	are set back to false. After this command is executed the controller performs the postRoundChecks and sends the new gamestate to all players
 */

public class StopProductionCommand
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		if (model.getPlayer().isDoingDefaultProduction())
			model.getPlayer().setDoingDefaultProduction(false);

		for (int i = 0; i < model.getPlayer().getDashboard().getDevCardAreas().length; i++)	/* For each dev card area */
		{
			if (!model.getPlayer().getDashboard().getDevCardAreas()[i].isEmpty())		/* IndexOutOfBoundsException if we don't check if the area is empty */
				model.getPlayer().getDashboard().getDevCardAreas()[i].getTopDevCard().setUsedForProduction(false);
		}

		List<LeaderCard> leaderCards = model.getPlayer().getLeaderCards();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillProduction)
				((SkillProduction) model.getPlayer().getLeaderCards().get(i)).setUsedForProduction(false);
		}

		return false;
	}
}
