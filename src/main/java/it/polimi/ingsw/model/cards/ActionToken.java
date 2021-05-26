package it.polimi.ingsw.model.cards;

public abstract class ActionToken
{
	private boolean isFlipped;

	public abstract void doAction();		/* All ActionTokens only do one thing when they are flipped. Executed by server controller, not client */

	public boolean isFlipped()
	{
		return isFlipped;
	}

	public void setFlipped(boolean flipped)
	{
		isFlipped = flipped;
	}
}
