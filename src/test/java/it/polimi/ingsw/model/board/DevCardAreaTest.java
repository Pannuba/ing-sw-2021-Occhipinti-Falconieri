package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DevCardAreaTest {

	private DevCardArea devCardArea = new DevCardArea();
	private DevCard card1 = new DevCard();
	private DevCard card2 = new DevCard();
	private DevCard card3 = new DevCard();
	private List<DevCard> devCards = new ArrayList<>();

	@Before
	public void setUp() throws Exception
	{
		assertNotNull(devCardArea);
	}

	@Test
	public void checkDevCardArea1()
	{
		devCardArea.setLayer(-1);
		assertEquals("Error", false, devCardArea.checkDevCardArea());
	}

	@Test
	public void checkDevCardArea2()
	{
		devCardArea.setLayer(4);
		assertEquals("Error", false, devCardArea.checkDevCardArea());
	}

	@Test
	public void checkDevCardArea3()
	{
		devCardArea.setLayer(2);
		assertEquals("Error", true, devCardArea.checkDevCardArea());
	}

	@Test
	public void addDevCard1()    /* Card is correct added */
	{
		devCards.add(card1);
		devCards.add(card2);
		card1.setLevel(1);
		card2.setLevel(2);
		assertTrue(devCardArea.addDevCard(card1));
		assertTrue(devCardArea.addDevCard(card2));
		assertEquals("Error", 2, devCardArea.getLayer());
		assertFalse(devCardArea.isEmpty());
		assertEquals("Error", devCards, devCardArea.getDevCards());   /* check that the cards have really been added  */
	}

	@Test
	public void addDevCard2()  /* Dev card area is full */
	{
		devCardArea.setLayer(3);
		card1.setLevel(2);
		assertFalse(devCardArea.addDevCard(card1));
	}

	@Test
	public void addDevCard()   /* Card to be added is not compatible with current dev card area */
	{
		devCardArea.setLayer(2);
		card1.setLevel(2);
		assertFalse(devCardArea.addDevCard(card1));
	}

	@Test
	public void getTopDevCard1()
	{
		devCards.add(card1);
		devCards.add(card2);
		card1.setLevel(1);
		card2.setLevel(2);
		devCardArea.addDevCard(card1);
		devCardArea.addDevCard(card2);
		assertEquals("Error", card2, devCardArea.getTopDevCard());
	}

	@Test
	public void getTopDevCard2()
	{
		devCards.add(card1);
		devCards.add(card2);
		devCards.add(card3);
		card1.setLevel(1);
		card2.setLevel(2);
		card3.setLevel(3);
		devCardArea.addDevCard(card1);
		devCardArea.addDevCard(card2);
		devCardArea.addDevCard(card3);
		assertEquals("Error", card3, devCardArea.getTopDevCard());
	}
}
