package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Action Black 2 tests
 * @author Chiara Falconieri
 */

public class ActionBlack2Test {

	private ActionToken actionToken;
	private Track track;
	private List<Player> players;
	private Player p1;

	/**
	 * Check that blackPawn has advanced two box
	 * doAction method
	 */

	@Test
	public void doAction()
	{
		p1 = new Player("pippo");
		players = new ArrayList<>();
		players.add(p1);
		track = new Track(players);
		track.setBlackPawn(5);
		actionToken = new ActionBlack2(track);
		actionToken.doAction();
		assertEquals("Error", 7, track.getBlackPawn());
	}
}