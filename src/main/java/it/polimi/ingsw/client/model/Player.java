package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.model.board.Dashboard;
import it.polimi.ingsw.client.model.cards.DevCard;
import it.polimi.ingsw.client.model.cards.LeaderCard;

import java.util.ArrayList;
import java.util.List;

public class Player
{
	private int id;
	private String username;
	private Dashboard dashboard;
	private int victoryPoints;
	private List<DevCard> devCards = new ArrayList<DevCard>();
	private List<LeaderCard> leaderCards = new ArrayList<LeaderCard>();

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
