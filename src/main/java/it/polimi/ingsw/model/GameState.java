package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*	Every turn, the server sends a GameState object to all clients so the client view reads it and updates everything */

public class GameState implements Serializable
{
	private final List<Player> currPlayers;
	private final String currPlayerName;		/* Player who has the current turn (choose action). ID? */
	private final Track currTrack;
	private final MarblesMarket currMarblesMarket;
	private final DevCardsMarket currDevCardsMarket;
	//private int round;

	public GameState(List<Player> currPlayers, String currPlayerName, Track currTrack, MarblesMarket currMarblesMarket, DevCardsMarket currDevCardsMarket)
	{
		//this.round = round;
		this.currPlayers = currPlayers;
		this.currPlayerName = currPlayerName;
		this.currTrack = currTrack;
		this.currMarblesMarket = currMarblesMarket;
		this.currDevCardsMarket = currDevCardsMarket;
	}

	public Player getPlayerByName(String name)
	{
		for (int i = 0; i < currPlayers.size(); i++)
		{
			if (currPlayers.get(i).getUsername().equals(name))
			{
				return currPlayers.get(i);
			}
		}

		System.out.println("Player \"" + name + "\" not found");
		return null;
	}

	public List<Player> getCurrPlayers()
	{
		return currPlayers;
	}

	public String getCurrPlayerName()
	{
		return currPlayerName;
	}

	public Track getCurrTrack()
	{
		return currTrack;
	}

	public MarblesMarket getCurrMarblesMarket()
	{
		return currMarblesMarket;
	}

	public DevCardsMarket getCurrDevCardsMarket()
	{
		return currDevCardsMarket;
	}

	/*public int getRound()
	{
		return round;
	}*/
}
