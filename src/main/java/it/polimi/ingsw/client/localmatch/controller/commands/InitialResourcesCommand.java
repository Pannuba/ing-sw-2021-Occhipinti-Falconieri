package it.polimi.ingsw.client.localmatch.controller.commands;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class InitialResourcesCommand       /* "INITIAL_RESOURCES", "BY", "2" */
{
	public boolean run(LocalController controller, List<String> command, String username, LocalModel model)
	{
		String message = "";
		boolean isFailed = false;

		if (!command.get(1).isEmpty())
		{
			for (int i = 0; i < command.get(1).length(); i++)
			{
				ResourceType resourceToAdd = ResourceType.convertStringToResType(Character.toString(command.get(1).charAt(i)));
				model.getPlayer().getDashboard().getStorage().addResourceSmart(resourceToAdd);
			}
		}

		if (!command.get(2).isEmpty())		/* If there are initial faithpoints, get that player's pawn and move it */
			controller.updatePlayerPosition(model.getPlayer().getId(), Integer.parseInt(command.get(2)));

		return isFailed;
	}
}
