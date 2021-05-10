package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket
{
	private List<DevCard> devCards = new ArrayList<DevCard>();
	private int cardsInMarket;

	public DevCardsMarket()
	{
		System.out.println("Creating devcards market");

		cardsInMarket = 48;

		for (int i = 0; i < 48; i++)
		{
			System.out.println("DevCardsMarket: creating devcard " + (i + 1));
			try
			{
				devCards.add((DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard" + (i + 1) + ".xml"));
				//devCards.get(i).setCardNumber(i + 1); Already in xmls
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void removeCardFromMarket(DevCard cardToRemove)
	{
		devCards.remove(cardToRemove);
		cardsInMarket--;
	}

	public DevCard getDevCardByNumber(int cardNumber)		/* Set method useless */
	{
		for (int i = 0; i < cardsInMarket; i++)
		{
			if (devCards.get(i).getCardNumber() == cardNumber)
				return devCards.get(i);
		}

		return null;
	}

	public List<DevCard> getDevCards()
	{
		return devCards;
	}

	public void setDevCards(List<DevCard> devCards)
	{
		this.devCards = devCards;
	}

	public int getCardsInMarket()
	{
		return cardsInMarket;
	}

	public void setCardsInMarket(int cardsInMarket)
	{
		this.cardsInMarket = cardsInMarket;
	}
}
