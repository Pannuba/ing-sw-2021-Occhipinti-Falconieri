package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class TrackTest {

    String p1Name = "pippo";
    String p2Name = "pluto";
    String p3Name = "paperino";
    private Player p1 = new Player(p1Name);
    private Player p2 = new Player(p2Name);
    private Player p3 = new Player(p3Name);
    private List<Player> players = new ArrayList<>();
    private Track track = new Track(players);
    private HashMap<Integer, Integer> redPawns = new HashMap<>();

    @Test
    public void checkVaticanReport()
    {
        players.add(p1);
        players.add(p2);
        players.add(p3);
        redPawns.put(players.get(0).getId(), 8);
        redPawns.put(players.get(1).getId(), 3);
        redPawns.put(players.get(2).getId(), 7);
        track.setRedPawns(redPawns);
        assertEquals("Error", 8, track.checkVaticanReport());
    }
}