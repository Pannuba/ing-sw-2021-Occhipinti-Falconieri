package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.PopeToken;
import it.polimi.ingsw.model.cards.SkillMarble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable
{
	private int id;							/* Set randomly in model. 0, 1, 2, 3 to manage turns, pawns and so on */
	private boolean isMyTurn;
	private int victoryPoints;				/* Value assigned at the end of the match */
	private List<LeaderCard> leaderCards;
	private final String username;
	private final Dashboard dashboard;
	private final PopeToken[] popeTokens = new PopeToken[3];			/* Discarded or turned/activated depending on pawn position during vatican report */

	public Player(String username)
	{
		this.username = username;
		isMyTurn = false;
		victoryPoints = 0;

		popeTokens[0] = new PopeToken(2);
		popeTokens[1] = new PopeToken(3);
		popeTokens[2] = new PopeToken(4);

		leaderCards = new ArrayList<>();
		dashboard = new Dashboard();
	}

	public List<ResourceType> getWhiteMarbleTypes()		/* Checks if the player has activated a SkillMarble leadercard. If so, returns the MarbleType(s) of the cards */
	{
		/* If there are 2 SkillMarble cards, the player has to choose which resource to convert the white marbles */
		List<ResourceType> whiteMarbleResources = new ArrayList<>();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isDiscarded() == false && leaderCards.get(i) instanceof SkillMarble)		/* && is active? */
			{
				whiteMarbleResources.add(((SkillMarble)leaderCards.get(i)).getWhiteMarble());
			}
		}

		return whiteMarbleResources;
	}

	public int getPopeTokenPoints()
	{
		int popeTokenPoints = 0;

		for (int i = 0; i < 3; i++)			/* Make pope tokens number dynamic? */
		{
			if (popeTokens[i].isActive())
				popeTokenPoints += popeTokens[i].getPoints();
		}

		return popeTokenPoints;
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

	public boolean isMyTurn()
	{
		return isMyTurn;
	}

	public void setMyTurn(boolean myTurn)
	{
		isMyTurn = myTurn;
	}

	public Dashboard getDashboard()
	{
		return dashboard;
	}

	public int getVictoryPoints()
	{
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints)
	{
		this.victoryPoints = victoryPoints;
	}

	public List<LeaderCard> getLeaderCards()
	{
		return leaderCards;
	}

	public void setLeaderCards(List<LeaderCard> leaderCards)
	{
		this.leaderCards = leaderCards;
	}

	public PopeToken[] getPopeTokens()
	{
		return popeTokens;
	}
}
