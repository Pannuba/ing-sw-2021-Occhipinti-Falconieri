package model.board;

import model.cards.DevCard;

public class DevCardArea
{
	private DevCard devCard;
	private int layer;

	public DevCardArea()
	{
		layer = 0;
	}

	public boolean checkDevCardArea()
	{
		if (layer < 0 || layer > 3)
		{
			return false;	// Error
		}

		else
			return true;
	}

	public void addDevCard(DevCard cardToAdd)
	{
		if (layer == 3)
		{
			System.out.println("Error: there are already three cards");		/* Need to use logger (or do we?) */
			return;
		}

		if (cardToAdd.getLevel() == layer + 1)		/* if layer is 0 and we add a card of level 1 (0 + 1), it's ok */
		{
			devCard = cardToAdd;
			layer++;
		}
	}

	public DevCard getDevCard()
	{
		return devCard;
	}

	public void setDevCard(DevCard devCard)
	{
		this.devCard = devCard;
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
