package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.view.ClientHandler;

import javax.print.MultiDocPrintService;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/*	TODO: second player chooses 1 resource, third 1 resource and 1 faithPoint, fourth 2 resources and 1 faithPoint
	Put a check in client view and send a command like SELECT_INITIAL_RESOURCES
*/

public class Controller implements Observer			/* Observes view to get commands... View observes model right? */
{
	private final Model model;

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
	}

	public void parseInput(String username, List<String> command)		/* Gets name from ClientHandler to change that player's stuff */
	{
		System.out.println("activePlayerID in model: " + model.getActivePlayerID() + ", username ID: " + model.getPlayerByUsername(username).getId());

		if (command.get(0).equals("SELECT_LEADERCARDS"))		/* "SELECT_LEADERCARDS", "x", "y" */
		{
			List<LeaderCard> cards = new ArrayList<LeaderCard>();

			for (int i = 0; i < 16; i++)
			{
				if (String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(1))		||
					String.valueOf(model.getAllLeaderCards().get(i).getCardNumber()).equals(command.get(2))		)
					cards.add(model.getAllLeaderCards().get(i));
			}
			System.out.println("Setting cards #" + cards.get(0).getCardNumber() + " and #" + cards.get(1).getCardNumber() + "for " + username);
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

		if (command.get(0).equals("BUY_DEVCARD"))
		{


			chooseNextPlayer();		/* Don't choose next player during setup actions */
		}

		if (command.get(0).equals("ACTIVATE_PRODUCTION"))
		{


			chooseNextPlayer();
		}

		if (command.get(0).equals("BUY_RESOURCES"))		/* What do with marble amounts? Ask where put to them in storage? */
		{

			if (command.get(1).equals("ROW"))
			{
				model.getMarblesMarket().buyMarblesRow(Integer.parseInt(command.get(2)));
			}

			if (command.get(1).equals("COLUMN"))
			{
				model.getMarblesMarket().buyMarblesCol(Integer.parseInt(command.get(2)));
			}

			chooseNextPlayer();
		}

		model.update();		/* Should this be called from Match after every round? */
	}

	private void chooseNextPlayer()		/* Turns alternate by player ID: 0 -> 1 -> 2 -> 3 */
	{
		int activePlayerNum = model.getActivePlayerID();

		if (activePlayerNum == model.getNumPlayers())			/* If activePlayerNum = 4 it can't go to 5, so back to 0 */
			activePlayerNum = 0;

		else													/* Otherwise just increase by 1 */
			activePlayerNum++;

		model.setActivePlayerID(activePlayerNum);		/* Is activePlayerID even needed? Only the active player sends commands, we can get their ID and increase by 1 */

		for (int i = 0; i < model.getNumPlayers(); i++)
		{
			if (model.getPlayers().get(i).getId() == activePlayerNum)
			{
				model.getPlayers().get(i).setMyTurn(true);
				System.out.println("Next player is " + model.getPlayers().get(i).getUsername());
			}
		}
	}

	private void updatePlayerPosition(int ID, int faithPoints)		/* To be called when the player buys red marbles, or...? */
	{
		System.out.println("Updating position for " + model.getPlayerByID(ID).getUsername() + " by " + faithPoints + " faithpoints");
		List<Integer> newRedPawns = model.getTrack().getRedPawns();
		/* Gets the pawn at position ID [0, 4] and increments the existing value by the new faithPoints */
		newRedPawns.set(ID, model.getTrack().getRedPawns().get(ID) + faithPoints);
		model.getTrack().setRedPawns(newRedPawns);
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		parseInput(((ClientHandler)obs).getUsername(), (List<String>)obj);
	}
}