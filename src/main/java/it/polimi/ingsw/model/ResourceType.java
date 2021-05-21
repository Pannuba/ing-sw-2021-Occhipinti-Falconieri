package it.polimi.ingsw.model;

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

		switch(str.toUpperCase())		/* So lowercase inputs like "g" are still valid */
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

		switch(marbleType)
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
}
