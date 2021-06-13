package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DevCardMarket tests
 * @author Chiara Falconieri
 */

public class DevCardsMarketTest {

	private DevCardsMarket devCardsMarket;
	private DevCard card;

	/**
	 * Removes two cards and check that the cards have really been removed
	 * buyCardFromMarket method
	 */

	@Test
	public void buyCardFromMarket()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		card = new DevCard();
		card = devCardsMarket.getDevCardByNumber(27);
		assertEquals("Error", card, devCardsMarket.buyCardFromMarket(27));
		card = devCardsMarket.getDevCardByNumber(30);
		assertEquals("Error", card, devCardsMarket.buyCardFromMarket(30));
		assertNull("Error", devCardsMarket.getDevCardByNumber(27));
		assertNull("Error", devCardsMarket.getDevCardByNumber(30));
	}
}