package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * SkillMarble cards have the ability of converting white marbles in the marbles market to a resource of type "whiteMarble".
 * In order to be activated, they require two dev cards of any level, different colors specified in the requirements instance variable
 * @author Giulio Occhipinti
 */

public class SkillMarble extends LeaderCard
{
	private ResourceType whiteMarble;
	private List<DevCardColor> requirements;		/* Like SkillDiscount but it's 2 + 1 cards in the base game, like [YELLOW, YELLOW, BLUE] */

	/**
	 * Constructor
	 */

	public SkillMarble()
	{
		requirements = new ArrayList<>();
	}

	@Override
	public boolean checkRequirements(Player player)		/* Player has to have x devcards of any level */
	{
		List<DevCard> devCards = player.getDashboard().getAllDevCards();
		List<DevCardColor> colorRequirements = this.getRequirements();

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
	}

	public ResourceType getWhiteMarble()
	{
		return whiteMarble;
	}

	public void setWhiteMarble(ResourceType whiteMarble)
	{
		this.whiteMarble = whiteMarble;
	}

	public List<DevCardColor> getRequirements()
	{
		return requirements;
	}

	public void setRequirements(List<DevCardColor> requirements)
	{
		this.requirements = requirements;
	}
}
