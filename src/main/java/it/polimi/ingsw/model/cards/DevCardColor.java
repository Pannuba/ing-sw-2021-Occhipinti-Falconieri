package it.polimi.ingsw.model.cards;

public enum DevCardColor
{
	BLUE,
	GREEN,
	YELLOW,
	PURPLE;

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
