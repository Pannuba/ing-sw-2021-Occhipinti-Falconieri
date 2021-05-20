package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.Dashboard;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.*;

public class Controller implements Observer			/* Observes view to get commands... View observes model */
{
	private final Model model;
	private String username;			/* Username of the player who sent the command */
	private ClientHandler view;			/* ClientHandler instance of the view which sent the command. Used for send() method */

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
	}

	public void parseInput(List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("Username ID: " + model.getPlayerByUsername(username).getId());

		if (command.get(0).equals("SELECT_LEADERCARDS"))		/* "SELECT_LEADERCARDS", "x", "y" */
		{
			List<LeaderCard> cards = new ArrayList<LeaderCard>();

			for (int i = 0; i < 16; i++)
			{
				if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1))		||
					String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(2))		)
					cards.add(model.getAllLeaderCards().get(i));
			}
			System.out.println("Setting cards #" + cards.get(0).getCardNumber() + " and #" + cards.get(1).getCardNumber() + " for " + username);
			model.getPlayerByUsername(username).setLeaderCards(cards);
		}

		if (command.get(0).equals("INITIAL_RESOURCES"))			/* "INITIAL_RESOURCES", "BY", "2" */
		{
			if (command.get(1).isEmpty() == false)
			{
				for (int i = 0; i < command.get(1).length(); i++)
				{
					Resource resourceToAdd = new Resource();
					resourceToAdd.setQuantity(1);
					resourceToAdd.setResourceType(ResourceType.convertStringToResType(Character.toString(command.get(1).charAt(i))));

					/* Put resource in storage, ask player where? Create method somewhere */

				}
			}

			if (command.get(2).isEmpty() == false)		/* If there are initial faithpoints, get that player's pawn and move it */
			{
				updatePlayerPosition(model.getPlayerByUsername(username).getId(), Integer.parseInt(command.get(2)));
				/* model.getPlayerByUsername(username).getID() and model.getActivePlayerID() should always be the same */
			}
		}

		if (command.get(0).equals("BUY_DEVCARD"))		/* 	Client picks a devcard # to buy from their local devCardsMarket. Server checks for resources
															If there are enough resources, spend them and add the devcards to Player
															Otherwise send an error message. Can client perform another action?		*/
		{
			int boughtCardNum = Integer.parseInt(command.get(1));
			DevCard boughtCard = model.getDevCardsMarket().getDevCardByNumber(boughtCardNum);
			checkResources(model.getPlayerByUsername(username).getDashboard(), boughtCard);


			chooseNextPlayer();		/* Don't choose next player during setup actions */
		}

		if (command.get(0).equals("ACTIVATE_PRODUCTION"))
		{


			chooseNextPlayer();
		}

		if (command.get(0).equals("BUY_RESOURCES"))		/* What do with marble amounts? Ask where put to them in storage? */
		{
			List<Marble> boughtMarbles = new ArrayList<Marble>();
			List<Resource> resourcesToAddToStorage = new ArrayList<Resource>();

			if (command.get(1).equals("ROW"))
				boughtMarbles = model.getMarblesMarket().buyMarblesRow(Integer.parseInt(command.get(2)) - 1);		/* - 1 because 0-indexed, [0, 1, 2] */

			if (command.get(1).equals("COLUMN"))
				boughtMarbles = model.getMarblesMarket().buyMarblesCol(Integer.parseInt(command.get(2))- 1);

			for (int i = 0; i < boughtMarbles.size(); i++)
			{
				if (i == 0)		/* boughtMarbles[0] has the red marbles, so faithpoints */
					updatePlayerPosition(model.getPlayerByUsername(username).getId(), boughtMarbles.get(0).getQuantity());

				else
				{
					ResourceType marbleResourceType = null;

					if (i == 1)        /* boughtMarbles[1] has the white marbles */
					{
						List<ResourceType> whiteMarbleTypes = model.getPlayerByUsername(username).getWhiteMarbleTypes();    /* or model.getWhiteMarbleTypes(username */

						if (whiteMarbleTypes.size() == 1)		/* If there's only 1 active SkillMarble */
							marbleResourceType = whiteMarbleTypes.get(0);

						if (whiteMarbleTypes.size() > 1)
						{
							/* Ask client which resourcetype they want to pick to convert white marbles */
						}
					}

					else
						marbleResourceType = convertMarbleToResType(boughtMarbles.get(i).getMarbleType());

					Resource resourceToAdd = new Resource();
					resourceToAdd.setResourceType(marbleResourceType);
					resourceToAdd.setQuantity(boughtMarbles.get(i).getQuantity());
					resourcesToAddToStorage.add(resourceToAdd);
				}
			}

			/* Add resources to storage */
			System.out.println("Sending " + resourcesToAddToStorage + " to " + username);
			view.send(resourcesToAddToStorage);

			chooseNextPlayer();
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
		System.out.println("ActivePlayerID after " + activePlayerID);
		System.out.println("Next player is " + model.getPlayerByID(activePlayerID).getUsername());
	}

	private void updatePlayerPosition(int ID, int faithPoints)		/* To be called when the player buys red marbles, or...? */
	{
		System.out.println("Updating position for " + model.getPlayerByID(ID).getUsername() + " by " + faithPoints + " faithpoint(s)");
		HashMap<Integer, Integer> newRedPawns = model.getTrack().getRedPawns();

		/* Gets the pawn at position ID [0, 4] and increments the existing value by the new faithPoints */
		newRedPawns.put(ID, model.getTrack().getRedPawns().get(ID) + faithPoints);
		model.getTrack().setRedPawns(newRedPawns);
	}

	private ResourceType convertMarbleToResType(MarbleType marbleType)		/* Also check player leadercards SkillMarble, if active */
	{
		if (marbleType == null)
			return null;

		switch(marbleType)			/* White marbles have their separate function */
		{
			case GREY:
				return ResourceType.GREY;

			case YELLOW:
				return ResourceType.YELLOW;

			case BLUE:
				return ResourceType.BLUE;

			case PURPLE:
				return ResourceType.PURPLE;

			default:
				System.out.print("Error\n");
				return null;
		}
	}

	private void postRoundChecks()		/* What should be in model and controller? */
	{
		int vaticanReportNum = model.getTrack().checkVaticanReport();

		if (vaticanReportNum != 0)
			model.vaticanReport(vaticanReportNum);	/* Clients need to check if a vatican report has happened */

		if (model.isMatchOver())		/* Clients perform a check. If victorypoints != 0, the match has ended */
			model.endMatch();
	}

	private boolean checkResources(Dashboard playerBoard, DevCard boughtCard)	/* If there are enough resources, get them from storage, otherwise vault */
	{																			/* Ask for player input only when bought resouces have to be discarded, see Slack */
		Storage storage = playerBoard.getStorage();
		Vault vault = playerBoard.getVault();
		List<Resource> requirements = boughtCard.getRequirements();
		// ... ... ...
		return false;
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		username = ((ClientHandler)obs).getUsername();
		view = (ClientHandler)obs;
		parseInput((List<String>)obj);
	}
}