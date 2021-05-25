package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.*;
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
				runCommand.selectLeaderCards(command, username);
				break;

			case "INITIAL_RESOURCES":							/* "INITIAL_RESOURCES", "BY", "2" */
				runCommand.initialResources(command, username);
				break;

			case "ACTIVATE_LEADER":
				runCommand.activateLeader(command, username);
				break;

			case "DISCARD_LEADER":
				runCommand.discardLeader(command, username);
				break;

			case "BUY_DEVCARD":							/* 	Client picks a devcard # to buy from their local devCardsMarket. Server checks for resources
															If there are enough resources, spend them and add the devcards to Player
															Otherwise send an error message. Can client perform another action?		*/
				runCommand.buyDevCard(command, username);
				chooseNextPlayer();		/* Don't choose next player during setup actions */
				break;

			case "ACTIVATE_PRODUCTION":

				chooseNextPlayer();
				break;

			case "BUY_RESOURCES":					/* What do with marble amounts? Ask where put to them in storage? */
													/* Cut off when it sends the bought resources, and make another command for the rest of the action? */
				runCommand.buyResources(command, username);
				chooseNextPlayer();
				break;
		}

		postRoundChecks();
		model.update();		/* Send new gamestate to everyone */
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
		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);	/* Clients need to check if a vatican report has happened */

		if (model.isMatchOver())		/* Clients perform a check. If victorypoints != 0, the match has ended */
			model.endMatch();
	}

	public boolean checkLeaderRequirements(Dashboard playerBoard, LeaderCard boughtCard)		/* True if requirements are satisfied. Put in Model? */
	{
		List<DevCard> devCards = playerBoard.getAllDevCards();

		switch(boughtCard.getClass().getSimpleName())
		{
			case "SkillDiscount":		/* Player has to have x devcards of any level */
			case "SkillMarble":

				List<DevCardColor> colorRequirements = ((SkillDiscount)boughtCard).getRequirements();

				for (int i = 0; i < devCards.size(); i++)
				{
					for (int j = 0; j < colorRequirements.size(); j++)
					{
						if (devCards.get(i).getColor() == colorRequirements.get(j))
						{
							colorRequirements.remove(j);		/* Remove a requirement for every card that satisfies it */
							j--;
						}
					}
				}

				if (colorRequirements.isEmpty())				/* If there are no more requirements left, it means they're all satisfied */
					return true;

				else
					return false;

			case "SkillProduction":		/* Player has to have 1 devcard of color x and level y */

				Pair<DevCardColor, Integer> prodRequirements = ((SkillProduction) boughtCard).getRequirements();

				for (int i = 0; i < devCards.size(); i++)
				{
					if (devCards.get(i).getColor() == prodRequirements.obj1 && devCards.get(i).getLevel() == prodRequirements.obj2)
						return true;
				}

				return false;

			case "SkillStorage":		/* Player has to have x resources of the same type */

				Resource storageRequirements = ((SkillStorage) boughtCard).getRequirements();
				Storage storage = playerBoard.getStorage();
				Vault vault = playerBoard.getVault();

				int reqResourceQuantity = 0;
				reqResourceQuantity = storage.findResourceByType(storageRequirements.getResourceType());
				reqResourceQuantity += vault.getResourceAmounts().get(storageRequirements.getResourceType());

				if (reqResourceQuantity >= storageRequirements.getQuantity())
					return true;

				else
					return false;
		}

		return false;
	}

	public boolean checkDevCardRequirements(Dashboard playerBoard, DevCard boughtCard)
	{
		Storage storage = playerBoard.getStorage();				/* If there are enough resources, get them from storage, otherwise vault */
		Vault vault = playerBoard.getVault();					/* Ask for player input only when bought resources have to be discarded, see Slack */
		List<Resource> requirements = boughtCard.getRequirements();

		for (int i = 0; i < requirements.size(); i++)
		{
			int requiredResAmount = 0;
			requiredResAmount += storage.findResourceByType(requirements.get(i).getResourceType());
			requiredResAmount += vault.getResourceAmounts().get(requirements.get(i).getResourceType());

			if (requiredResAmount < requirements.get(i).getQuantity())		/* If only 1 resource (type and quantity) isn't satisfied, return false */
				return false;
		}

		return true;
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