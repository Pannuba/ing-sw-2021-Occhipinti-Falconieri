package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PopeToken;
import it.polimi.ingsw.util.XML_Serialization;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {

	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private List<Player> players;
	private Model model;
	private Track track;
	private HashMap<Integer, Integer> redPawns;
	private Dashboard dashboard;
	private DevCardArea devCardArea1 = new DevCardArea();
	private DevCardArea devCardArea2 = new DevCardArea();
	private DevCardArea devCardArea3 = new DevCardArea();
	private DevCardArea[] devCardAreas = new DevCardArea[3];
	private DevCard card1 = new DevCard();
	private DevCard card2 = new DevCard();
	private DevCard card3 = new DevCard();
	private DevCard card4 = new DevCard();
	private DevCard card5 = new DevCard();
	private DevCard card6 = new DevCard();
	private DevCard card7 = new DevCard();
	private List<LeaderCard> leaderCards;
	private Vault vault;
	private HashMap<ResourceType, Integer> resourceAmounts = new HashMap<>();
	private Storage storage;
	private Resource r1 = new Resource();
	private Resource r2 = new Resource();
	private Resource r3 = new Resource();
	private Resource resourceToAdd = new Resource();
	private Shelf[] shelves = new Shelf[3];
	private final PopeToken[] popeTokens = new PopeToken[3];

	@Test
	public void vaticanReport1()		/* First pope box has been reached */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		model = new Model(players);
		model.getPlayers().get(0).setId(0);
		model.getPlayers().get(1).setId(1);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 2);
		redPawns.put(players.get(1).getId(), 8);
		track.setRedPawns(redPawns);
		model.setTrack(track);
		model.vaticanReport(8);
		assertTrue("Error", track.getPopeTokens()[0].isDiscarded());
		assertFalse("Error", players.get(0).getPopeTokens()[0].isActive());
		assertTrue("Error", players.get(1).getPopeTokens()[0].isActive());
	}

	@Test
	public void vaticanReport2()		/* First pope box has been reached */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p3 = new Player("paperino");
		p4 = new Player("topolino");
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		model = new Model(players);
		model.getPlayers().get(0).setId(0);
		model.getPlayers().get(1).setId(1);
		model.getPlayers().get(2).setId(2);
		model.getPlayers().get(3).setId(3);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 5);
		redPawns.put(players.get(1).getId(), 8);
		redPawns.put(players.get(2).getId(), 2);
		redPawns.put(players.get(3).getId(), 3);
		track.setRedPawns(redPawns);
		model.setTrack(track);
		model.vaticanReport(8);
		assertTrue("Error", track.getPopeTokens()[0].isDiscarded());
		assertTrue("Error", players.get(0).getPopeTokens()[0].isActive());
		assertTrue("Error", players.get(1).getPopeTokens()[0].isActive());
		assertFalse("Error", players.get(2).getPopeTokens()[0].isActive());
		assertFalse("Error", players.get(3).getPopeTokens()[0].isActive());
	}

	@Test
	public void vaticanReport3()		/* Second pope box has been reached */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		model = new Model(players);
		model.getPlayers().get(0).setId(0);
		model.getPlayers().get(1).setId(1);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 16);
		redPawns.put(players.get(1).getId(), 13);
		track.setRedPawns(redPawns);
		model.setTrack(track);
		model.vaticanReport(16);
		assertTrue("Error", track.getPopeTokens()[1].isDiscarded());
		assertTrue("Error", players.get(0).getPopeTokens()[1].isActive());
		assertTrue("Error", players.get(1).getPopeTokens()[1].isActive());
	}

	@Test
	public void vaticanReport4()		/* Third pope box has been reached */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		model = new Model(players);
		model.getPlayers().get(0).setId(0);
		model.getPlayers().get(1).setId(1);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 16);
		redPawns.put(players.get(1).getId(), 24);
		track.setRedPawns(redPawns);
		model.setTrack(track);
		model.vaticanReport(24);
		assertTrue("Error", track.getPopeTokens()[2].isDiscarded());
		assertFalse("Error", players.get(0).getPopeTokens()[2].isActive());
		assertTrue("Error", players.get(1).getPopeTokens()[2].isActive());
	}

	@Test
	public void isMatchOver1()   /* match over: track */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 2);
		redPawns.put(players.get(1).getId(), 24);
		track.setRedPawns(redPawns);
		model = new Model(players);
		model.setTrack(track);
		assertTrue("Error", model.isMatchOver());
	}

	@Test
	public void isMatchOver2()    /*  match over: a player has 7 devCards */
	{
		card1.setLevel(1);
		card2.setLevel(2);
		card3.setLevel(1);
		card4.setLevel(2);
		card5.setLevel(1);
		card6.setLevel(2);
		card7.setLevel(3);
		devCardArea1.addDevCard(card1);
		devCardArea1.addDevCard(card2);
		devCardArea2.addDevCard(card3);
		devCardArea2.addDevCard(card4);
		devCardArea3.addDevCard(card5);
		devCardArea3.addDevCard(card6);
		devCardArea3.addDevCard(card7);
		devCardAreas[0] = devCardArea1;
		devCardAreas[1] = devCardArea2;
		devCardAreas[2] = devCardArea3;
		dashboard = new Dashboard();
		dashboard.setDevCardAreas(devCardAreas);
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		p1.setDashboard(dashboard);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 2);
		redPawns.put(players.get(1).getId(), 16);
		track.setRedPawns(redPawns);
		model = new Model(players);
		model.setTrack(track);
		assertEquals("Error", 7, p1.getDashboard().getTotalDevCardsNum());
		assertTrue("Error", model.isMatchOver());
	}

	@Test
	public void isMatchOver3()   /* no match over */
	{
		card1.setLevel(1);
		card2.setLevel(2);
		card3.setLevel(1);
		card4.setLevel(2);
		devCardArea1.addDevCard(card1);
		devCardArea1.addDevCard(card2);
		devCardArea2.addDevCard(card3);
		devCardArea2.addDevCard(card4);
		devCardAreas[0] = devCardArea1;
		devCardAreas[1] = devCardArea2;
		devCardAreas[2] = devCardArea3;
		dashboard = new Dashboard();
		dashboard.setDevCardAreas(devCardAreas);
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		p1.setDashboard(dashboard);
		players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 5);
		redPawns.put(players.get(1).getId(), 16);
		track.setRedPawns(redPawns);
		model = new Model(players);
		model.setTrack(track);
		assertFalse("Error", model.isMatchOver());
	}

	@Test
	public void calculatePoints()
	{
		leaderCards = new ArrayList<>();

		try
		{
			leaderCards.add((LeaderCard) XML_Serialization.deserialize("src/main/resources/xml/leadercards/leadercard1.xml"));
			leaderCards.add((LeaderCard) XML_Serialization.deserialize("src/main/resources/xml/leadercards/leadercard8.xml"));
			card1 = (DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard5.xml");
			card2 = (DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard30.xml");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		leaderCards.get(0).setActive(true);
		leaderCards.get(1).setActive(true);

		dashboard = new Dashboard();
		devCardArea1.addDevCard(card1);
		devCardArea1.addDevCard(card2);
		devCardAreas[0] = devCardArea1;
		devCardAreas[1] = devCardArea2;
		devCardAreas[2] = devCardArea3;
		dashboard.setDevCardAreas(devCardAreas);

		vault = new Vault();
		vault.setResourceAmounts(resourceAmounts);
		resourceAmounts.put(ResourceType.BLUE,	 7);
		resourceAmounts.put(ResourceType.GREY,	10);
		resourceAmounts.put(ResourceType.YELLOW, 3);
		resourceAmounts.put(ResourceType.PURPLE, 6);
		dashboard.setVault(vault);

		storage = new Storage();
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
		dashboard.setStorage(storage);

		popeTokens[0] = new PopeToken(2);
		popeTokens[1] = new PopeToken(3);
		popeTokens[2] = new PopeToken(4);
		popeTokens[0].setActive(true);
		popeTokens[1].setActive(false);
		popeTokens[2].setActive(true);

		p1 = new Player("pippo");
		p1.setLeaderCards(leaderCards);
		p1.setDashboard(dashboard);
		p1.setPopeTokens(popeTokens);
		players = new ArrayList<>();
		players.add(p1);
		model = new Model(players);
		track = new Track(players);
		redPawns = new HashMap<>();
		redPawns.put(players.get(0).getId(), 24);
		track.setRedPawns(redPawns);
		model.setTrack(track);
		assertEquals("Error", 47, model.calculatePoints(p1));
	}

	@Test
	public void endMatch() {
	}
}