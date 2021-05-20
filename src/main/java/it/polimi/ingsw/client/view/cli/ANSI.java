package it.polimi.ingsw.client.view.cli;

public enum ANSI	       /* https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797 | https://unicode.org/charts/nameslist/n_25A0.html */
{
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	GREY("\u001B[38;5;245m"),
	WHITE("\u001B[37m"),

	RESET("\u001B[0m"),

	MARBLE("\u2B24"),		/* ⬤ */
	RESOURCE("\u2B1B"),		/* ⬛ */
	EMPTY("\u25A1");			/* □ */

	private final String escape;

	ANSI(String escape)
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
}
