package it.polimi.ingsw.model;

/**
 * MarbleType enumeration, contains the six possible colors marble can have
 * @author Giulio Occhipinti
 */

public enum MarbleType
{
	RED,
	WHITE,
	BLUE,
	GREY,
	YELLOW,
	PURPLE;

	/**
	 * Converts a string to a MarbleType. Used by MarblesMarket's create() method
	 * @param str the string is to be converted
	 * @return the converted MarbleType
	 */

	public static MarbleType convertStringToMarbleType(String str)
	{
		if (str == null)
			return null;

		switch (str.toUpperCase())		/* So lowercase inputs like "g" are still valid */
		{
			case "R":
				return MarbleType.RED;

			case "W":
				return MarbleType.WHITE;

			case "B":
				return MarbleType.BLUE;

			case "G":
				return MarbleType.GREY;

			case "Y":
				return MarbleType.YELLOW;

			case "P":
				return MarbleType.PURPLE;

			default:
				System.out.println("convertStringToMarbleType: error");
				return null;
		}
	}
}