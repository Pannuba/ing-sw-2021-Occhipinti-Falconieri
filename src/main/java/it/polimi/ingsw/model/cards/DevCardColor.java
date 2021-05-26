package it.polimi.ingsw.model.cards;

public enum DevCardColor
{
	BLUE,
	GREEN,
	YELLOW,
	PURPLE;

	public static DevCardColor convertStringToDevColorType(String str)
	{
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
				System.out.print("Error\n");
				return null;
		}
	}
}
