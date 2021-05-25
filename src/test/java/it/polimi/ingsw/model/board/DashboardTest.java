package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DashboardTest {

    private Dashboard dashboard = new Dashboard();
    DevCardArea one = new DevCardArea();
    DevCardArea two = new DevCardArea();
    DevCardArea three = new DevCardArea();
    DevCardArea[] devCardAreas = new DevCardArea[3];
    DevCard card1 = new DevCard();
    DevCard card2 = new DevCard();
    DevCard card3 = new DevCard();
    DevCard card4 = new DevCard();
    List<DevCard> allDevCards = new ArrayList<>();

    @Test
    public void getTotalDevCardsNum()
    {
        devCardAreas[0] = one;
        devCardAreas[1] = two;
        devCardAreas[2] = three;
        one.setLayer(3);
        two.setLayer(2);
        three.setLayer(1);
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
        one.addDevCard(card1);
        one.addDevCard(card2);
        two.addDevCard(card3);
        two.addDevCard(card4);
        devCardAreas[0] = one;
        devCardAreas[1] = two;
        devCardAreas[2] = three;
        dashboard.setDevCardAreas(devCardAreas);
        allDevCards.add(card1);
        allDevCards.add(card2);
        allDevCards.add(card3);
        allDevCards.add(card4);
        assertEquals("Error", allDevCards, dashboard.getAllDevCards());
    }
}