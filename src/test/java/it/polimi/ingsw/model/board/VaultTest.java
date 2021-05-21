package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class VaultTest {

    private Vault a = new Vault();
    private HashMap<ResourceType, Integer> resourceAmounts = new HashMap<>();

    @Test
    public void addResource1()
    {
        a.addResource(ResourceType.BLUE);
        a.addResource(ResourceType.BLUE);
        resourceAmounts.put(ResourceType.BLUE,	 2);
        resourceAmounts.put(ResourceType.GREY,	 0);
        resourceAmounts.put(ResourceType.YELLOW, 0);
        resourceAmounts.put(ResourceType.PURPLE, 0);
        assertEquals("Error",resourceAmounts, a.getResourceAmounts());
    }

    @Test
    public void addResource2()
    {
        a.addResource(ResourceType.BLUE);
        a.addResource(ResourceType.PURPLE);
        resourceAmounts.put(ResourceType.BLUE,	 1);
        resourceAmounts.put(ResourceType.GREY,	 0);
        resourceAmounts.put(ResourceType.YELLOW, 0);
        resourceAmounts.put(ResourceType.PURPLE, 1);
        assertEquals("Error",resourceAmounts, a.getResourceAmounts());
    }

    @Test
    public void addResource3()
    {
        a.addResource(ResourceType.BLUE);
        a.addResource(ResourceType.GREY);
        a.addResource(ResourceType.YELLOW);
        a.addResource(ResourceType.PURPLE);
        resourceAmounts.put(ResourceType.BLUE,	 1);
        resourceAmounts.put(ResourceType.GREY,	 1);
        resourceAmounts.put(ResourceType.YELLOW, 1);
        resourceAmounts.put(ResourceType.PURPLE, 1);
        assertEquals("Error",resourceAmounts, a.getResourceAmounts());
    }

    @Test
    public void getTotalResources()
    {
        a.setResourceAmounts(resourceAmounts);
        resourceAmounts.put(ResourceType.BLUE,	 7);
        resourceAmounts.put(ResourceType.GREY,	10);
        resourceAmounts.put(ResourceType.YELLOW, 3);
        resourceAmounts.put(ResourceType.PURPLE, 6);
        assertEquals("Error", 26, a.getTotalResources());
    }
}