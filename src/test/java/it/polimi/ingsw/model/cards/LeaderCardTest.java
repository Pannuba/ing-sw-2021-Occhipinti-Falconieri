package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.util.XML_Serialization;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderCardTest {

	private Player p1;
	private Dashboard dashboard;
	private DevCard devCard1;
	private DevCard devCard2;
	private DevCard devCard3;
	private List<LeaderCard> leaderCards;
	private Storage storage;
	private Shelf[] shelves = new Shelf[3];
	private Vault vault;
	private HashMap<ResourceType, Integer> resourceAmounts;
	private Resource r1 = new Resource();
	private Resource r2 = new Resource();
	private Resource r3 = new Resource();

	@Test
	public void checkRequirements1()			/* SkillDiscount -> true */
	{
		p1 = new Player("pippo");
		dashboard = new Dashboard();
		devCard1 = new DevCard();
		devCard2 = new DevCard();
		try
		{
			devCard1 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard1.xml"));
			devCard2 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard8.xml"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		dashboard.addDevCardToArea(devCard1, 0);
		dashboard.addDevCardToArea(devCard2, 1);
		leaderCards = new ArrayList<>();
		try
		{
			leaderCards.add((LeaderCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/leadercards/leadercard1.xml")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		p1.setLeaderCards(leaderCards);
		p1.setDashboard(dashboard);
		assertTrue(leaderCards.get(0).checkRequirements(p1.getDashboard()));
	}

	@Test
	public void checkRequirements2()			/* SkillMarble -> true */
	{
		p1 = new Player("pippo");
		dashboard = new Dashboard();
		devCard1 = new DevCard();
		devCard2 = new DevCard();
		devCard3 = new DevCard();
		try
		{
			devCard1 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard1.xml"));
			devCard2 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard2.xml"));
			devCard3 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard17.xml"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		dashboard.addDevCardToArea(devCard1, 0);
		dashboard.addDevCardToArea(devCard2, 1);
		dashboard.addDevCardToArea(devCard3, 1);
		leaderCards = new ArrayList<>();
		try
		{
			leaderCards.add((LeaderCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/leadercards/leadercard10.xml")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		p1.setLeaderCards(leaderCards);
		p1.setDashboard(dashboard);
		assertTrue(leaderCards.get(0).checkRequirements(p1.getDashboard()));
	}

	@Test
	public void checkRequirements3()		/* SkillProduction -> false */
	{
		p1 = new Player("pippo");
		dashboard = new Dashboard();
		devCard1 = new DevCard();
		devCard2 = new DevCard();
		devCard3 = new DevCard();
		try
		{
			devCard1 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard1.xml"));
			devCard2 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard24.xml"));
			devCard3 = (DevCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/devcards/devcard41.xml"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		dashboard.addDevCardToArea(devCard1, 0);
		dashboard.addDevCardToArea(devCard2, 0);
		dashboard.addDevCardToArea(devCard3, 0);
		leaderCards = new ArrayList<>();
		try
		{
			leaderCards.add((LeaderCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/leadercards/leadercard14.xml")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		p1.setLeaderCards(leaderCards);
		p1.setDashboard(dashboard);
		assertFalse(leaderCards.get(0).checkRequirements(p1.getDashboard()));
	}

	@Test
	public void checkRequirements4()			/* SkillStorage -> false */
	{
		p1 = new Player("pippo");
		vault = new Vault();
		resourceAmounts = new HashMap<>();
		resourceAmounts.put(ResourceType.BLUE,	 10);
		resourceAmounts.put(ResourceType.GREY,	 5);
		resourceAmounts.put(ResourceType.YELLOW, 2);
		resourceAmounts.put(ResourceType.PURPLE, 15);
		vault.setResourceAmounts(resourceAmounts);
		dashboard = new Dashboard();
		dashboard.setVault(vault);
		for (int i=0; i<3; i++)
			shelves[i] = new Shelf(i+1);
		r1.setQuantity(1);
		r1.setResourceType(ResourceType.PURPLE);
		r2.setQuantity(1);
		r2.setResourceType(ResourceType.YELLOW);
		r3.setQuantity(3);
		r3.setResourceType(ResourceType.BLUE);
		shelves[0].setShelfResource(r1);
		shelves[1].setShelfResource(r2);
		shelves[2].setShelfResource(r3);
		storage = new Storage();
		storage.setShelves(shelves);
		dashboard.setStorage(storage);
		leaderCards = new ArrayList<>();
		try
		{
			leaderCards.add((LeaderCard) XML_Serialization.deserialize(getClass().getResourceAsStream("/xml/leadercards/leadercard5.xml")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		p1.setLeaderCards(leaderCards);
		p1.setDashboard(dashboard);
		assertFalse(leaderCards.get(0).checkRequirements(p1.getDashboard()));
	}
}