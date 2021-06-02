package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.ResourceType;

public class ConvertMethods
{
	public static String convertResTypeToPath(ResourceType resType)
	{
		switch (resType)
		{
			case PURPLE:
				return "/img/resources/purple.png";

			case YELLOW:
				return "/img/resources/yellow.png";

			case GREY:
				return "/img/resources/grey.png";

			case BLUE:
				return "/img/resources/blue.png";
		}

		return null;
	}
}
