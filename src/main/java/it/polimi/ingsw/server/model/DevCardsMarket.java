package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.DevCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevCardsMarket
{
	private List<DevCard> devCards = new ArrayList<DevCard>();

	public DevCardsMarket() throws IOException
	{
		XML_Serialization xmlDecode = new XML_Serialization();

		for (int i = 0; i < 48; i++)
			devCards.add((DevCard) xmlDecode.deserialize("resources/xml/devcards/leadercard" + (i+1) + ".xml"));
	}

	public DevCard getDevCard(int cardNumber)		/* Set method useless */
	{
		return devCards.get(cardNumber - 1);		/* - 1 because lists are 0-indexed, so cards go from 0 to 47 */
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
