package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.List;

public enum ResourceType
{
	BLUE,
	GREY,
	YELLOW,
	PURPLE;

	public static ResourceType convertStringToResType(String str)
	{
		if (str == null)
		{
			return null;
		}

		switch (str.toUpperCase())		/* So lowercase inputs like "g" are still valid */
		{
			case "G":
				return ResourceType.GREY;

			case "Y":
				return ResourceType.YELLOW;

			case "B":
				return ResourceType.BLUE;

			case "P":
				return ResourceType.PURPLE;

			default:
				System.out.print("Error\n");
				return null;
		}
	}

	public static ResourceType convertMarbleToResType(MarbleType marbleType)		/* Also check player leadercards SkillMarble, if active */
	{
		if (marbleType == null)
			return null;

		switch (marbleType)
		{
			case WHITE:			/* White marbles have their separate function */
				return null;

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

	public static HashMap<ResourceType, Integer> convertResTypeListToHashMap(List<ResourceType> list)
	{
		HashMap<ResourceType, Integer> map = new HashMap<>();

		map.put(BLUE, 0);
		map.put(GREY, 0);
		map.put(YELLOW, 0);
		map.put(PURPLE, 0);

		for (int i = 0; i < list.size(); i++)
			map.put(list.get(i), map.get(list.get(i)) + 1);			/* Increase counter for "key" resource type */

		return map;
	}
}
