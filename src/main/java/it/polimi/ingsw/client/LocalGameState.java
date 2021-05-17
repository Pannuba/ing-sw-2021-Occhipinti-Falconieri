package it.polimi.ingsw.client;

import it.polimi.ingsw.model.DevCardsMarket;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.board.Track;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class LocalGameState implements Observer            /* Observes NetworkHandler to get the newest gamestate */
{
	private List<Player> currPlayers;
	private String currPlayerName;		/* Player who has the current turn (choose action). ID? */
	private Track currTrack;
	private MarblesMarket currMarblesMarket;
	private DevCardsMarket currDevCardsMarket;

	public LocalGameState()
	{

	}

	@Override
	public void update(Observable observable, Object o)			/* Called when the NetworkHandler receives a new gamestate */
	{
		GameState newGameState = (GameState) o;
		this.currPlayers = newGameState.getCurrPlayers();
		this.currPlayerName = newGameState.getCurrPlayerName();
		this.currTrack = newGameState.getCurrTrack();
		this.currMarblesMarket = newGameState.getCurrMarblesMarket();
		this.currDevCardsMarket = newGameState.getCurrDevCardsMarket();
	}

	public List<Player> getCurrPlayers()
	{
		return currPlayers;
	}

	public void setCurrPlayers(List<Player> currPlayers)
	{
		this.currPlayers = currPlayers;
	}

	public String getCurrPlayerName()
	{
		return currPlayerName;
	}

	public void setCurrPlayerName(String currPlayerName)
	{
		this.currPlayerName = currPlayerName;
	}

	public Track getCurrTrack()
	{
		return currTrack;
	}

	public void setCurrTrack(Track currTrack)
	{
		this.currTrack = currTrack;
	}

	public MarblesMarket getCurrMarblesMarket()
	{
		return currMarblesMarket;
	}

	public void setCurrMarblesMarket(MarblesMarket currMarblesMarket)
	{
		this.currMarblesMarket = currMarblesMarket;
	}

	public DevCardsMarket getCurrDevCardsMarket()
	{
		return currDevCardsMarket;
	}

	public void setCurrDevCardsMarket(DevCardsMarket currDevCardsMarket)
	{
		this.currDevCardsMarket = currDevCardsMarket;
	}
}
