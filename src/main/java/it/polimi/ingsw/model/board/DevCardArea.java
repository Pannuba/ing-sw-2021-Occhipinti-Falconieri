package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DevCardArea implements Serializable            /* Devcards can't be removed once added */
{
	private boolean isEmpty;
	private List<DevCard> devCards = new ArrayList<DevCard>();
	private int layer;

	public DevCardArea()
	{
		isEmpty = true;
		layer = 0;
	}

	public boolean checkDevCardArea()			/* Layer is 0 if there are no devcards, 3 if there are 3 devcards */
	{
		if (layer < 0 || layer > 3)
		{
			return false;	// Error
		}

		else
			return true;
	}

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

	public DevCard getTopDevCard()			/* The devcard of highest level is the most important */
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
