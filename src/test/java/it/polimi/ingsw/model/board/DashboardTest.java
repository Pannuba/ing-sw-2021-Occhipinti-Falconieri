package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DashboardTest {

	private Dashboard dashboard = new Dashboard();
	DevCardArea devCardArea1 = new DevCardArea();
	DevCardArea devCardArea2 = new DevCardArea();
	DevCardArea devCardArea3 = new DevCardArea();
	DevCardArea[] devCardAreas = new DevCardArea[3];
	DevCard card1 = new DevCard();
	DevCard card2 = new DevCard();
	DevCard card3 = new DevCard();
	DevCard card4 = new DevCard();
	List<DevCard> allDevCards = new ArrayList<>();

	@Test
	public void getTotalDevCardsNum()
	{
		devCardAreas[0] = devCardArea1;
		devCardAreas[1] = devCardArea2;
		devCardAreas[2] = devCardArea3;
		devCardArea1.setLayer(3);
		devCardArea2.setLayer(2);
		devCardArea3.setLayer(1);
		dashboard.setDevCardAreas(devCardAreas);
		assertEquals("Error", 6, dashboard.getTotalDevCardsNum());
	}

	@Test
	public void getAllDevCards()
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
	}
}