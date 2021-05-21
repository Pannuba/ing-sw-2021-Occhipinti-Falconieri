package it.polimi.ingsw.model.cards;

import java.io.Serializable;

/*	Parameter editor has to edit leadercard requirements. For simplicity's sake, and order to keep symmetry with the original game,
	the type of requirement cannot be changed (devcards, resources).

	Every round, the player can either play the leadercard and it stays active for the rest of the match, giving the victory points
	in calculatePoints() OR discard the leadercard and gain 1 faithPoint, with no victory points at the end of the round
*/

public abstract class LeaderCard implements Serializable        /* Can't do new LeaderCard(); because it's abstract */
{
	private int cardNumber;
	private int points;
	private boolean isDiscarded;		/* When the card is discarded it can no longer be activated, gives 1 faithpoint and no victorypoints */
	private boolean isActive;			/* When the card is activated it can no longer be discarded, giving "points" victorypoints */

	public LeaderCard()
	{
		isDiscarded = false;
		isActive = false;
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

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean active)
	{
		isActive = active;
	}
}
