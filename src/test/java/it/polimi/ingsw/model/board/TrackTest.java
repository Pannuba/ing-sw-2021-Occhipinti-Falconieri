package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Track tests
 * @author Chiara Falconieri
 */

public class TrackTest {

	private Player p1;
	private Player p2;
	private Player p3;
	private List<Player> players = new ArrayList<>();
	private Track track;
	private HashMap<Integer, Integer> redPawns = new HashMap<>();

	/**
	 * Vatican report for first pope token
	 * checkVaticanReport method
	 */

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
		track.getPopeTokens()[0].setDiscarded(false);
		track.getPopeTokens()[1].setDiscarded(false);
		track.getPopeTokens()[2].setDiscarded(false);
		assertEquals("Error", 8, track.checkVaticanReport());
	}

	/**
	 * Vatican report for second pope token
	 * checkVaticanReport method
	 */

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
		redPawns.put(players.get(2).getId(), 9);
		track.setRedPawns(redPawns);
		track.getPopeTokens()[0].setDiscarded(true);
		track.getPopeTokens()[1].setDiscarded(false);
		track.getPopeTokens()[2].setDiscarded(false);
		assertEquals("Error", 16, track.checkVaticanReport());
	}

	/**
	 * Vatican report for third pope token
	 * checkVaticanReport method
	 */

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
		track.getPopeTokens()[0].setDiscarded(true);
		track.getPopeTokens()[1].setDiscarded(true);
		track.getPopeTokens()[2].setDiscarded(false);
		assertEquals("Error", 24, track.checkVaticanReport());
	}

	/**
	 * Single player: black pawn trigger vatican report
	 * checkVaticanReport method
	 */

	@Test
	public void checkVaticanReport4()
	{
		p1 = new Player("pippo");
		p1.setId(0);
		players.add(p1);
		track = new Track(players);
		redPawns.put(players.get(0).getId(), 16);
		track.setBlackPawn(24);
		track.setRedPawns(redPawns);
		track.getPopeTokens()[0].setDiscarded(true);
		track.getPopeTokens()[1].setDiscarded(true);
		track.getPopeTokens()[2].setDiscarded(false);
		assertEquals("Error", 24, track.checkVaticanReport());
	}
}