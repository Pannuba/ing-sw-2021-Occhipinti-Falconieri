package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class DevCardsMarketTest {

	private DevCardsMarket devCardsMarket;
	private DevCard card;

	@Test
	public void buyCardFromMarket()
	{
		devCardsMarket = new DevCardsMarket();
		card = new DevCard();
		card = devCardsMarket.getDevCardByNumber(27);
		assertEquals("Error", card, devCardsMarket.buyCardFromMarket(27));
		card = devCardsMarket.getDevCardByNumber(30);
		assertEquals("Error", card, devCardsMarket.buyCardFromMarket(30));
		assertNull("Error", devCardsMarket.getDevCardByNumber(27));				/* check that the card has really been removed */
		assertNull("Error", devCardsMarket.getDevCardByNumber(30));
	}
}