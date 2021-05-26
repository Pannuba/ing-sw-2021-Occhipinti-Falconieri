package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameStateTest {

	private Player p1 = new Player("pippo");
	private Player p2 = new Player("pluto");
	private Player p3 = new Player("paperino");
	private List<Player> players = new ArrayList<>();
	private Track track = new Track(players);
	private MarblesMarket marblesMarket = new MarblesMarket();
	private DevCardsMarket devCardsMarket = new DevCardsMarket();
	private GameState gameState = new GameState(players, "pluto",track,marblesMarket,devCardsMarket);

	@Test
	public void getPlayerByName1()
	{
		players.add(p1);
		players.add(p2);
		players.add(p3);
		assertEquals("Error", p2, gameState.getPlayerByName(gameState.getCurrPlayerName()));
	}

	@Test
	public void getCurrPlayers()
	{
		players.add(p1);
		players.add(p2);
		players.add(p3);
		assertEquals("Error", players, gameState.getCurrPlayers());
	}
}