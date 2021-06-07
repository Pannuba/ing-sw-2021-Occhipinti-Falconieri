package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillStorage;
import it.polimi.ingsw.util.XML_Serialization;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

	private Player p1;
	private Dashboard dashboard;
	private Storage storage;
	private Shelf[] shelves = new Shelf[3];
	private Vault vault;
	private HashMap<ResourceType, Integer> resourceAmounts;
	private Resource r1 = new Resource();
	private Resource r2 = new Resource();
	private Resource r3 = new Resource();
	private Resource r4 = new Resource();
	private Resource r5 = new Resource();
	private Resource r6 = new Resource();
	private List<Player> players = new ArrayList<>();
	private Model model;
	private Controller controller;
	private List<Resource> requirements;

	@Test
	void checkResourceAmounts1()
	{
		p1 = new Player("pippo");
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 4);
		resourceAmounts.put(ResourceType.GREY,	 8);
		resourceAmounts.put(ResourceType.YELLOW, 2);
		resourceAmounts.put(ResourceType.PURPLE, 10);
		vault.setResourceAmounts(resourceAmounts);
		dashboard = new Dashboard();
		dashboard.setVault(vault);
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage = new Storage();
		storage.setShelves(shelves);
		dashboard.setStorage(storage);
		p1.setDashboard(dashboard);
		players.add(p1);
		model = new Model(players);
		controller = new Controller(model);
		r4.setQuantity(5);
		r4.setResourceType(ResourceType.YELLOW);
		requirements = new ArrayList<>();
		requirements.add(r4);
		assertFalse(controller.checkResourceAmounts(players.get(0), requirements));
	}

	@Test
	void checkResourceAmounts2()
	{
		p1 = new Player("pippo");
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 0);
		resourceAmounts.put(ResourceType.GREY,	 2);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 1);
		vault.setResourceAmounts(resourceAmounts);
		dashboard = new Dashboard();
		dashboard.setVault(vault);
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage = new Storage();
		storage.setShelves(shelves);
		dashboard.setStorage(storage);
		p1.setDashboard(dashboard);
		players.add(p1);
		model = new Model(players);
		controller = new Controller(model);
		r4.setQuantity(1);
		r4.setResourceType(ResourceType.YELLOW);
		r5.setQuantity(1);
		r5.setResourceType(ResourceType.BLUE);
		r6.setQuantity(1);
		r6.setResourceType(ResourceType.GREY);
		requirements = new ArrayList<>();
		requirements.add(r4);
		requirements.add(r5);
		requirements.add(r6);
		assertTrue(controller.checkResourceAmounts(players.get(0), requirements));
	}

	@Test
	void checkResourceAmounts3() {
		p1 = new Player("pippo");
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE, 0);
		resourceAmounts.put(ResourceType.GREY, 2);
		resourceAmounts.put(ResourceType.YELLOW, 0);
		resourceAmounts.put(ResourceType.PURPLE, 1);
		vault.setResourceAmounts(resourceAmounts);
		dashboard = new Dashboard();
		dashboard.setVault(vault);
		for (int i = 0; i < 3; i++)
			shelves[i] = new Shelf(i + 1);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(2);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage = new Storage();
		storage.setShelves(shelves);
		dashboard.setStorage(storage);
		p1.setDashboard(dashboard);
		players.add(p1);
		model = new Model(players);
		controller = new Controller(model);
		r4.setQuantity(3);
		r4.setResourceType(ResourceType.PURPLE);
		r5.setQuantity(2);
		r5.setResourceType(ResourceType.BLUE);
		requirements = new ArrayList<>();
		requirements.add(r4);
		requirements.add(r5);
		assertFalse(controller.checkResourceAmounts(players.get(0), requirements));
	}
}