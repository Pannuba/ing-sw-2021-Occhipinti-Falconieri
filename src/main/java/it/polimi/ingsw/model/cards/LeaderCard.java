package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for leader cards
 *
 * Parameter editor has to edit leaderCard requirements. For simplicity's sake, and order to keep symmetry with the original game,
 * the type of requirement cannot be changed (devCards, resources).
 *
 * Every round, the player can either play the leaderCard and it stays active for the rest of the match, giving the victory points
 * in calculatePoints() OR discard the leaderCard and gain one faithPoint, with no victory points at the end of the round
 *
 * @author Giulio Occhipinti
*/

public abstract class LeaderCard implements Serializable		/* Can't do new LeaderCard(); because it's abstract */
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

	/**
	 * Check if the leader card can be activated
	 * @param player used to take the requirements to activate the card
	 * @return true if requirements are satisfied
	 */

	/* TODO: add checkRequirements to each Skill.... class? */

	public abstract boolean checkRequirements(Player player);

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
