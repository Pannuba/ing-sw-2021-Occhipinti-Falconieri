package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageTest
{
    private Storage storage = new Storage();
    private Resource a = new Resource();
    private Resource b = new Resource();
    private Resource c = new Resource();
    private Resource resourceToAdd = new Resource();
    private Shelf[] shelves = new Shelf[3];


    @Test
    public void checkShelves1()    /* Test for incorrect amount of resources */
    {
        for (int i=0; i<3; i++)
            shelves[i] = new Shelf(i+1);
        a.setQuantity(4);
        a.setResourceType(ResourceType.PURPLE);
        b.setQuantity(2);
        b.setResourceType(ResourceType.GREY);
        c.setQuantity(1);
        c.setResourceType(ResourceType.BLUE);
        shelves[0].setShelfResource(a);
        shelves[1].setShelfResource(b);
        shelves[2].setShelfResource(c);
        storage.setShelves(shelves);
        assertEquals("Error", false, storage.checkShelves());
    }

    @Test
    public void checkShelves2()    /* Test for the same type of resources in different shelves */
    {
        for (int i=0; i<3; i++)
            shelves[i] = new Shelf(i+1);
        a.setQuantity(1);
        a.setResourceType(ResourceType.PURPLE);
        b.setQuantity(1);
        b.setResourceType(ResourceType.GREY);
        c.setQuantity(3);
        c.setResourceType(ResourceType.PURPLE);
        shelves[0].setShelfResource(a);
        shelves[1].setShelfResource(b);
        shelves[2].setShelfResource(c);
        storage.setShelves(shelves);
        assertEquals("Error", false, storage.checkShelves());
    }

    @Test
    public void checkShelves3()    /* Test for entering correct parameters */
    {
        for (int i=0; i<3; i++)
            shelves[i] = new Shelf(i+1);
        a.setQuantity(1);
        a.setResourceType(ResourceType.PURPLE);
        b.setQuantity(1);
        b.setResourceType(ResourceType.GREY);
        c.setQuantity(3);
        c.setResourceType(ResourceType.YELLOW);
        shelves[0].setShelfResource(a);
        shelves[1].setShelfResource(b);
        shelves[2].setShelfResource(c);
        storage.setShelves(shelves);
        assertEquals("Error", true, storage.checkShelves());
    }

    @Test
    public void addResource() {
    }

    @Test
    public void moveResources() {
    }

    @Test
    public void getTotalResources()
    {
        for (int i=0; i<3; i++)
            shelves[i] = new Shelf(i+1);
        a.setQuantity(1);
        b.setQuantity(2);
        c.setQuantity(1);
        shelves[0].setShelfResource(a);
        shelves[1].setShelfResource(b);
        shelves[2].setShelfResource(c);
        storage.setShelves(shelves);
        assertEquals("Error", 4, storage.getTotalResources());
    }
}