package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class VaultTest {

    private Vault vault = new Vault();
    private HashMap<ResourceType, Integer> resourceAmounts = new HashMap<>();

    @Test
    public void addResource1()
    {
        vault.addResource(ResourceType.BLUE);
        vault.addResource(ResourceType.BLUE);
        resourceAmounts.put(ResourceType.BLUE,	 2);
        resourceAmounts.put(ResourceType.GREY,	 0);
        resourceAmounts.put(ResourceType.YELLOW, 0);
        resourceAmounts.put(ResourceType.PURPLE, 0);
        assertEquals("Error",resourceAmounts, vault.getResourceAmounts());
    }

    @Test
    public void addResource2()
    {
        vault.addResource(ResourceType.BLUE);
        vault.addResource(ResourceType.PURPLE);
        resourceAmounts.put(ResourceType.BLUE,	 1);
        resourceAmounts.put(ResourceType.GREY,	 0);
        resourceAmounts.put(ResourceType.YELLOW, 0);
        resourceAmounts.put(ResourceType.PURPLE, 1);
        assertEquals("Error",resourceAmounts, vault.getResourceAmounts());
    }

    @Test
    public void addResource3()
    {
        vault.addResource(ResourceType.BLUE);
        vault.addResource(ResourceType.GREY);
        vault.addResource(ResourceType.YELLOW);
        vault.addResource(ResourceType.PURPLE);
        resourceAmounts.put(ResourceType.BLUE,	 1);
        resourceAmounts.put(ResourceType.GREY,	 1);
        resourceAmounts.put(ResourceType.YELLOW, 1);
        resourceAmounts.put(ResourceType.PURPLE, 1);
        assertEquals("Error",resourceAmounts, vault.getResourceAmounts());
    }

    @Test
    public void getTotalResources()
    {
        vault.setResourceAmounts(resourceAmounts);
        resourceAmounts.put(ResourceType.BLUE,	 7);
        resourceAmounts.put(ResourceType.GREY,	10);
        resourceAmounts.put(ResourceType.YELLOW, 3);
        resourceAmounts.put(ResourceType.PURPLE, 6);
        assertEquals("Error", 26, vault.getTotalResources());
    }
}