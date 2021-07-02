package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Player class. It contains all the information about each player connected to the match: the player's name, dashboard, pope tokens and dev/leader cards
 * @author Giulio Occhipinti
 */

public class Player implements Serializable
{
	private int id;							/* Set randomly in model. 0, 1, 2, 3 to manage turns, pawns and so on */
	private boolean isMyTurn;
	private boolean isDoingDefaultProduction;
	private int victoryPoints;				/* Value assigned at the end of the match */
	private List<LeaderCard> leaderCards;
	private String username;
	private Dashboard dashboard;
	private PopeToken[] popeTokens = new PopeToken[3];			/* Discarded or turned/activated depending on pawn position during vatican report */

	/**
	 * Empty constructor required by XMLEncoder for the "persistence" advanced feature
	 */

	public Player()
	{

	}

	/**
	 * Constructor
	 * @param username the player's username
	 */

	public Player(String username)
	{
		this.username = username;
		isMyTurn = false;
		isDoingDefaultProduction = false;
		victoryPoints = 0;

		popeTokens[0] = new PopeToken(2);
		popeTokens[1] = new PopeToken(3);
		popeTokens[2] = new PopeToken(4);

		leaderCards = new ArrayList<>();
		dashboard = new Dashboard();
	}

	/**
	 * Checks if the player has activated a SkillMarble leadercard. If so, returns the MarbleType(s) of the cards
	 * @return the list of ResourceTypes of the active SkillMarble cards owned by the player
	 */

	public List<ResourceType> getWhiteMarbleTypes()
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

		return whiteMarbleResources;
	}

	/**
	 * Used by the client to check if the player has two active SkillMarble leader cards
	 * @return the list of active SkillMarble cards owned by the player
	 */

	public List<SkillMarble> getMarbleLeaders()
	{
		List<SkillMarble> marbleLeaders = new ArrayList<>();

		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillMarble)
				marbleLeaders.add(((SkillMarble)leaderCards.get(i)));
		}

		return marbleLeaders;
	}

	/**
	 * Used by checkResourceAmounts and spendResources in the controller
	 * @param resType the resource type of the required SkillStorage card
	 * @return the active SkillStorage leader card with the passed ResourceType
	 */

	public SkillStorage getStorageLeader(ResourceType resType)
	{
		for (int i = 0; i < leaderCards.size(); i++)
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillStorage)
			{
				if (((SkillStorage) leaderCards.get(i)).getAdditionalStorage().getShelfResourceType() == resType)
					return ((SkillStorage) leaderCards.get(i));
			}
		}

		return null;
	}

	/**
	 * Used by the controller's "buy dev card" command
	 * @param discountedResource the resource type of the resource that has to be discounted
	 * @return the active SkillDiscount leader card that applies discounts to the passed resource type
	 */

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

	/**
	 * Gets the total amount of resources owned by the player. Used by calculatePoints in Model
	 * @return the amount of resources owned by the player
	 */

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < leaderCards.size(); i++)		/* Get resources from active SkillStorage cards, if any */
		{
			if (leaderCards.get(i).isActive() && leaderCards.get(i) instanceof SkillStorage)
				totalResources += ((SkillStorage) leaderCards.get(i)).getAdditionalStorage().getShelfResourceQuantity();
		}

		totalResources += dashboard.getVault().getTotalResources();
		totalResources += dashboard.getStorage().getTotalResources();

		return totalResources;
	}

	/**
	 * Used by calculatePoints in Model
	 * @return the sum of victory points of the player's flipped pope tokens
	 */

	public int getPopeTokenPoints()
	{
		int popeTokenPoints = 0;

		for (int i = 0; i < 3; i++)
		{
			if (popeTokens[i].isActive())
				popeTokenPoints += popeTokens[i].getPoints();
		}

		return popeTokenPoints;
	}

	/**
	 * Returns the leader card of the passed number. Used by the "activate/discard leader" commands in the controller
	 * @param number the number of the requested leader card
	 * @return the LeaderCard of the passed number
	 */

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

	public void setUsername(String username)
	{
		this.username = username;
	}

	public boolean isMyTurn()
	{
		return isMyTurn;
	}

	public void setMyTurn(boolean myTurn)
	{
		isMyTurn = myTurn;
	}

	public boolean isDoingDefaultProduction()
	{
		return isDoingDefaultProduction;
	}

	public void setDoingDefaultProduction(boolean doingDefaultProduction)
	{
		isDoingDefaultProduction = doingDefaultProduction;
	}

	public Dashboard getDashboard()
	{
		return dashboard;
	}

	public void setDashboard (Dashboard dashboard)
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

	public void setPopeTokens(PopeToken[] popeTokens)
	{
		this.popeTokens = popeTokens;
	}
}
