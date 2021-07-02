package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Single DevCard area of the dashboard
 * @author Giulio Occhipinti
 */

public class DevCardArea implements Serializable			/* Devcards can't be removed once added */
{
	private boolean isEmpty;
	private List<DevCard> devCards = new ArrayList<>();
	private int layer;

	/**
	 * Constructor, creates an empty area
	 */

	public DevCardArea()
	{
		isEmpty = true;
		layer = 0;
	}

	/**
	 * Check that there aren't fewer than zero cards or more than three
	 * @return false if there's a problem in the dev card area, true otherwise
	 */

	public boolean checkDevCardArea()			/* Layer is 0 if there are no devcards, 3 if there are 3 devcards */
	{
		if (layer < 0 || layer > 3)
		{
			System.out.println("checkDevCardArea: layer is < 0 or > 3");
			return false;
		}

		else
			return true;
	}

	/**
	 * Add the devCard to the area and check that the area does not already have three cards or that the card to add is compatible
	 * If layer is 0 we can add a card of level 1
	 * If layer is 1 we can add a card of level 2
	 * If layer is 2 we can add a card of level 3
	 * @return true if the card is added to the area, false otherwise
	 */

	public boolean addDevCard(DevCard cardToAdd)	/* changed from void to boolean for testing */
	{
		if (layer == 3)
		{
			System.out.println("Error: there are already three cards");
			return false;
		}

		if (cardToAdd.getLevel() == layer + 1)		/* if layer is 0 and we add a card of level 1 (0 + 1), it's ok */
		{
			devCards.add(cardToAdd);
			layer++;

			if (isEmpty)
				isEmpty = false;

			return true;
		}

		else
		{
			System.out.println("Card to be added is not compatible with current dev card area");
			return false;
		}
	}

	/**
	 * The devCard of highest level is the most important because it's the one used for production
	 * @return the card in the area with the highest level
	 */

	public DevCard getTopDevCard()
	{
		return devCards.get(layer - 1);		/* If layer is 3, the third devcard has index 2: [0, 1, 2] */
	}

	public boolean isEmpty()
	{
		return isEmpty;
	}

	public void setEmpty(boolean empty)
	{
		isEmpty = empty;
	}

	public List<DevCard> getDevCards()
	{
		return devCards;
	}

	public void setDevCards(List<DevCard> devCards)
	{
		this.devCards = devCards;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
}
