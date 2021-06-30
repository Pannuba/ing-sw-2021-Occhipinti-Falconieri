package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.server.ServerListener;
import it.polimi.ingsw.server.controller.commands.*;
import it.polimi.ingsw.server.messages.ActionTokenMessage;
import it.polimi.ingsw.server.messages.SinglePlayerGameOverMessage;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.*;

/**
 * Controller class
 * @author Giulio Occhipinti
 */

public class Controller implements Observer			/* Observes view to get commands... View observes model */
{
	private final Model model;
	private final ServerListener serverListener;
	private String username;			/* Username of the player who sent the command */
	private ClientHandler view;			/* ClientHandler instance of the view which sent the command. Used for send() method */
	private boolean isTaskFailed;

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
		this.serverListener = null;
	}

	public Controller(Model model, ServerListener serverListener)
	{
		System.out.println("Creating controller");
		this.model = model;
		this.serverListener = serverListener;
	}

	/**
	 * The main function that calls the commands
	 * @param command the command received by the ClientHandler
	 */
	public void parseInput(List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("Received " + command + " from " + username);
		switch (command.get(0))
		{
			case "SELECT_LEADERCARDS":
				isTaskFailed = new SelectLeaderCardsCommand(this).run(command);
				return;

			case "INITIAL_RESOURCES":
				isTaskFailed = new InitialResourcesCommand(this).run(command);
				/* This way the clients can immediately see the picked cards and resources, and the GUI doesn't freeze! */
				view.send(new GameState(model.getPlayers(), model.getCurrentPlayerName(), model.getTrack(), model.getMarblesMarket(), model.getDevCardsMarket()));
				return;

			case "ACTIVATE_LEADER":
				isTaskFailed = new ActivateLeaderCommand(this).run(command);
				view.send(new GameState(model.getPlayers(), model.getCurrentPlayerName(), model.getTrack(), model.getMarblesMarket(), model.getDevCardsMarket()));
				break;

			case "DISCARD_LEADER":
				isTaskFailed = new DiscardLeaderCommand(this).run(command);
				break;

			case "BUY_DEVCARD":
				isTaskFailed = new BuyDevCardCommand(this).run(command);
				break;

			case "ACTIVATE_PRODUCTION":
				isTaskFailed = new ActivateProductionCommand(this).run(command);
				break;

			case "STOP_PRODUCTION":
				isTaskFailed = new StopProductionCommand(this).run(command);
				break;

			case "BUY_RESOURCES":
				isTaskFailed = new BuyResourcesCommand(this).run(command);
				break;
		}

		if ((command.get(0).equals("BUY_RESOURCES") || command.get(0).equals("BUY_DEVCARD")	||
			command.get(0).equals("STOP_PRODUCTION") || command.get(0).equals("DISCARD_LEADER")) && !isTaskFailed)
		{
			if (!command.get(0).equals("DISCARD_LEADER"))	/* Don't progress rounds if the player discarded a leader, but update gamestate to show other players the new position */
			{
				chooseNextPlayer();				/* Don't choose next player during setup, failed actions or "discard leader" actions */
				postRoundChecks();				/* Put in model as separate method, or in update()? */
			}

			model.update();				/* Send new gamestate to everyone */
		}
	}

	/**
	 * If the action performed by the client is successful, this method is executed.
	 */

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

	/**
	 * Called whenever the player gains faith points
	 * @param ID the player's ID, used as key to get their pawn in the Track's redPawns hashmap
	 * @param faithPoints the number of places to move the pawn
	 */

	public void updatePlayerPosition(int ID, int faithPoints)		/* To be called when the player buys red marbles, or...? */
	{
		System.out.println("Updating position for " + model.getPlayerByID(ID).getUsername() + " by " + faithPoints + " faithpoint(s)");
		HashMap<Integer, Integer> newRedPawns = model.getTrack().getRedPawns();

		/* Gets the pawn at position ID [0, 4] and increments the existing value by the new faithPoints */
		newRedPawns.put(ID, model.getTrack().getRedPawns().get(ID) + faithPoints);
		model.getTrack().setRedPawns(newRedPawns);
	}

	/**
	 * Called when the client's action is successful. Checks for vatican reports, match-ending conditions and flips the next ActionToken for singleplayer matches
	 */

	private void postRoundChecks()		/* What should be in model and controller? */
	{
		if (model.getNumPlayers() == 1)
		{
			flipActionToken();        /* Not in CommandProcessor because it's not a command sent by the client */

			if (model.isSinglePlayerMatchLost() != null)		/* If the singleplayer has lost the game */
			{
				if (serverListener != null)
					serverListener.deleteRecoveredMatch(model);

				view.send(new SinglePlayerGameOverMessage(model.isSinglePlayerMatchLost()));
				return;		/* Don't want to execute the other functions below if the match is over */
			}
		}

		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);

		model.checkMatchOver();

		if (model.isMatchOver() && model.getPlayerByUsername(username).getId() == (model.getNumPlayers() - 1))		/* Run endMatch when the first player has been reached */
		{
			if (serverListener != null)
				serverListener.deleteRecoveredMatch(model);

			model.endMatch();
		}
	}

	/**
	 * Checks if the player's storage, vault and SkillStorage cards (if any) have enough resources before buying a devcard or activating the production
	 * @param player the player who is spending the resources
	 * @param requirements the resources that have to be spent
	 * @return true if the requirements are satisfied, false otherwise
	 */

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

	/**
	 * Called when checkResourceAmounts returns true, this method actually removes the required resources from the player's deposits
	 * @param resourcesToSpend the resources that need to be spent. Same as "requirements" in checkResourceAmounts
	 */

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

	/**
	 * This method is called in singleplayer matches, it gets the next action token from the Model, activates it and sends it to the Client
	 */

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

	public Model getModel()
	{
		return model;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}