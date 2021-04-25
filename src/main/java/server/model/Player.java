package server.model;

import server.model.board.Dashboard;

public class Player
{
	private int id;				/* 0, 1, 2, 3 to manage turns, pawns and so on */
	private String username;
	private String password;
	private Dashboard dashboard;
	private int victoryPoints;

	/* TODO: add cards array */

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
}
