package it.polimi.ingsw.model.cards;

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
	 * @param playerBoard used to take the requirements from the player board to activate the card
	 * @return true if requirements are satisfied
	 */

	/* TODO: add checkRequirements to each Skill.... class? */

	public boolean checkRequirements(Dashboard playerBoard)		/* True if requirements are satisfied. Here or Model? */
	{
		List<DevCard> devCards = playerBoard.getAllDevCards();
		List<DevCardColor> colorRequirements = new ArrayList<>();		/* SkillMarble and SkillDiscount have the same requirement type */

		if (this.getClass().getSimpleName().equals("SkillMarble"))
			colorRequirements = ((SkillMarble) this).getRequirements();

		if (this.getClass().getSimpleName().equals("SkillDiscount"))
			colorRequirements = ((SkillDiscount) this).getRequirements();

		switch (this.getClass().getSimpleName())		/* Works! "this" is black magic I swear */
		{
			case "SkillDiscount":		/* Player has to have x devcards of any level */
			case "SkillMarble":

				for (int i = 0; i < devCards.size(); i++)
				{
					for (int j = 0; j < colorRequirements.size(); j++)
					{
						if (devCards.get(i).getColor() == colorRequirements.get(j))
						{
							colorRequirements.remove(j);		/* Remove a requirement for every card that satisfies it */
							j--;
						}
					}
				}

				if (colorRequirements.isEmpty())				/* If there are no more requirements left, it means they're all satisfied */
					return true;

				else
					return false;

			case "SkillProduction":		/* Player has to have 1 devcard of color x and level y */

				Pair<DevCardColor, Integer> prodRequirements = ((SkillProduction) this).getRequirements();

				for (int i = 0; i < devCards.size(); i++)
				{
					if (devCards.get(i).getColor() == prodRequirements.getObj1() && devCards.get(i).getLevel() == prodRequirements.getObj2())
						return true;
				}

				return false;

			case "SkillStorage":		/* Player has to have x resources of the same type */

				Resource storageRequirements = ((SkillStorage) this).getRequirements();
				Storage storage = playerBoard.getStorage();
				Vault vault = playerBoard.getVault();

				int reqResourceQuantity = 0;
				reqResourceQuantity += storage.findResourceByType(storageRequirements.getResourceType());
				/* TODO: add existing SkillStorage? */
				reqResourceQuantity += vault.getResourceAmounts().get(storageRequirements.getResourceType());

				if (reqResourceQuantity >= storageRequirements.getQuantity())
					return true;

				else
					return false;
		}

		return false;
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
