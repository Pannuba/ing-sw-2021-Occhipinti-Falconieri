package model;

public enum ActionToken
{
	TOKEN_CROSS_1("-2", "Y"),
	TOKEN_CROSS_2("-2", "P");
	/*TOKEN_DISCARD_BLU,
	TOKEN_DISCARD_GREEN,
	TOKEN_DISCARD_YELLOW,
	TOKEN_DISCARD_PURPLE,
	TOKEN_CHANGE,*/

	private final String num = new String();
	private final String type = new String();

	ActionToken(String num, String type)
	{

	}

}
