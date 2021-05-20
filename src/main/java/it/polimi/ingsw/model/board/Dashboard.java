package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dashboard implements Serializable
{
	private Vault vault;
	private Storage storage;
	private DevCardArea[] devCardAreas = new DevCardArea[3];

	public Dashboard()
	{
		for (int i = 0; i < 3; i++)
			devCardAreas[i] = new DevCardArea();

		vault = new Vault();
		storage = new Storage();
	}

	public int getTotalDevCardsNum()
	{
		int totCards = 0;

		for (int i = 0; i < 3; i++)
			totCards += devCardAreas[i].getLayer();

		return totCards;
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

	public Vault getVault()
	{
		return vault;
	}

	public void setVault(Vault vault)
	{
		this.vault = vault;
	}

	public Storage getStorage()
	{
		return storage;
	}

	public void setStorage(Storage storage)
	{
		this.storage = storage;
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
