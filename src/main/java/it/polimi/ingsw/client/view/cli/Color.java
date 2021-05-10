package it.polimi.ingsw.client.view.cli;

public enum Color		/* https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797 */
{
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	GREY("\u001B[38;5;245m"),
	WHITE("\u001B[37m");

	public static final String RESET = "\u001B[0m";

	private final String escape;

	Color(String escape)
	{
		this.escape = escape;
	}

	public String getEscape()
	{
		return escape;
	}

	@Override
	public String toString()
	{
		return escape;
	}

	public String getName()
	{
		switch (this)
		{
			case RED:
				return "Red";

			case GREEN:
				return "Green";

			case YELLOW:
				return "Yellow";

			case PURPLE:
				return "Purple";

			default:
				return "Blue";
		}
	}
}
