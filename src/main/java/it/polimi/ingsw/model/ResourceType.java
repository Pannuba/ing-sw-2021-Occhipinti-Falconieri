package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum ResourceType	/* TODO: add RED resource, remove TODOs scattered around the project about this issue */
{
	BLUE,
	GREY,
	YELLOW,
	PURPLE;

	public static ResourceType convertStringToResType(String str)
	{
		if (str == null)
			return null;

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
				System.out.println("convertStringToResType: error");
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

	public static List<Resource> convertResTypeListToResList(List<ResourceType> list)
	{
		List<Resource> resList = new ArrayList<>();

		resList.add(new Resource(BLUE,	 0));
		resList.add(new Resource(GREY,	 0));
		resList.add(new Resource(YELLOW, 0));
		resList.add(new Resource(PURPLE, 0));

		for (int i = 0; i < list.size(); i++)
		{
			switch (list.get(i))
			{
				case BLUE:
					resList.get(0).setQuantity(resList.get(0).getQuantity() + 1);
					break;

				case GREY:
					resList.get(1).setQuantity(resList.get(1).getQuantity() + 1);
					break;

				case YELLOW:
					resList.get(2).setQuantity(resList.get(2).getQuantity() + 1);
					break;

				case PURPLE:
					resList.get(3).setQuantity(resList.get(3).getQuantity() + 1);
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
