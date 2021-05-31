package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.util.Pair;

import java.io.Serializable;
import java.util.List;

/*	Parameter editor has to edit leadercard requirements. For simplicity's sake, and order to keep symmetry with the original game,
	the type of requirement cannot be changed (devcards, resources).

	Every round, the player can either play the leadercard and it stays active for the rest of the match, giving the victory points
	in calculatePoints() OR discard the leadercard and gain 1 faithPoint, with no victory points at the end of the round
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

	public boolean checkRequirements(Dashboard playerBoard)		/* True if requirements are satisfied. Here or Model? */
	{
		List<DevCard> devCards = playerBoard.getAllDevCards();

		switch (this.getClass().getSimpleName())		/* Works! "this" is black magic I swear */
		{
			case "SkillDiscount":		/* Player has to have x devcards of any level */
			case "SkillMarble":

				List<DevCardColor> colorRequirements = ((SkillDiscount) this).getRequirements();

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
