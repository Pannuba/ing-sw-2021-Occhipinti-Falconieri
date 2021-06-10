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
	private final Model model;
	private final Controller controller;
	private final String username;

	public InitialResourcesCommand(Controller controller)
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
