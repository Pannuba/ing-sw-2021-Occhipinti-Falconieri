package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DevCardsMarket;

import java.util.List;

public class ActionDevCard extends ActionToken
{
	private final DevCardColor color;
	private final DevCardsMarket devCardsMarket;

	public ActionDevCard(DevCardColor color, DevCardsMarket devCardsMarket)
	{
		this.color = color;
		this.devCardsMarket = devCardsMarket;		/* Pass pointer to devCardsMarket in constructor to avoid doing it when calling action(), */
	}												/* so the abstract method action can be used correctly */

	@Override
	public void doAction()			/* Remove 2 devcards of color from market starting from the lowest level */
	{
		System.out.println("Activating action of ActionDevCard");
		List<DevCard> devCards = devCardsMarket.getDevCards();		/* Put this here or outside removeDevCards? */
		int cardsToRemove = 2;

		/* Scan all cards looking for the ones with the color we want, then delete them */

		for (int i = 0; i < 3; i++)		/* Check all 3 levels starting from 1 */
		{
			for (int j = 0; j < devCards.size(); j++)
			{
				if (devCards.get(j).getColor() == color && devCards.get(j).getLevel() == (i + 1) && cardsToRemove != 0)
				{
					devCards.remove(j);
					cardsToRemove--;
					j--;	/* Thanks IntelliJ I love you */
				}
			}
		}

		/* TODO: if cardsToRemove != 0 error? */

		devCardsMarket.setDevCards(devCards);
	}

	public DevCardColor getColor()
	{
		return color;
	}
}
