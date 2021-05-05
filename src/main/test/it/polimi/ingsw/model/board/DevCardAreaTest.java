package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.DevCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DevCardAreaTest {

    private DevCardArea d = new DevCardArea();

    @Before
    public void setUp() throws Exception
    {
        assertNotNull(d);
    }

    @Test
    public void checkDevCardArea1()
    {
        d.setLayer(-1);
        assertEquals("Errore", false, d.checkDevCardArea());
    }

    @Test
    public void checkDevCardArea2()
    {
        d.setLayer(4);
        assertEquals("Errore", false, d.checkDevCardArea());
    }

    @Test
    public void addDevCard()
    {
        d.setLayer(3);

        /* How import devCard's level? */

    }
}