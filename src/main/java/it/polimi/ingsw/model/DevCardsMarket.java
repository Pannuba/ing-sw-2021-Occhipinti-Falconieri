package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.util.XML_Serialization;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket implements Serializable
{
	private List<DevCard> devCards = new ArrayList<>();

	public DevCardsMarket()
	{
		System.out.println("Creating devcards...");

		for (int i = 0; i < 48; i++)
		{
			try
			{
				devCards.add((DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard" + (i + 1) + ".xml"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public DevCard buyCardFromMarket(int boughtCardNum)
	{
		System.out.println("DevCardMarket: buying card #" + boughtCardNum);
		DevCard boughtCard = getDevCardByNumber(boughtCardNum);
		devCards.remove(boughtCard);

		return boughtCard;
	}

	public DevCard getDevCardByNumber(int cardNumber)		/* Set method useless */
	{
		for (int i = 0; i < devCards.size(); i++)
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
}
