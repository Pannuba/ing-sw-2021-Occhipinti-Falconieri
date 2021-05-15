package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public abstract class LeaderCard implements Serializable        /* Can't do new LeaderCard(); because it's abstract */
{
	private int cardNumber;
	private int points;
	private boolean isDiscarded;		/* Not included in cards .xmls, is set when the player discards a leadercard */

	public LeaderCard()				/* For some reason, XML_Serialization throws exceptions without this */
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
