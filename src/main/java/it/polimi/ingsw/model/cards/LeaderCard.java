package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/*	Parameter editor has to edit leadercard requirements. For simplicity's sake, and order to keep symmetry with the original game,
	the type of requirement cannot be changed (devcards, resources)
*/

public abstract class LeaderCard implements Serializable        /* Can't do new LeaderCard(); because it's abstract */
{
	private int cardNumber;
	private int points;
	private boolean isDiscarded;		/* Not included in cards .xmls, is set when the player discards a leadercard */
										/* boolean isActive? */

	public LeaderCard()
	{
		isDiscarded = false;
	}

	public int getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(int cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public boolean isDiscarded()
	{
		return isDiscarded;
	}

	public void setDiscarded(boolean discarded)
	{
		isDiscarded = discarded;
	}
}
