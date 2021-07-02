package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ResourceType enumeration, contains the six possible types resources can have
 * @author Giulio Occhipinti
 */

public enum ResourceType
{
	RED,
	BLUE,
	GREY,
	YELLOW,
	PURPLE;

	/**
	 * Converts a string to a ResourceType. Used by the controller's "activate production" command
	 * @param str the string is to be converted
	 * @return the converted ResourceType
	 */

	public static ResourceType convertStringToResType(String str)
	{
		if (str == null)
			return null;

		switch (str.toUpperCase())		/* So lowercase inputs like "g" are still valid */
		{
			case "R":
				return ResourceType.RED;

			case "G":
				return ResourceType.GREY;

			case "Y":
				return ResourceType.YELLOW;

			case "B":
				return ResourceType.BLUE;

			case "P":
				return ResourceType.PURPLE;

			default:
				System.out.println("convertStringToResType: error");
				return null;
		}
	}

	/**
	 * Converts a MarbleType to a ResourceType. Used by the controller's "buy resources" command
	 * @param marbleType the MarbleType is to be converted
	 * @return the converted ResourceType
	 */

	public static ResourceType convertMarbleToResType(MarbleType marbleType)		/* Also check player leadercards SkillMarble, if active */
	{
		if (marbleType == null)
			return null;

		switch (marbleType)
		{
			case WHITE:			/* White marbles have their separate function */
				return null;

			case RED:
				return ResourceType.RED;

			case GREY:
				return ResourceType.GREY;

			case YELLOW:
				return ResourceType.YELLOW;

			case BLUE:
				return ResourceType.BLUE;

			case PURPLE:
				return ResourceType.PURPLE;

			default:
				System.out.println("convertMarbleToResType: error");
				return null;
		}
	}

	/**
	 * Converts a list of ResourceType to a list of Resource. Used by the controller's "buy resources" command
	 * @param list list of resource types that are to be converted
	 * @return the converted Resource list
	 */

	public static List<Resource> convertResTypeListToResList(List<ResourceType> list)
	{
		List<Resource> resList = new ArrayList<>();

		resList.add(new Resource(RED,	 0));
		resList.add(new Resource(BLUE,	 0));
		resList.add(new Resource(GREY,	 0));
		resList.add(new Resource(YELLOW, 0));
		resList.add(new Resource(PURPLE, 0));

		for (int i = 0; i < list.size(); i++)
		{
			switch (list.get(i))
			{
				case RED:
					resList.get(0).setQuantity(resList.get(0).getQuantity() + 1);
					break;

				case BLUE:
					resList.get(1).setQuantity(resList.get(1).getQuantity() + 1);
					break;

				case GREY:
					resList.get(2).setQuantity(resList.get(2).getQuantity() + 1);
					break;

				case YELLOW:
					resList.get(3).setQuantity(resList.get(3).getQuantity() + 1);
					break;

				case PURPLE:
					resList.get(4).setQuantity(resList.get(4).getQuantity() + 1);
					break;
			}
		}

		for (int i = 0; i < resList.size(); i++)
		{
			if (resList.get(i).getQuantity() == 0)		/* Remove empty resources */
			{
				resList.remove(i);
				i--;
			}
		}

		return resList;
	}
}
