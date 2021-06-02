package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.event.ActionEvent;

import java.util.List;

public class ActionGUI extends MessageExecutor
{
	private final NetworkHandler networkHandler;

	public ActionGUI(NetworkHandler networkHandler)
	{
		this.networkHandler = networkHandler;
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)
	{

	}

	@Override
	public void startMatch()
	{

	}

	@Override
	public void chooseResources(int playerID)
	{

	}

	@Override
	public void vaticanReport(int popeBoxNum, List<Player> players)
	{

	}

	@Override
	public void chooseLeaderCards(List<LeaderCard> leaderCards)
	{

	}

	@Override
	public void getBoughtResources(List<Resource> boughtResources)
	{

	}

	@Override
	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{

	}

	@Override
	public void getBoughtDevCard(DevCard boughtCard)
	{

	}

	@Override
	public void getOperationResultMessage(String message, boolean isFailed)
	{

	}

	@Override
	public void getActionToken(ActionToken token)
	{

	}

	@Override
	public void matchOver(String winnerName, List<Player> players)
	{

	}

	@Override
	public void singlePlayerGameOver(String message)
	{

	}
}
