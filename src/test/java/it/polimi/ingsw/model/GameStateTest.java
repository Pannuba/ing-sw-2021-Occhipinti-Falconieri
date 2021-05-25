package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameStateTest {

    String pippo = "pippo";
    String pluto = "pluto";
    String paperino = "paperino";
    private Player a = new Player(pippo);
    private Player b = new Player(pluto);
    private Player c = new Player(paperino);
    private List<Player> players = new ArrayList<>();
    private Track track = new Track(players);
    private MarblesMarket marblesMarket = new MarblesMarket();
    private DevCardsMarket devCardsMarket = new DevCardsMarket();
    private GameState gameState = new GameState(players,pluto,track,marblesMarket,devCardsMarket);

    @Test
    public void getPlayerByName1()
    {
        players.add(a);
        players.add(b);
        players.add(c);
        assertEquals("Error", b, gameState.getPlayerByName(gameState.getCurrPlayerName()));
    }

    @Test
    public void getCurrPlayers()
    {
        players.add(a);
        players.add(b);
        players.add(c);
        assertEquals("Error", players, gameState.getCurrPlayers());
    }
}