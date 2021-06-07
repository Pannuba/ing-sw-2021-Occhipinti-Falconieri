package it.polimi.ingsw.client.localmatch.controller;

import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.commands.BuyResourcesCommand;
import it.polimi.ingsw.client.localmatch.controller.commands.InitialResourcesCommand;
import it.polimi.ingsw.client.localmatch.controller.commands.SelectLeaderCardsCommand;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.server.messages.ActionTokenMessage;
import it.polimi.ingsw.server.messages.SinglePlayerGameOverMessage;

import java.util.*;

public class LocalController extends Observable			/* Observes view to get commands... View observes model */
{
	private final LocalModel model;
	private String username;			/* Username of the player who sent the command */
	private View view;
	private boolean isTaskFailed;

	public LocalController(LocalModel model, View view)
	{
		System.out.println("Creating controller");
		this.view = view;
		this.model = model;
		this.username = model.getPlayer().getUsername();
	}

	public void parseInput(List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("Received " + command + " from player");
		switch (command.get(0))
		{
			case "SELECT_LEADERCARDS":
				isTaskFailed = new SelectLeaderCardsCommand().run(this, command, username, model);
				return;

			case "INITIAL_RESOURCES":
				isTaskFailed = new InitialResourcesCommand().run(this, command, username, model);
				/* This way the clients can immediately see the picked cards and resources, and the GUI doesn't freeze! */
				List<Player> playerAsList = new ArrayList<>();
				playerAsList.add(model.getPlayer());
				view.update(new GameState(playerAsList, model.getPlayer().getUsername(), model.getTrack(), model.getMarblesMarket(), model.getDevCardsMarket()));
				return;

			case "ACTIVATE_LEADER":
				//isTaskFailed = new ActivateLeaderCommand().run(this, command, username, model);
				break;

			case "DISCARD_LEADER":
				//isTaskFailed = new DiscardLeaderCommand().run(this, command, username, model);
				break;

			case "BUY_DEVCARD":
				//isTaskFailed = new BuyDevCardCommand().run(this, command, username, model);
				break;

			case "ACTIVATE_PRODUCTION":
				//isTaskFailed = new ActivateProductionCommand().run(this, command, username, model);
				break;

			case "BUY_RESOURCES":
				isTaskFailed = new BuyResourcesCommand().run(this, command, username, model);
				break;
		}

		if ((command.get(0).equals("BUY_RESOURCES") || command.get(0).equals("BUY_DEVCARD")	||
				command.get(0).equals("ACTIVATE_PRODUCTION")) && !isTaskFailed)
		{
			postRoundChecks();			/* Put in model as separate method, or in update()? */
			model.update();				/* Send new gamestate to everyone */
		}
	}

	public void updatePlayerPosition(int ID, int faithPoints)		/* To be called when the player buys red marbles, or...? */
	{
		System.out.println("Updating position for " + model.getPlayer().getUsername() + " by " + faithPoints + " faithpoint(s)");
		HashMap<Integer, Integer> newRedPawns = model.getTrack().getRedPawns();

		/* Gets the pawn at position ID [0, 4] and increments the existing value by the new faithPoints */
		newRedPawns.put(ID, model.getTrack().getRedPawns().get(ID) + faithPoints);
		model.getTrack().setRedPawns(newRedPawns);
	}

	private void postRoundChecks()		/* What should be in model and controller? */
	{
		flipActionToken();        /* Not in CommandProcessor because it's not a command sent by the client */

		if (model.isSinglePlayerMatchLost() != null)		/* If the singleplayer has lost the game */
		{
			view.update(new SinglePlayerGameOverMessage(model.isSinglePlayerMatchLost()));
			return;		/* Don't want to execute the other functions below if the match is over */
		}

		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);

		if (model.isMatchOver())		/* Run endMatch when the first player has been reached */
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
			reqAmount -= model.getPlayer().getDashboard().getStorage().removeResource(resourcesToSpend.get(i));

			/* If there are still resources to be removed after checking storage, check SkillStorage cards (if any) */

			if (reqAmount != 0 && model.getPlayer().getStorageLeader(resourcesToSpend.get(i).getResourceType()) != null)
				reqAmount -= model.getPlayer().getStorageLeader(resourcesToSpend.get(i).getResourceType()).removeResources(reqAmount);

			if (reqAmount != 0)		/* If there are still resources to be removed after checking SkillStorage cards, also check vault */
				reqAmount -= model.getPlayer().getDashboard().getVault().removeResource(new Resource(reqType, reqAmount));
		}

		System.out.println("spendResources: reqAmount = " + reqAmount + " (SHOULD BE ZERO)");
	}

	private void flipActionToken()
	{
		ActionToken token = model.getNextActionToken();
		token.doAction();
		view.update(new ActionTokenMessage(token));
	}

	public View getView()
	{
		return view;
	}
}