package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class TrackTest {

	private Player p1;
	private Player p2;
	private Player p3;
	private List<Player> players = new ArrayList<>();
	private Track track;
	private HashMap<Integer, Integer> redPawns = new HashMap<>();

	@Test
	public void checkVaticanReport1()
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p3 = new Player("paperino");
		p1.setId(0);
		p2.setId(1);
		p3.setId(2);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		track = new Track(players);
		redPawns.put(players.get(0).getId(), 2);
		redPawns.put(players.get(1).getId(), 3);
		redPawns.put(players.get(2).getId(), 8);
		track.setRedPawns(redPawns);
		assertEquals("Error", 8, track.checkVaticanReport());
	}

	@Test
	public void checkVaticanReport2()
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p3 = new Player("paperino");
		p1.setId(0);
		p2.setId(1);
		p3.setId(2);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		track = new Track(players);
		redPawns.put(players.get(0).getId(), 2);
		redPawns.put(players.get(1).getId(), 16);
		redPawns.put(players.get(2).getId(), 8);
		track.setRedPawns(redPawns);
		assertEquals("Error", 16, track.checkVaticanReport());
	}

	@Test
	public void checkVaticanReport3()
	{
		p1 = new Player("pippo");
		p2 = new Player("pluto");
		p3 = new Player("paperino");
		p1.setId(0);
		p2.setId(1);
		p3.setId(2);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		track = new Track(players);
		redPawns.put(players.get(0).getId(), 24);
		redPawns.put(players.get(1).getId(), 15);
		redPawns.put(players.get(2).getId(), 20);
		track.setRedPawns(redPawns);
		assertEquals("Error", 24, track.checkVaticanReport());
	}
}