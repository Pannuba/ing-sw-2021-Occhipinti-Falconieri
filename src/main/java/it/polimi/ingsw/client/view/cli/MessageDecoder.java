package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

/* Call an action everytime to allow for an abstract class for CLI and GUI? CLIAction and GUIAction */

/* Remove MessageDecoder and make Message call a function in ActionExecutor? */

import java.util.List;

public class MessageDecoder		/* Reads Message objects sent by server and runs the respective method */
{
	private final ActionCLI action;

	public MessageDecoder(ActionCLI action)
	{
		this.action = action;
	}

	public void firstPlayer(boolean isFirstPlayer)
	{
		action.firstPlayer(isFirstPlayer);
	}

	public void startMatch()
	{
		action.startMatch();
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
		action.getOperationResultMessage(message, isFailed);
	}

	public void getBoughtResources(List<Resource> boughtResources)
	{
		action.getBoughtResources(boughtResources);
	}

	public void getBoughtDevCard(DevCard boughtCard)
	{
		action.getBoughtDevCard(boughtCard);
	}

	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{
		action.getDiscardedResources(discardedResNum, playerWhoDiscarded);
	}

	public void vaticanReport(int popeBoxNum, List<Player> players)
	{
		action.vaticanReport(popeBoxNum, players);
	}

	public void getActionToken(ActionToken token)
	{
		action.getActionToken(token);
	}

	public void matchOver(String winner, List<Player> players)
	{
		action.matchOver(winner, players);
	}

	public void singlePlayerGameOver(String message)
	{
		action.singlePlayerGameOver(message);
	}
}
