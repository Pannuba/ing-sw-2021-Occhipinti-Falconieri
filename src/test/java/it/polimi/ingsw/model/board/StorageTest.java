package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Storage tests
 * @author Chiara Falconieri
 */

public class StorageTest
{
	private Storage storage = new Storage();
	private Resource r1 = new Resource();
	private Resource r2 = new Resource();
	private Resource r3 = new Resource();
	private Resource resourceToAdd = new Resource();
	private Resource resourceToRemove = new Resource();
	private Shelf[] shelves = new Shelf[3];

	/**
	 *  Test for incorrect amount of resources
	 *  checkShelves method
	 */

	@Test
	public void checkShelves1()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(4);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(1);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertFalse("Error", storage.checkShelves());
	}

	/**
	 *  Test for the same type of resources in different shelves
	 *  checkShelves method
	 */

	@Test
	public void checkShelves2()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.PURPLE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertFalse("Error", storage.checkShelves());
	}

	/**
	 * Test for entering correct parameters
	 * checkShelves method
	 */

	@Test
	public void checkShelves3()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertTrue("Error", storage.checkShelves());
	}

	/**
	 * Test for entering correct parameters
	 * addResource method
	 */

	@Test
	public void addResource1()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.GREY);
		assertTrue(storage.addResource(resourceToAdd.getResourceType(), 2));
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
	}

	/**
	 * Test for entering correct parameters
	 * addResource method
	 */

	@Test
	public void addResource2()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		assertTrue(storage.addResource(resourceToAdd.getResourceType(), 1));
		assertEquals("Error", 1, shelves[0].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[0].getShelfResourceType());
	}

	/**
	 * Destination shelf and resource to add have different resource types
	 * addResource method
	 */

	@Test
	public void addResource3()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		assertFalse(storage.addResource(resourceToAdd.getResourceType(), 2));
	}

	/**
	 * Not enough space on the destination shelf
	 * addResource method
	 */

	@Test
	public void addResource4()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.YELLOW);
		assertFalse(storage.addResource(resourceToAdd.getResourceType(), 3));
		resourceToAdd.setResourceType(ResourceType.PURPLE);
		assertFalse(storage.addResource(resourceToAdd.getResourceType(), 1));
	}

	/**
	 * Third shelf is empty and first shelf have the same type of resourceToAdd
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart1()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.GREY);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.PURPLE);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 2, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.PURPLE, shelves[2].getShelfResourceType());
	}

	/**
	 * Third shelf is empty and second shelf have the same type of resourceToAdd
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart2()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.GREY);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.GREY);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.GREY, shelves[2].getShelfResourceType());
	}

	/**
	 * Second shelf is empty and first shelf have the same type of resourceToAdd
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart3()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.PURPLE);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.PURPLE, shelves[1].getShelfResourceType());
	}

	/**
	 * Second shelf is not full and have the same type of resourceToAdd
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart4()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.GREY);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.GREY, shelves[1].getShelfResourceType());
	}

	/**
	 * All shelves have resources, swap first and second shelf
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart5()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.PURPLE);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.PURPLE, shelves[1].getShelfResourceType());
		assertEquals("Error", 1, shelves[0].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.GREY, shelves[0].getShelfResourceType());
	}

	/**
	 * All shelves have resources, swap first and third shelf
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart6()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(1);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.PURPLE);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 2, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.PURPLE, shelves[2].getShelfResourceType());
		assertEquals("Error", 1, shelves[0].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[0].getShelfResourceType());
	}

	/**
	 * All shelves have resources, swap first and second shelf
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart7()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.YELLOW);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.YELLOW, shelves[2].getShelfResourceType());
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[1].getShelfResourceType());
	}

	/**
	 * All shelves have resources, swap second and third shelf and change quantity
	 * addResourceSmart method
	 */

	@Test
	public void addResourceSmart8()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(1);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setResourceType(ResourceType.YELLOW);
		storage.addResourceSmart(resourceToAdd.getResourceType());
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.YELLOW, shelves[2].getShelfResourceType());
		assertEquals("Error", 1, shelves[1].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[1].getShelfResourceType());
	}

	/**
	 * Remove from first shelf
	 * removeResource method
	 */

	@Test
	public void removeResource1()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToRemove.setQuantity(1);
		resourceToRemove.setResourceType(ResourceType.PURPLE);
		assertEquals("Error", 1, storage.removeResource(resourceToRemove));
		assertNull("Error", shelves[0].getShelfResourceType());
		assertEquals("Error", 0, shelves[0].getShelfResourceQuantity());
	}

	/**
	 * Remove from second shelf
	 * removeResource method
	 */

	@Test
	public void removeResource2()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToRemove.setQuantity(1);
		resourceToRemove.setResourceType(ResourceType.GREY);
		assertEquals("Error", 1, storage.removeResource(resourceToRemove));
		assertEquals("Error", ResourceType.GREY, shelves[1].getShelfResourceType());
		assertEquals("Error", 1, shelves[1].getShelfResourceQuantity());
	}

	/**
	 * Remove from third shelf
	 * removeResource method
	 */

	@Test
	public void removeResource3()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToRemove.setQuantity(3);
		resourceToRemove.setResourceType(ResourceType.YELLOW);
		assertEquals("Error", 3, storage.removeResource(resourceToRemove));
		assertNull("Error", shelves[2].getShelfResourceType());
		assertEquals("Error", 0, shelves[2].getShelfResourceQuantity());
	}

	/**
	 * Destination shelf is not empty
	 * moveResources method
	 */

	@Test
	public void moveResources1()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertFalse(storage.moveResources(1,2));
	}

	/**
	 * Correct parameters
	 * moveResources method
	 */

	@Test
	public void moveResources2()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertTrue(storage.moveResources(1,2));
		assertEquals("Error", 0, shelves[0].getShelfResourceQuantity());
		assertEquals("Error", 1, shelves[1].getShelfResourceQuantity());
		assertNull("Error", shelves[0].getShelfResourceType());
		assertEquals("Error", ResourceType.PURPLE, shelves[1].getShelfResourceType());
	}

	/**
	 * Not enough space on destination shelf
	 * moveResources method
	 */

	@Test
	public void moveResources3()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertFalse(storage.moveResources(3,2));
	}

	/**
	 * findResourceByType method
	 */

	@Test
	public void findResourceByType()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.BLUE);
		r2.setQuantity(2);
		r2.setResourceType(ResourceType.GREY);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.YELLOW);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertEquals("Error", 1, storage.findResourceByType(ResourceType.BLUE));
		assertEquals("Error", 2, storage.findResourceByType(ResourceType.GREY));
		assertEquals("Error", 2, storage.findResourceByType(ResourceType.YELLOW));
		assertEquals("Error", 0, storage.findResourceByType(ResourceType.PURPLE));
	}

	/**
	 * convertIndexToShelf
	 */

	@Test
	public void convertIndexToShelf()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		storage.setShelves(shelves);
		assertEquals("Error", shelves[0], storage.convertIndexToShelf(1));
		assertEquals("Error", shelves[1], storage.convertIndexToShelf(2));
		assertEquals("Error", shelves[2], storage.convertIndexToShelf(3));
	}

	/**
	 * Check that returns the correct number of total resources of storage
	 * getTotalResources method
	 */

	@Test
	public void getTotalResources()
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r2.setQuantity(2);
		r3.setQuantity(1);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		assertEquals("Error", 4, storage.getTotalResources());
	}
}