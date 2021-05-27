package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DashboardTest {

	private Dashboard dashboard = new Dashboard();
	private DevCardArea devCardArea1 = new DevCardArea();
	private DevCardArea devCardArea2 = new DevCardArea();
	private DevCardArea devCardArea3 = new DevCardArea();
	private DevCardArea[] devCardAreas = new DevCardArea[3];
	private DevCard card1 = new DevCard();
	private DevCard card2 = new DevCard();
	private DevCard card3 = new DevCard();
	private DevCard card4 = new DevCard();
	private List<DevCard> allDevCards = new ArrayList<>();

	@Test
	public void getAllDevCards()  /* check also getAllDevCardsNum method */
	{
		card1.setLevel(1);
		card2.setLevel(2);
		card3.setLevel(1);
		card4.setLevel(2);
		devCardArea1.addDevCard(card1);
		devCardArea1.addDevCard(card2);
		devCardArea2.addDevCard(card3);
		devCardArea2.addDevCard(card4);
		devCardAreas[0] = devCardArea1;
		devCardAreas[1] = devCardArea2;
		devCardAreas[2] = devCardArea3;
		dashboard.setDevCardAreas(devCardAreas);
		allDevCards.add(card1);
		allDevCards.add(card2);
		allDevCards.add(card3);
		allDevCards.add(card4);
		assertEquals("Error", allDevCards, dashboard.getAllDevCards());
		assertEquals("Error", 4, dashboard.getTotalDevCardsNum());
	}
}