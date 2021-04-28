package it.polimi.ingsw.client.model.cards;

import it.polimi.ingsw.client.model.DevCardsMarket;

import java.util.List;

public class ActionDevCard extends ActionToken
{
	private DevCardColor color;

	public DevCardColor getColor()
	{
		return color;
	}

	public void setColor(DevCardColor color)
	{
		this.color = color;
	}
}
