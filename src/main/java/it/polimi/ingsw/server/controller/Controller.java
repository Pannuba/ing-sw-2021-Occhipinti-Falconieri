package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.server.controller.commands.*;
import it.polimi.ingsw.server.messages.ActionTokenMessage;
import it.polimi.ingsw.server.messages.SinglePlayerGameOverMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.*;

public class Controller implements Observer			/* Observes view to get commands... View observes model */
{
	private final Model model;
	private String username;			/* Username of the player who sent the command */
	private ClientHandler view;			/* ClientHandler instance of the view which sent the command. Used for send() method */
	private boolean isTaskFailed;

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
	}

	public void parseInput(List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("Received " + command + " from " + username);
		switch (command.get(0))
		{
			case "SELECT_LEADERCARDS":
				isTaskFailed = new SelectLeaderCardsCommand().run(this, command, username, model);
				return;

			case "INITIAL_RESOURCES":
				isTaskFailed = new InitialResourcesCommand().run(this, command, username, model);
				/* This way the clients can immediately see the picked cards and resources, and the GUI doesn't freeze! */
				view.send(new GameState(model.getPlayers(), model.getCurrentPlayerName(), model.getTrack(), model.getMarblesMarket(), model.getDevCardsMarket()));
				return;

			case "ACTIVATE_LEADER":
				isTaskFailed = new ActivateLeaderCommand().run(this, command, username, model);
				break;

			case "DISCARD_LEADER":
				isTaskFailed = new DiscardLeaderCommand().run(this, command, username, model);
				break;

			case "BUY_DEVCARD":
				isTaskFailed = new BuyDevCardCommand().run(this, command, username, model);
				break;

			case "ACTIVATE_PRODUCTION":
				isTaskFailed = new ActivateProductionCommand().run(this, command, username, model);
				break;

			case "BUY_RESOURCES":
				isTaskFailed = new BuyResourcesCommand().run(this, command, username, model);
				break;
		}

		if ((command.get(0).equals("BUY_RESOURCES") || command.get(0).equals("BUY_DEVCARD")	||
			command.get(0).equals("ACTIVATE_PRODUCTION")) && !isTaskFailed)
		{
			chooseNextPlayer();			/* Don't choose next player during setup or failed actions */
			postRoundChecks();			/* Put in model as separate method, or in update()? */
			model.update();				/* Send new gamestate to everyone */
		}
	}

	private void chooseNextPlayer()		/* In model? Turns alternate by player ID: 0 -> 1 -> 2 -> 3 */
	{
		if (model.getNumPlayers() == 1)
			return;

		int activePlayerID = model.getPlayerByUsername(username).getId();

		if (activePlayerID == model.getNumPlayers() - 1)			/* If activePlayerID = 3 it can't go to 4 (0-indexed!), so back to 0 */
			activePlayerID = 0;

		else													/* Otherwise just increase by 1 */
			activePlayerID++;

		model.getPlayerByUsername(username).setMyTurn(false);		/* Sets false for the player who just sent the command */
		model.getPlayerByID(activePlayerID).setMyTurn(true);

		System.out.println("Next player is " + model.getPlayerByID(activePlayerID).getUsername());
	}

	public void updatePlayerPosition(int ID, int faithPoints)		/* To be called when the player buys red marbles, or...? */
	{
		System.out.println("Updating position for " + model.getPlayerByID(ID).getUsername() + " by " + faithPoints + " faithpoint(s)");
		HashMap<Integer, Integer> newRedPawns = model.getTrack().getRedPawns();

		/* Gets the pawn at position ID [0, 4] and increments the existing value by the new faithPoints */
		newRedPawns.put(ID, model.getTrack().getRedPawns().get(ID) + faithPoints);
		model.getTrack().setRedPawns(newRedPawns);
	}

	private void postRoundChecks()		/* What should be in model and controller? */
	{
		if (model.getNumPlayers() == 1)
		{
			flipActionToken();        /* Not in CommandProcessor because it's not a command sent by the client */

			if (model.isSinglePlayerMatchLost() != null)		/* If the singleplayer has lost the game */
			{
				view.send(new SinglePlayerGameOverMessage(model.isSinglePlayerMatchLost()));
				return;		/* Don't want to execute the other functions below if the match is over */
			}
		}

		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);

		model.checkMatchOver();

		if (model.isMatchOver() && model.getPlayerByUsername(username).getId() == (model.getNumPlayers() - 1))		/* Run endMatch when the first player has been reached */
			model.endMatch();
	}

	public boolean checkResourceAmounts(Player player, List<Resource> requirements)		/* Checks without spending anything */
	{
		Storage storage = player.getDashboard().getStorage();
		Vault vault = player.getDashboard().getVault();

		for (int i = 0; i < requirements.size(); i++)
		{
			int requiredResAmount = 0;
			requiredResAmount += storage.findResourceByType(requirements.get(i).getResourceType());

			SkillStorage storageLeader = player.getStorageLeader(requirements.get(i).getResourceType());

			if (storageLeader != null)		/* If the player has an active SkillStorage of that resource type */
				requiredResAmount += storageLeader.getAdditionalStorage().getShelfResourceQuantity();

			else
				System.out.println(player.getUsername() + " has no active " + requirements.get(i).getResourceType() + " SkillStorage cards!");

			requiredResAmount += vault.getResourceAmounts().get(requirements.get(i).getResourceType());

			if (requiredResAmount < requirements.get(i).getQuantity())		/* If only 1 resource (type and quantity) isn't satisfied, return false */
				return false;
		}

		return true;
	}

	public void spendResources(List<Resource> resourcesToSpend)		/* Only called when a player has enough resources */
	{
		int reqAmount = 0;
		ResourceType reqType;

		for (int i = 0; i < resourcesToSpend.size(); i++)		/* Check every Resource in requirements list */
		{
			reqAmount = resourcesToSpend.get(i).getQuantity();
			reqType = resourcesToSpend.get(i).getResourceType();
			reqAmount -= model.getPlayerByUsername(username).getDashboard().getStorage().removeResource(resourcesToSpend.get(i));

			/* If there are still resources to be removed after checking storage, check SkillStorage cards (if any) */

			if (reqAmount != 0 && model.getPlayerByUsername(username).getStorageLeader(resourcesToSpend.get(i).getResourceType()) != null)
				reqAmount -= model.getPlayerByUsername(username).getStorageLeader(resourcesToSpend.get(i).getResourceType()).removeResources(reqAmount);

			if (reqAmount != 0)		/* If there are still resources to be removed after checking SkillStorage cards, also check vault */
				reqAmount -= model.getPlayerByUsername(username).getDashboard().getVault().removeResource(new Resource(reqType, reqAmount));
		}

		System.out.println("spendResources: reqAmount = " + reqAmount + " (SHOULD BE ZERO)");
	}

	private void flipActionToken()
	{
		ActionToken token = model.getNextActionToken();
		token.doAction();
		view.send(new ActionTokenMessage(token));
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		username = ((ClientHandler)obs).getUsername();
		view = (ClientHandler)obs;
		parseInput((List<String>)obj);
	}

	public ClientHandler getView()
	{
		return view;
	}

	public void setUsername(String username) { this.username = username; }
}