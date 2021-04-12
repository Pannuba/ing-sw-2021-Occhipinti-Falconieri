package model.cards;

public enum ActionToken				/* Only used in singleplayer */
{
	TOKEN_CROSS_1("+1", "X"),
	TOKEN_CROSS_2("+2", "X"),
	TOKEN_CROSS_3("+2", "X"),
	TOKEN_DISCARD_BLU("-2", "B"),
	TOKEN_DISCARD_GREEN("-2", "G"),
	TOKEN_DISCARD_YELLOW("-2", "Y"),
	TOKEN_DISCARD_PURPLE("-2", "P");

	private final String num = new String();
	private final String type = new String();

	ActionToken(String num, String type)
	{

	}

	public String getNum()
	{
		return num;
	}

	public String getType()
	{
		return type;
	}
}
