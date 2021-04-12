package model.cards;

import model.DevCardsMarket;

public class ActionDevCard extends ActionToken
{
	private DevCardColor color;

	public void removeDevCards(DevCardsMarket devCardsMarket)
	{
		DevCard[] oldCardArray = devCardsMarket.getDevCards();			/* Put this here or outside removeDevCards? */
		DevCard[] newCardArray = oldCardArray;

		/* Scan all cards looking for the ones with the color we want, then delete them (set them as null) */

		for (int i = 0; i < oldCardArray.length; i++)		/* Could be i < 48 but it's better this way */
		{
			if (oldCardArray[i].getColor() == color)
				oldCardArray[i] = null;
		}

		devCardsMarket.setDevCards(newCardArray);
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
