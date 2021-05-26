package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dashboard implements Serializable
{
	private final Vault vault;
	private final Storage storage;
	private DevCardArea[] devCardAreas = new DevCardArea[3];

	public Dashboard()
	{
		for (int i = 0; i < 3; i++)
			devCardAreas[i] = new DevCardArea();

		vault = new Vault();
		storage = new Storage();
	}

	public List<DevCard> getAllDevCards()
	{
		List<DevCard> allDevCards = new ArrayList<>();

		for (int i = 0; i < 3; i++)
		{
			if (devCardAreas[i].isEmpty() == false)		/* To avoid exceptions when accessing devCards.get(0) if layer is 0 */
			{
				for (int j = 0; j < devCardAreas[i].getLayer(); j++)
					allDevCards.add(devCardAreas[i].getDevCards().get(j));
			}
		}

		return allDevCards;
	}

	public int getTotalDevCardsNum()		/* Used to calculate the sum of layers in all areas, but it's simply getAllDevCards().size() */
	{
		return getAllDevCards().size();
	}

	public DevCard getTopDevCardByNumber(int number)
	{
		for (int i = 0; i < 3; i++)
		{
			if (devCardAreas[i].isEmpty() == false)
			{
					if (devCardAreas[i].getTopDevCard().getCardNumber() == number)
						return devCardAreas[i].getTopDevCard();
			}
		}

		System.out.println("getTopDevCardByNumber: card not found!");
		return null;
	}

	public Vault getVault()
	{
		return vault;
	}

	public Storage getStorage()
	{
		return storage;
	}

	public DevCardArea[] getDevCardAreas()
	{
		return devCardAreas;
	}

	public void setDevCardAreas(DevCardArea[] devCardAreas)
	{
		this.devCardAreas = devCardAreas;
	}
}
