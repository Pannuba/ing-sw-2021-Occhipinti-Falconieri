package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.cards.*;

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
	private Dashboard dashboard;
	private PopeToken[] popeTokens = new PopeToken[3];			/* Discarded or turned/activated depending on pawn position during vatican report */

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
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillMarble)
			{
				whiteMarbleResources.add(((SkillMarble)leaderCards.get(i)).getWhiteMarble());
			}
		}

		System.out.println("Player getWhiteMarbleTypes: returning " + whiteMarbleResources);
		return whiteMarbleResources;
	}

	public List<SkillMarble> getMarbleLeaders()		/* Used by CLI to check if the player has 2 active SkillMarbles */
	{
		List<SkillMarble> marbleLeaders = new ArrayList<>();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillMarble)
				marbleLeaders.add(((SkillMarble)leaderCards.get(i)));
		}

		return marbleLeaders;
	}

	public SkillStorage getStorageLeader(ResourceType resType)		/* Returns the active SkillStorage leadercard with the passed ResourceType */
	{
		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillStorage)		/* Check for discarded? */
			{
				if (((SkillStorage) leaderCards.get(i)).getAdditionalStorage().getShelfResourceType() == resType)
					return ((SkillStorage) leaderCards.get(i));
			}
		}

		return null;
	}

	public SkillDiscount getDiscountLeader(ResourceType discountedResource)
	{
		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillDiscount)
			{
				if (((SkillDiscount) leaderCards.get(i)).getDiscountedResource() == discountedResource)
					return ((SkillDiscount) leaderCards.get(i));
			}
		}

		return null;
	}

	public int getTotalResources()		/* Used by calculatePoints in Model */
	{
		int totalResources = 0;

		for (int i = 0; i < leaderCards.size(); i++)		/* Get resources from active SkillStorage cards, if any */
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillStorage)
				totalResources += ((SkillStorage) leaderCards.get(i)).getAdditionalStorage().getShelfResourceQuantity();
		}

		totalResources += dashboard.getVault().getTotalResources();		/* Points from resources */
		totalResources += dashboard.getStorage().getTotalResources();

		return totalResources;
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

	public LeaderCard getLeaderCardByNumber(int number)
	{
		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).getCardNumber() == number)
				return leaderCards.get(i);
		}

		System.out.println("getLeaderCardByNumber: no cards found!");
		return null;
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

	public void setDashboard (Dashboard dashboard) { this.dashboard = dashboard; }

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

	public void setPopeTokens(PopeToken[] popeTokens) { this.popeTokens = popeTokens; }
}
