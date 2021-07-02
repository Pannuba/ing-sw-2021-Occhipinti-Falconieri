package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/**
 * Abstract class for action tokens.
 * @author Giulio Occhipinti
 */

public abstract class ActionToken implements Serializable
{
	private boolean isFlipped;

	/**
	 * Perform the token's action. Called by the controller after every round, if it's a singleplayer match
	 */

	public abstract void doAction();		/* All ActionTokens only do one thing when they are flipped */

	public boolean isFlipped()
	{
		return isFlipped;
	}

	public void setFlipped(boolean flipped)
	{
		isFlipped = flipped;
	}
}
