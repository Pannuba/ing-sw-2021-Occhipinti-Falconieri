package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket implements Serializable
{
	private List<DevCard> devCards = new ArrayList<>();
	private int cardsInMarket;

	public DevCardsMarket()
	{
		System.out.println("Creating devcards...");

		cardsInMarket = 48;

		for (int i = 0; i < 48; i++)
		{
			try
			{
				devCards.add((DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard" + (i + 1) + ".xml"));
				//devCards.get(i).setCardNumber(i + 1); Already in xmls, TODO: do the same to leadercards
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public DevCard buyCardFromMarket(int boughtCardNum)
	{
		DevCard boughtCard = getDevCardByNumber(boughtCardNum);
		devCards.remove(boughtCard);
		cardsInMarket--;

		return boughtCard;
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
