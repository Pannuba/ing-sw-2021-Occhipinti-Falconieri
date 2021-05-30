package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageTest
{
	private Storage storage = new Storage();
	private Resource r1 = new Resource();
	private Resource r2 = new Resource();
	private Resource r3 = new Resource();
	private Resource resourceToAdd = new Resource();
	private Shelf[] shelves = new Shelf[3];


	@Test
	public void checkShelves1()    /* Test for incorrect amount of resources */
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

	@Test
	public void checkShelves2()    /* Test for the same type of resources in different shelves */
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

	@Test
	public void checkShelves3()    /* Test for entering correct parameters */
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

	@Test
	public void addResource1()     /* Test for entering correct parameters */
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
		resourceToAdd.setQuantity(1);
		resourceToAdd.setResourceType(ResourceType.GREY);
		//assertTrue(storage.addResource(resourceToAdd, 2));
		assertEquals("Error", 2, shelves[1].getShelfResourceQuantity());
	}

	@Test
	public void addResource2()    /* Test for entering correct parameters */
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
		resourceToAdd.setQuantity(1);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		//assertTrue(storage.addResource(resourceToAdd, 1));
		assertEquals("Error", 1, shelves[0].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[0].getShelfResourceType());
	}

	@Test
	public void addResource3()        /* destination shelf and resource to add have different resource types */
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
		resourceToAdd.setQuantity(1);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		//assertFalse(storage.addResource(resourceToAdd, 2));
	}

	@Test
	public void addResource4()        /* not enough space on the destination shelf */
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
		resourceToAdd.setQuantity(2);
		resourceToAdd.setResourceType(ResourceType.GREY);
		//assertFalse(storage.addResource(resourceToAdd, 2));
	}

	@Test
	public void addResourceSmart1()   /* case 1, third if */
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
		resourceToAdd.setQuantity(1);
		resourceToAdd.setResourceType(ResourceType.GREY);
		//storage.addResourceSmart(resourceToAdd);
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.GREY, shelves[2].getShelfResourceType());
	}

	@Test
	public void addResourceSmart2()   /* case 2, second if */
	{
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.GREY);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage.setShelves(shelves);
		resourceToAdd.setQuantity(2);
		resourceToAdd.setResourceType(ResourceType.GREY);
		//storage.addResourceSmart(resourceToAdd);
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.GREY, shelves[2].getShelfResourceType());
	}

	@Test
	public void addResourceSmart3()   /* case 2, third if */
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
		resourceToAdd.setQuantity(2);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		//storage.addResourceSmart(resourceToAdd);
		assertEquals("Error", 2, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[2].getShelfResourceType());
	}

	@Test
	public void addResourceSmart4()   /* case 3 */
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
		resourceToAdd.setQuantity(3);
		resourceToAdd.setResourceType(ResourceType.BLUE);
		//storage.addResourceSmart(resourceToAdd);
		assertEquals("Error", 3, shelves[2].getShelfResourceQuantity());
		assertEquals("Error", ResourceType.BLUE, shelves[2].getShelfResourceType());
	}

	@Test
	public void moveResources1()     /* destination shelf is not empty */
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

	@Test
	public void moveResources2()     /* correct parameters */
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

	@Test
	public void moveResources3()     /* not enough space on destination shelf */
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