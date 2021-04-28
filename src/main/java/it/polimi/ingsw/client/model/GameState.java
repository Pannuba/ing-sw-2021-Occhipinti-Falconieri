package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.model.board.Dashboard;
import it.polimi.ingsw.client.model.board.Track;

public class GameState
{
	private Player[] currPlayers;
	private Dashboard[] currBoards;
	private String currPlayerName;
	private Track currTrack;
	private MarblesMarket currMarblesMarket;
	private DevCardsMarket currDevMarket;
	private int round;

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
