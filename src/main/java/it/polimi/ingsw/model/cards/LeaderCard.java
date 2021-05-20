package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public abstract class LeaderCard implements Serializable        /* Can't do new LeaderCard(); because it's abstract */
{
	private int cardNumber;
	private int points;
	private String requirements;		/* Can be devcards or resources, so it's a string that will be decoded elsewhere */
	private boolean isDiscarded;		/* Not included in cards .xmls, is set when the player discards a leadercard */
										/* boolean isActive? */

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

	public String getRequirements()
	{
		return requirements;
	}

	public void setRequirements(String requirements)
	{
		this.requirements = requirements;
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
