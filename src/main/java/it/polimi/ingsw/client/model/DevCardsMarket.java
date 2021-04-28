package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.model.cards.DevCard;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket
{
	private List<DevCard> devCards = new ArrayList<DevCard>();

	public List<DevCard> getDevCards()
	{
		return devCards;
	}

	public void setDevCards(List<DevCard> devCards)
	{
		this.devCards = devCards;
	}
}
