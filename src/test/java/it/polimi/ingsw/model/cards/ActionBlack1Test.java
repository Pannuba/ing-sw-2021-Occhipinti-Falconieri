package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Action Black 1 tests
 * @author Chiara Falconieri
 */

public class ActionBlack1Test {

	private ActionToken actionToken;
	private List<ActionToken> actionTokens = new ArrayList<>();
	private Track track;
	private List<Player> players;
	private Player p1;

	/**
	 * Check that blackPawn has advanced one box
	 * doAction method
	 */

	@Test
	public void doAction()
	{
		p1 = new Player("pippo");
		players = new ArrayList<>();
		players.add(p1);
		track = new Track(players);
		track.setBlackPawn(3);
		actionToken = new ActionBlack1(track, actionTokens);
		actionToken.doAction();
		assertEquals("Error", 4, track.getBlackPawn());
	}
}