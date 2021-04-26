package server.model.cards;

public class PopeToken
{
	private int points;
	private boolean isUsed;

	public PopeToken(int points, boolean isUsed)
	{
		this.points = points;		/* Check in separate program if this.x works in constructor, but it should */
		this.isUsed = isUsed;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public boolean isUsed()
	{
		return isUsed;
	}

	public void setUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}
}
