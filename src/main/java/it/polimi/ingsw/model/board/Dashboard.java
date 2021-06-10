package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Each player's dashboard
 * @author Giulio Occhipinti
 */

public class Dashboard implements Serializable
{
	/**
	 * Creates the three devCard areas, the vault and the storage
	 */

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

	/**
	 * Player's devCards
	 * @return all devCards the player owns
	 */

	public List<DevCard> getAllDevCards()
	{
		List<DevCard> allDevCards = new ArrayList<>();

		for (int i = 0; i < 3; i++)
		{
			if (!devCardAreas[i].isEmpty())		/* To avoid exceptions when accessing devCards.get(0) if layer is 0 */
			{
				for (int j = 0; j < devCardAreas[i].getLayer(); j++)
					allDevCards.add(devCardAreas[i].getDevCards().get(j));
			}
		}

		return allDevCards;
	}

	/**
	 * Adds the purchased card to the chosen devCard area
	 * @param cardToAdd purchased card
	 * @param areaIndex devCard area number
	 */

	public void addDevCardToArea(DevCard cardToAdd, int areaIndex)		/* Find the devCardArea where cardToAdd can be added, then adds it */
	{
		devCardAreas[areaIndex].addDevCard(cardToAdd);		/* Need to trust the client that it chose a compatible area... add checks in controller? */
	}

	/**
	 * Returns the highest card based on the entered area devCard number
	 * @param number devCard area number
	 * @return top card of the chosen devCard area
	 */

	public DevCard getTopDevCardByNumber(int number)
	{
		for (int i = 0; i < 3; i++)
		{
			if (!devCardAreas[i].isEmpty())
			{
					if (devCardAreas[i].getTopDevCard().getCardNumber() == number)
						return devCardAreas[i].getTopDevCard();
			}
		}

		System.out.println("getTopDevCardByNumber: card not found!");
		return null;
	}

	/**
	 * Returns the number of devCards the player owns
	 * Used to calculate the sum of layers in all areas
	 */

	public int getTotalDevCardsNum()		/* Used to calculate the sum of layers in all areas, but it's simply getAllDevCards().size() */
	{
		return getAllDevCards().size();
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
