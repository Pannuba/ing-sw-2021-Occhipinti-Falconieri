package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DevCardsMarket;

import java.util.List;

public class ActionDevCard extends ActionToken
{
	private DevCardColor color;
	private DevCardsMarket devCardsMarket;

	public ActionDevCard(DevCardColor color, DevCardsMarket devCardsMarket)
	{
		this.color = color;
		this.devCardsMarket = devCardsMarket;		/* Pass pointer to devCardsMarket in constructor to avoid doing it when calling action(), */
	}												/* so the abstract method action can be used correctly */

	@Override
	public void doAction()			/* Remove 2 devcards of color from market starting from the lowest level */
	{
		System.out.println("Activating action of ActionDevCard");
		List<List<DevCard>> devCardStacks = devCardsMarket.getDevCardStacks();		/* Put this here or outside removeDevCards? */
		int cardsToRemove = 2;

		/* Scan all cards looking for the ones with the color we want, then delete them */

		for (int i = 0; i < 3; i++)		/* Check all 3 levels starting from 1 (i+1 in check) */
		{
			for (int j = 0; j < devCardStacks.size(); j++)		/* 12 */
			{
				for (int k = 0; k < devCardStacks.get(j).size(); k++)
				{
					if (devCardStacks.get(j).get(k).getColor() == color && devCardStacks.get(j).get(k).getLevel() == (i + 1) && cardsToRemove != 0)
					{
						devCardStacks.get(j).remove(k);
						cardsToRemove--;
						k--;    /* Thanks IntelliJ I love you */
					}
				}
			}
		}

		/* TODO: if cardsToRemove != 0 error? */

		devCardsMarket.setDevCardStacks(devCardStacks);
	}

	public DevCardColor getColor()
	{
		return color;
	}

	public void setColor(DevCardColor color) { this.color = color; }

	public DevCardsMarket getDevCardsMarket() { return devCardsMarket; }

	public void setDevCardsMarket(DevCardsMarket devCardsMarket) { this.devCardsMarket = devCardsMarket; }
}
