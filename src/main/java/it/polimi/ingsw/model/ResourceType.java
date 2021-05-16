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
}
