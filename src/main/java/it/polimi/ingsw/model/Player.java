package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player
{
	private int id;				/* 0, 1, 2, 3 to manage turns, pawns and so on */
	private String username;
	private Dashboard dashboard;
	private int victoryPoints;
	private List<DevCard> devCards = new ArrayList<DevCard>();
	private List<LeaderCard> leaderCards = new ArrayList<LeaderCard>();

	public Player()
	{
		victoryPoints = 0;
		dashboard = new Dashboard();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Dashboard getDashboard()
	{
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard)
	{
		this.dashboard = dashboard;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints)
	{
		this.victoryPoints = victoryPoints;
	}

	public List<DevCard> getDevCards()
	{
		return devCards;
	}

	public void setDevCards(List<DevCard> devCards)
	{
		this.devCards = devCards;
	}

	public List<LeaderCard> getLeaderCards()
	{
		return leaderCards;
	}

	public void setLeaderCards(List<LeaderCard> leaderCards)
	{
		this.leaderCards = leaderCards;
	}
}
