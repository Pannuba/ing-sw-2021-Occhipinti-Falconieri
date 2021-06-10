package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;
import it.polimi.ingsw.server.controller.Controller;

import java.util.List;

/*	When the player has finished the production, all booleans "isDoingProduction" in the player's cards and the one for the default production
	are set back to false. After this command is executed the controller performs the postRoundChecks and sends the new gamestate to all players
 */

public class StopProductionCommand implements Command
{
	private final Model model;
	private final Controller controller;
	private final String username;

	public StopProductionCommand(Controller controller)
	{
		this.controller = controller;
		model = controller.getModel();
		username = controller.getUsername();
	}

	@Override
	public boolean run(List<String> command)
	{
		if (model.getPlayerByUsername(username).isDoingDefaultProduction())
			model.getPlayerByUsername(username).setDoingDefaultProduction(false);

		for (int i = 0; i < model.getPlayerByUsername(username).getDashboard().getDevCardAreas().length; i++)	/* For each dev card area */
			model.getPlayerByUsername(username).getDashboard().getDevCardAreas()[i].getTopDevCard().setUsedForProduction(false);

		List<LeaderCard> leaderCards = model.getPlayerByUsername(username).getLeaderCards();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillProduction)
				((SkillProduction) model.getPlayerByUsername(username).getLeaderCards().get(i)).setUsedForProduction(false);
		}

		return false;
	}
}
