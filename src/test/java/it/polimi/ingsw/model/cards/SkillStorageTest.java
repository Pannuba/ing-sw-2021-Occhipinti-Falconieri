package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * SkillStorage test
 * @author Chiara Falconieri
 */

public class SkillStorageTest {

	private SkillStorage skillStorage = new SkillStorage();
	private Shelf shelf = new Shelf();
	private Resource resource = new Resource();

	/**
	 * Check that he added two purple resources in the additional storage
	 * addOneResource method
	 */

	@Test
	public void addOneResource()
	{
		resource.setResourceType(ResourceType.PURPLE);
		shelf.setShelfResource(resource);
		skillStorage.setAdditionalStorage(shelf);
		skillStorage.addOneResource();
		skillStorage.addOneResource();
		assertEquals("Error", 2, skillStorage.getAdditionalStorage().getShelfResourceQuantity());
		assertEquals("Error", ResourceType.PURPLE, skillStorage.getAdditionalStorage().getShelfResourceType());
	}

	/**
	 * Check that he removed two yellow resources from the additional storage
	 * removeResources method
	 */

	@Test
	public void removeResources()
	{
		resource.setResourceType(ResourceType.YELLOW);
		resource.setQuantity(2);
		shelf.setShelfResource(resource);
		skillStorage.setAdditionalStorage(shelf);
		assertEquals("Error", 2, skillStorage.removeResources(2));
		assertEquals("Error", 0, skillStorage.getAdditionalStorage().getShelfResourceQuantity());
	}
}