package model;

import tools.XML_Serialization;
import java.io.IOException;

public class DevCardsMarket
{
	private DevCard[]devCards = new DevCard[48];

	public DevCardsMarket() throws IOException
	{
		devCards[0] = new DevCard();
		devCards[0].setColor("lol");


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
