package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.util.Pair;

/* Call an action everytime to allow for an abstract class for CLI and GUI? CLIAction and GUIAction */

import java.util.List;

public class MessageDecoder		/* Reads Message objects sent by server and runs the respective method */
{
	private final ActionExecutor action;

	public MessageDecoder(ActionExecutor action)
	{
		this.action = action;
	}

	public void firstPlayer(boolean isFirstPlayer)
	{
		action.firstPlayer(isFirstPlayer);	/* Very simple, bundle action here? */
	}

	public void startMatch()
	{
		System.out.println("Starting match\n\nMasters of the Renaissance!");
	}

	public void chooseLeaderCards(List<LeaderCard> leaderCards)		/* Call function in ActionExecutor directly from the message? */
	{
		action.chooseLeaderCards(leaderCards);
	}

	public void chooseResources(int playerID)
	{
		action.chooseResources(playerID);
	}

	public void getOperationResultMessage(String message, boolean isFailed)
	{
		System.out.println(message);

		if (isFailed)
			action.chooseAction();		/* Client can repeat the round for failed actions */
	}

	public void getBoughtResources(List<Resource> boughtResources)
	{
		System.out.print("Received the following resources: " + PrintMethods.convertResListToString(boughtResources) + "\n");
	}

	public void getBoughtDevCard(DevCard boughtCard)
	{
		System.out.print("Received the following devcard:\n");
		PrintMethods.printDevCard(boughtCard);
	}

	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{
		action.discardedResources(discardedResNum, playerWhoDiscarded);
	}

	public void vaticanReport(int popeBoxNum, List<Player> players)
	{
		action.vaticanReport(popeBoxNum, players);
	}

	public void getActionToken(ActionToken token)
	{
		System.out.print("Received action token: ");
		PrintMethods.printActionToken(token);
	}

	public void matchOver(String winner, List<Pair<String, Integer>> pointsTable)
	{
		System.out.println("Match over! The winner is " + winner);

		for (int i = 0; i < pointsTable.size(); i++)
			System.out.println(pointsTable.get(i).getObj1() + ": " + pointsTable.get(i).getObj2() + " points");

		//action.quit? back to Main?

	}
}
