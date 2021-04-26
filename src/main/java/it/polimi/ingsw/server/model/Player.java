package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.Dashboard;
import it.polimi.ingsw.server.model.cards.DevCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class Player
{
	private int id;				/* 0, 1, 2, 3 to manage turns, pawns and so on */
	private String username;
	private String password;
	private Dashboard dashboard;
	private int victoryPoints;
	private List<DevCard> devCards = new ArrayList<DevCard>();		/* TODO: migrate nearly everything to ArrayList */
	private List<LeaderCard> leaderCards = new ArrayList<LeaderCard>();

	public Player()
	{
		victoryPoints = 0;
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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
