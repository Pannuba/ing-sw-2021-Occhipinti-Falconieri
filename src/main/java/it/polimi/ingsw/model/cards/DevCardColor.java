package it.polimi.ingsw.model.cards;

/**
 * Enumeration of all possible DevCard color types
 * @author Giulio Occhipinti
 */

public enum DevCardColor
{
	BLUE,
	GREEN,
	YELLOW,
	PURPLE;

	/**
	 * Convert the received string to devCard color Type, used by the dev card serialization process
	 * @param str the string to convert to a DevCardColor
	 * @return the devCard color type
	 */

	public static DevCardColor convertStringToDevColorType(String str)
	{
		if (str == null)
			return null;

		switch (str.toUpperCase())
		{
			case "G":
				return DevCardColor.GREEN;

			case "Y":
				return DevCardColor.YELLOW;

			case "B":
				return DevCardColor.BLUE;

			case "P":
				return DevCardColor.PURPLE;

			default:
				System.out.println("convertStringToDevColorType: error");
				return null;
		}
	}
}
