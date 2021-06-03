package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

/*	Has the abstract definition of all methods called by messages sent by the server
	Put all possible actions here, or only server messages? If only server messages find a better class name. I think all actions is better. No
	Maybe separate methods called by messages and "local" methods/actions with spaces or comments */

public abstract class MessageExecutor
{
	public abstract void firstPlayer(boolean isFirstPlayer);

	public abstract void startMatch();

	public abstract void initialResources(int playerID);

	public abstract void vaticanReport(int popeBoxNum, List<Player> players);

	public abstract void chooseLeaderCards(List<LeaderCard> leaderCards);

	public abstract void getBoughtResources(List<Resource> boughtResources);

	public abstract void getDiscardedResources(int discardedResNum, String playerWhoDiscarded);

	public abstract void getBoughtDevCard(DevCard boughtCard);

	public abstract void getOperationResultMessage(String message, boolean isFailed);

	public abstract void getActionToken(ActionToken token);

	public abstract void matchOver(String winnerName, List<Player> players);

	public abstract void singlePlayerGameOver(String message);
}
