package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.DevCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.IOException;

public class DevCardsMarket
{
	private DevCard[] devCards = new DevCard[48];

	public DevCardsMarket() throws IOException
	{
		XML_Serialization xmlDecode = new XML_Serialization();

		for (int i = 0; i < 48; i++)
		{
			devCards[i] = new DevCard();		/* NullPointerException without this */
			devCards[i] = (DevCard) xmlDecode.deserialize("resources/xml/devcards/leadercard" + (i+1) + ".xml");
		}
	}

	public DevCard[] getDevCards()
	{
		return devCards;
	}

	public void setDevCards(DevCard[] devCards)
	{
		this.devCards = devCards;
	}
}
