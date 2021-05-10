package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Track;

import java.util.ArrayList;
import java.util.List;

/*	Every turn, the server sends a GameState object to all clients
	So the client view reads it and updates everything
	TODO: translate GameState object in client
 */

public class GameState
{
	private List<Player> currPlayers;
	private Dashboard[] currBoards;
	private String currPlayerName;
	private Track currTrack;
	private MarblesMarket currMarblesMarket;
	private DevCardsMarket currDevCardsMarket;
	private int round;

	public GameState(List<Player> currPlayers, Track currTrack, MarblesMarket currMarblesMarket, DevCardsMarket currDevCardsMarket, int round)
	{
		this.round = round;
		this.currPlayers = currPlayers;
		this.currTrack = currTrack;
		this.currMarblesMarket = currMarblesMarket;
		this.currDevCardsMarket = currDevCardsMarket;

		/*currBoard = currPlayers.getDashboard();		Need to get all dashboards from player. for loop?
		currPlayerName = currPlayers.getUsername();*/
	}

	public Player getPlayerByName(String name)
	{
		Player player = new Player();

		for (int i = 0; i < currPlayers.size(); i++)
		{
			if (currPlayers.get(i).getUsername().equals(name))
				player = currPlayers.get(i);

			else
			{
				System.out.println("Player \"" + name + "\" not found");
				return null;
			}
		}

		return player;
	}

	public int getRound()
	{
		return round;
	}

	public void setRound(int round)
	{
		this.round = round;
	}

	public String getCurrPlayerName()
	{
		return currPlayerName;
	}

	public void setCurrPlayerName(String currPlayerName)
	{
		this.currPlayerName = currPlayerName;
	}
}
