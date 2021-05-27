package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.List;

public class MessageDecoder		/* Reads Message objects sent by server and runs the respective method */
{
	private final ActionExecutor action;

	public MessageDecoder(ActionExecutor action)
	{
		this.action = action;
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
			action.chooseAction();
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

	public void getActionToken(ActionToken token)
	{
		System.out.print("Received action token: ");
		PrintMethods.printActionToken(token);
	}
}
