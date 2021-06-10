package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Vault tests
 * @author Chiara Falconieri
 */

public class VaultTest {

	private Vault vault;
	private HashMap<ResourceType, Integer> resourceAmounts;
	private HashMap<ResourceType, Integer> resourceAmounts2;
	private Resource resourceToRemove;

	/**
	 * Add two blue resources into an empty vault
	 * addResource method
	 */

	@Test
	public void addResource1()
	{
		vault = new Vault();
		vault.addResource(ResourceType.BLUE);
		vault.addResource(ResourceType.BLUE);
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 2);
		resourceAmounts.put(ResourceType.GREY,	 0);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 0);
		assertEquals("Error",resourceAmounts, vault.getResourceAmounts());
	}

	/**
	 * Add one blue and one purple resources into an empty vault
	 * addResource method
	 */

	@Test
	public void addResource2()
	{
		vault = new Vault();
		vault.addResource(ResourceType.BLUE);
		vault.addResource(ResourceType.PURPLE);
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 1);
		resourceAmounts.put(ResourceType.GREY,	 0);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 1);
		assertEquals("Error",resourceAmounts, vault.getResourceAmounts());
	}

	/**
	 * Add one blue, one grey, one yellow and one purple resources
	 * addResource method
	 */

	@Test
	public void addResource3()
	{
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 1);
		resourceAmounts.put(ResourceType.GREY,	 2);
		resourceAmounts.put(ResourceType.YELLOW, 1);
		resourceAmounts.put(ResourceType.PURPLE, 15);
		vault.setResourceAmounts(resourceAmounts);
		vault.addResource(ResourceType.BLUE);
		vault.addResource(ResourceType.GREY);
		vault.addResource(ResourceType.YELLOW);
		vault.addResource(ResourceType.PURPLE);
		resourceAmounts2 = new HashMap<>();
		resourceAmounts2.put(ResourceType.BLUE,	 2);
		resourceAmounts2.put(ResourceType.GREY,	 3);
		resourceAmounts2.put(ResourceType.YELLOW, 2);
		resourceAmounts2.put(ResourceType.PURPLE, 16);
		assertEquals("Error",resourceAmounts2, vault.getResourceAmounts());
	}

	/**
	 * Remove five purple resources
	 * removeResource method
	 */

	@Test
	public void removeResource1()
	{
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 10);
		resourceAmounts.put(ResourceType.GREY,	 5);
		resourceAmounts.put(ResourceType.YELLOW, 2);
		resourceAmounts.put(ResourceType.PURPLE, 15);
		vault.setResourceAmounts(resourceAmounts);
		resourceToRemove = new Resource();
		resourceToRemove.setResourceType(ResourceType.PURPLE);
		resourceToRemove.setQuantity(5);
		resourceAmounts2 = new HashMap<>();
		resourceAmounts2.put(ResourceType.BLUE,	 10);
		resourceAmounts2.put(ResourceType.GREY,	 5);
		resourceAmounts2.put(ResourceType.YELLOW, 2);
		resourceAmounts2.put(ResourceType.PURPLE, 10);
		assertEquals("Error", 5, vault.removeResource(resourceToRemove));
		assertEquals("Error", resourceAmounts2, vault.getResourceAmounts());
	}

	/**
	 * Remove three yellow resources
	 * removeResource method
	 */

	@Test
	public void removeResource2()
	{
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 10);
		resourceAmounts.put(ResourceType.GREY,	 5);
		resourceAmounts.put(ResourceType.YELLOW, 2);
		resourceAmounts.put(ResourceType.PURPLE, 15);
		vault.setResourceAmounts(resourceAmounts);
		resourceToRemove = new Resource();
		resourceToRemove.setResourceType(ResourceType.YELLOW);
		resourceToRemove.setQuantity(3);
		resourceAmounts2 = new HashMap<>();
		resourceAmounts2.put(ResourceType.BLUE,	 10);
		resourceAmounts2.put(ResourceType.GREY,	 5);
		resourceAmounts2.put(ResourceType.YELLOW, 0);
		resourceAmounts2.put(ResourceType.PURPLE, 15);
		assertEquals("Error", 2, vault.removeResource(resourceToRemove));
		assertEquals("Error", resourceAmounts2, vault.getResourceAmounts());
	}

	/**
	 * Check that returns the correct number of total resources of vault
	 * getTotalResources method
	 */

	@Test
	public void getTotalResources()
	{
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 7);
		resourceAmounts.put(ResourceType.GREY,	10);
		resourceAmounts.put(ResourceType.YELLOW, 3);
		resourceAmounts.put(ResourceType.PURPLE, 6);
		vault.setResourceAmounts(resourceAmounts);
		assertEquals("Error", 26, vault.getTotalResources());
	}
}