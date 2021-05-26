package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameStateTest {

    String p1Name = "pippo";
    String p2Name = "pluto";
    String p3Name = "paperino";
    private Player p1 = new Player(p1Name);
    private Player p2 = new Player(p2Name);
    private Player p3 = new Player(p3Name);
    private List<Player> players = new ArrayList<>();
    private Track track = new Track(players);
    private MarblesMarket marblesMarket = new MarblesMarket();
    private DevCardsMarket devCardsMarket = new DevCardsMarket();
    private GameState gameState = new GameState(players, p2Name,track,marblesMarket,devCardsMarket);

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