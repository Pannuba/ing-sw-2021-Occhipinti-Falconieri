package server.model;

import server.model.board.Dashboard;
import server.model.board.Track;

/*	Every turn, the server sends a GameState object to all clients
	So the client view reads it and updates everything
	TODO: translate GameState object in client
 */

public class GameState
{
	private Player[] currPlayers;
	private Dashboard[] currBoards;
	private String currPlayerName;
	private Track currTrack;
	private MarblesMarket currMarblesMarket;
	private DevCardsMarket currDevMarket;
	private int round;

	public GameState(Player[] currPlayers, Track currTrack, MarblesMarket currMarblesMarket, DevCardsMarket currDevMarket, int round)
	{
		this.round = round;
		this.currPlayers = currPlayers;
		this.currTrack = currTrack;
		this.currMarblesMarket = currMarblesMarket;
		this.currDevMarket = currDevMarket;

		/*currBoard = currPlayers.getDashboard();		Need to get all dashboards from player. for loop?
		currPlayerName = currPlayers.getUsername();*/
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
