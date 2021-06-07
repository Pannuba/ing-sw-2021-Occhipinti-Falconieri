package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.server.controller.Controller;

import java.util.List;

/**
 * @author Giulio Occhipinti
 */

public class InitialResourcesCommand implements Command		/* "INITIAL_RESOURCES", "BY", "2" */
{
	@Override
	public boolean run(Controller controller, List<String> command, String username, Model model)
	{
		String message = "";
		boolean isFailed = false;

		if (!command.get(1).isEmpty())
		{
			for (int i = 0; i < command.get(1).length(); i++)
			{
				ResourceType resourceToAdd = ResourceType.convertStringToResType(Character.toString(command.get(1).charAt(i)));
				model.getPlayerByUsername(username).getDashboard().getStorage().addResourceSmart(resourceToAdd);
			}
		}

		if (!command.get(2).isEmpty())		/* If there are initial faithpoints, get that player's pawn and move it */
			controller.updatePlayerPosition(model.getPlayerByUsername(username).getId(), Integer.parseInt(command.get(2)));

		return isFailed;
	}
}
