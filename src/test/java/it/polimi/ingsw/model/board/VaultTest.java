package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import static org.junit.Assert.*;

public class VaultTest {        /* TODO: change from array to hashmap */

    private Vault a = new Vault();

    @Test
    public void addResource1()
    {
        ResourceType resource1 = ResourceType.BLUE;
        a.addResource(resource1);
        ResourceType resource2 = ResourceType.BLUE;
        a.addResource(resource2);
        int[] b = {2,0,0,0};
        //assertArrayEquals("Error", b, a.getResourceAmounts());
    }

    @Test
    public void addResource2()
    {
        ResourceType resource1 = ResourceType.BLUE;
        a.addResource(resource1);
        ResourceType resource2 = ResourceType.PURPLE;
        a.addResource(resource2);
        int[] b = {1,0,0,1};
        //assertArrayEquals("Error", b, a.getResourceAmounts());
    }

    @Test
    public void addResource3()
    {
        ResourceType resource1 = ResourceType.BLUE;
        a.addResource(resource1);
        ResourceType resource2 = ResourceType.GREY;
        a.addResource(resource2);
        ResourceType resource3 = ResourceType.YELLOW;
        a.addResource(resource3);
        ResourceType resource4 = ResourceType.PURPLE;
        a.addResource(resource4);
        int[] b = {1,1,1,1};
        //assertArrayEquals("Error", b, a.getResourceAmounts());
    }

    @Test
    public void getTotalResources()
    {
        int[] resourceAmounts = {7,10,3,6};
        //a.setResourceAmounts(resourceAmounts);
        assertEquals("Error", 26, a.getTotalResources());
    }
}