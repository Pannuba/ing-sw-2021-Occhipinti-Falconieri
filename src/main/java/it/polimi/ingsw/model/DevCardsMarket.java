package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket
{
	private List<DevCard> devCards = new ArrayList<DevCard>();

	public DevCardsMarket()
	{
		XML_Serialization xmlDecode = new XML_Serialization();

		for (int i = 0; i < 48; i++)
		{
			try
			{
				devCards.add((DevCard) xmlDecode.deserialize("src/main/resources/xml/devcards/leadercard" + (i + 1) + ".xml"));
				devCards.get(i).setCardNumber(i + 1);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public DevCard getDevCard(int cardNumber)		/* Set method useless */
	{
		for (int i = 0; i < 48; i++)
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
