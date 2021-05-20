package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DevCardAreaTest {

    private DevCardArea d = new DevCardArea();
    private DevCard card = new DevCard();

    @Before
    public void setUp() throws Exception
    {
        assertNotNull(d);
    }

    @Test
    public void checkDevCardArea1()
    {
        d.setLayer(-1);
        assertEquals("Error", false, d.checkDevCardArea());
    }

    @Test
    public void checkDevCardArea2()
    {
        d.setLayer(4);
        assertEquals("Error", false, d.checkDevCardArea());
    }

    @Test
    public void addDevCard1()    /* Card is correct added */
    {
        d.setLayer(1);
        card.setLevel(2);
        assertTrue(d.addDevCard(card));
        assertEquals("Error", 2, d.getLayer());
        assertFalse(d.isEmpty());
    }

    @Test
    public void addDevCard2()  /* Dev card area is full */
    {
        d.setLayer(3);
        card.setLevel(2);
        assertFalse(d.addDevCard(card));
        assertFalse(d.isEmpty());
    }

    @Test
    public void addDevCard()   /* Card to be added is not compatible with current dev card area */
    {
        d.setLayer(2);
        card.setLevel(2);
        assertFalse(d.addDevCard(card));
        assertFalse(d.isEmpty());
    }
}
