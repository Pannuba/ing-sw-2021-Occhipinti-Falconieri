package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.view.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

		if (command.get(0).equals("BUY_DEVCARD"))
		{
			// change model
		}

		if (command.get(0).equals("ACTIVATE_PRODUCTION"))
		{

		}

		if (command.get(0).equals("MARBLE_MARKET"))
		{

		}

		chooseNextPlayer();

		model.update();		/* Should this be called from Match after every round? */
	}

	private void chooseNextPlayer()		/* Turns alternate by player ID: 0 -> 1 -> 2 -> 3 */
	{
		int activePlayerNum = model.getActivePlayerID();

		if (activePlayerNum == model.getNumPlayers())			/* If activePlayerNum = 4 it can't go to 5, so back to 0 */
			activePlayerNum = 0;

		else													/* Otherwise just increase by 1 */
			activePlayerNum++;

		for (int i = 0; i < model.getNumPlayers(); i++)
		{
			if (model.getPlayers().get(i).getId() == activePlayerNum)
			{
				model.getPlayers().get(i).setMyTurn(true);
				System.out.println("Next player is " + model.getPlayers().get(i).getUsername());
			}
		}
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		parseInput(((ClientHandler)obs).getUsername(), (List<String>)obj);
	}
}