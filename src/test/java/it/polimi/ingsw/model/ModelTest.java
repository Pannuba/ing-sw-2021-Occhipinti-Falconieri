package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.DevCardArea;
import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.cards.DevCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ModelTest {

	private Player p1;
	private Player p2;
	private List<Player> players = new ArrayList<>();
	private Model model;
	private Track track;
	private HashMap<Integer, Integer> redPawns = new HashMap<>();
	private Dashboard dashboard = new Dashboard();
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
	private List<DevCard> allDevCards = new ArrayList<>();

	@Test
	public void createActionTokens() {
	}

	@Test
	public void vaticanReport() {
	}

	@Test
	public void isMatchOver1()   /* match over: track */
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		players.add(p1);
		players.add(p2);
		track = new Track(players);
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
		dashboard.setDevCardAreas(devCardAreas);
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		p1.setDashboard(dashboard);
		players.add(p1);
		players.add(p2);
		track = new Track(players);
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
		dashboard.setDevCardAreas(devCardAreas);
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p1.setId(0);
		p2.setId(1);
		p1.setDashboard(dashboard);
		players.add(p1);
		players.add(p2);
		track = new Track(players);
		redPawns.put(players.get(0).getId(), 5);
		redPawns.put(players.get(1).getId(), 16);
		track.setRedPawns(redPawns);
		model = new Model(players);
		model.setTrack(track);
		assertFalse("Error", model.isMatchOver());
	}

	@Test
	public void calculatePoints() {
	}

	@Test
	public void endMatch() {
	}
}