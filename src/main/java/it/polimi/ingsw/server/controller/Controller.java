package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.server.messages.ActionTokenMessage;
import it.polimi.ingsw.server.view.ClientHandler;
import it.polimi.ingsw.util.Pair;

import java.util.*;

public class Controller implements Observer			/* Observes view to get commands... View observes model */
{
	private final Model model;
	private final CommandProcessor runCommand;
	private String username;			/* Username of the player who sent the command */
	private ClientHandler view;			/* ClientHandler instance of the view which sent the command. Used for send() method */

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
		this.runCommand = new CommandProcessor(model, this);		/* CommandProcessor edits the same model created in Match */
	}

	public void parseInput(List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("Username ID: " + model.getPlayerByUsername(username).getId());

		switch (command.get(0))
		{
			case "SELECT_LEADERCARDS":							/* "SELECT_LEADERCARDS", "x", "y" */
				runCommand.selectLeaderCards(command, username);		/* model.update() here or in INITIAL_RESOURCES fricks everything up */
				return;			/* Skip postRoundChecks for singleplayer... Or put flipActionToken somewhere else? */

			case "INITIAL_RESOURCES":							/* "INITIAL_RESOURCES", "BY", "2" */
				runCommand.initialResources(command, username);
				return;

			case "ACTIVATE_LEADER":
				runCommand.activateLeader(command, username);
				break;

			case "DISCARD_LEADER":
				runCommand.discardLeader(command, username);
				break;

			case "BUY_DEVCARD":							/* 	Client picks a devcard # to buy from their local devCardsMarket. Server checks for resources
															If there are enough resources, spend them and add the devcard to Player
															Otherwise send an error message. Can client perform another action?		*/
				runCommand.buyDevCard(command, username);
				chooseNextPlayer();		/* Don't choose next player during setup actions */
				postRoundChecks();		/* Put in model as separate method, or in update()? */
				model.update();			/* Send new gamestate to everyone */
				break;

			case "ACTIVATE_PRODUCTION":

				runCommand.activateProduction(command, username);
				chooseNextPlayer();
				postRoundChecks();
				model.update();
				break;

			case "BUY_RESOURCES":					/* What do with marble amounts? Ask where put to them in storage? */
													/* Cut off when it sends the bought resources, and make another command for the rest of the action? */
				runCommand.buyResources(command, username);
				chooseNextPlayer();
				postRoundChecks();
				model.update();
				break;
		}
	}

	private void chooseNextPlayer()		/* In model? Turns alternate by player ID: 0 -> 1 -> 2 -> 3 */
	{
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
			flipActionToken();		/* Not in CommandProcessor because it's not a command sent by the client */
		}

		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);	/* Clients need to check if a vatican report has happened */

		if (model.isMatchOver())		/* Clients perform a check. If victorypoints != 0, the match has ended */
			model.endMatch();
	}

	public boolean spendResources(List<Resource> requirements)		/* Returns false if it can't remove all the resources from storage and/or vault */
	{
		int reqAmount;
		ResourceType reqType;

		for (int i = 0; i < requirements.size(); i++)		/* Check every Resource in requirements */
		{
			reqAmount = requirements.get(i).getQuantity();
			reqType = requirements.get(i).getResourceType();
			reqAmount -= model.getPlayerByUsername(username).getDashboard().getStorage().removeResource(requirements.get(i));

			if (reqAmount != 0)		/* If there are still resources to be removed after checking storage, also check vault */
				reqAmount -= model.getPlayerByUsername(username).getDashboard().getVault().removeResource(new Resource(reqType, reqAmount));

			if (reqAmount != 0)		/* If there are STILL resources to be removed after checking vault, return false */
				return false;
		}

		return true;
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
}