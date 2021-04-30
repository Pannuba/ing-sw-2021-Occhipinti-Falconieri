package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DevCardsMarket;

import java.util.List;

public class ActionDevCard extends ActionToken
{
	private DevCardColor color;

	public void removeDevCards(DevCardsMarket devCardsMarket)			/* Remove 2 devcards of color from market starting from the lowest level */
	{
		List<DevCard> devCards = devCardsMarket.getDevCards();		/* Put this here or outside removeDevCards? */
		int cardsToRemove = 2;

		/* Scan all cards looking for the ones with the color we want, then delete them (set them as null) */

		for (int i = 0; i < 3; i++)		/* Check all 3 levels starting from 1 */
		{
			for (int j = 0; j < devCards.size(); j++)
			{
				if (devCards.get(j).getColor() == color && devCards.get(j).getLevel() == i+1 && cardsToRemove != 0)
				{
					devCards.remove(j);
					cardsToRemove--;
				}
			}
		}
		devCardsMarket.setDevCards(devCards);
	}

	public DevCardColor getColor()
	{
		return color;
	}

	public void setColor(DevCardColor color)
	{
		this.color = color;
	}
}
