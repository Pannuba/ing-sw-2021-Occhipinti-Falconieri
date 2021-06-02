package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.MarbleType;
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

	public static String convertMarbleTypeToPath(MarbleType type)
	{
		switch (type)
		{
			case WHITE:
				return "/img/marbles/white.png";

			case RED:
				return "/img/marbles/red.png";

			case PURPLE:
				return "/img/marbles/purple.png";

			case YELLOW:
				return "/img/marbles/yellow.png";

			case GREY:
				return "/img/marbles/grey.png";

			case BLUE:
				return "/img/marbles/blue.png";
		}

		return null;
	}
}
