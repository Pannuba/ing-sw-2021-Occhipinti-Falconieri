package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.DevCardsMarket;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Action devCard area tests
 * @author Chiara Falconieri
 */

public class ActionDevCardTest {

	private DevCardsMarket devCardsMarket;
	private List<DevCard> devCards;
	private List<DevCard> devCards2;
	private ActionDevCard actionDevCard;

	/**
	 * Check that the action token has removed the green devCards
	 * doAction method
	 */

	@Test
	public void doAction1()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		devCards = new ArrayList<>();
		devCards2 = new ArrayList<>();
		devCards = devCardsMarket.getDevCardStacks().get(0);
		devCards2 = devCardsMarket.getDevCardStacks().get(1);
		devCards.remove(0);
		devCards.remove(1);
		actionDevCard = new ActionDevCard(DevCardColor.GREEN, devCardsMarket);
		actionDevCard.doAction();
		assertEquals("Error", devCards, devCardsMarket.getDevCardStacks().get(0));
		devCards2.remove(0);
		devCards2.remove(1);
		actionDevCard.doAction();
		actionDevCard.doAction();
		assertEquals("Error", devCards2, devCardsMarket.getDevCardStacks().get(1));
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(0).isEmpty());
	}

	/**
	 * Check that the action token has removed the green devCards and the devCard stacks is empty
	 * doAction method
	 */

	@Test
	public void doAction2()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		actionDevCard = new ActionDevCard(DevCardColor.GREEN, devCardsMarket);
		actionDevCard.doAction();
		actionDevCard.doAction();
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(0).isEmpty());
	}

	/**
	 * Check that the action token has removed the blu devCards
	 * doAction method
	 */

	@Test
	public void doAction3()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		devCards = new ArrayList<>();
		devCards2 = new ArrayList<>();
		devCards = devCardsMarket.getDevCardStacks().get(3);
		devCards2 = devCardsMarket.getDevCardStacks().get(4);
		devCards.remove(0);
		devCards.remove(1);
		actionDevCard = new ActionDevCard(DevCardColor.BLUE, devCardsMarket);
		actionDevCard.doAction();
		assertEquals("Error", devCards, devCardsMarket.getDevCardStacks().get(3));
		devCards2.remove(0);
		devCards2.remove(1);
		actionDevCard.doAction();
		actionDevCard.doAction();
		assertEquals("Error", devCards2, devCardsMarket.getDevCardStacks().get(4));
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(3).isEmpty());
	}

	/**
	 * Check that the action token has removed the yellow devCards and the devCard stacks is empty
	 * doAction method
	 */

	@Test
	public void doAction4()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		actionDevCard = new ActionDevCard(DevCardColor.YELLOW, devCardsMarket);
		actionDevCard.doAction();
		actionDevCard.doAction();
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(9).isEmpty());
	}

	/**
	 * Check that the action token has removed the purple devCards and all the devCard stacks (purple) is empty
	 * doAction method
	 */

	@Test
	public void doAction5()
	{
		devCardsMarket = new DevCardsMarket();
		devCardsMarket.create();
		actionDevCard = new ActionDevCard(DevCardColor.PURPLE, devCardsMarket);
		actionDevCard.doAction();
		actionDevCard.doAction();
		actionDevCard.doAction();
		actionDevCard.doAction();
		actionDevCard.doAction();
		actionDevCard.doAction();
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(6).isEmpty());
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(7).isEmpty());
		assertTrue("Error", devCardsMarket.getDevCardStacks().get(8).isEmpty());
	}
}