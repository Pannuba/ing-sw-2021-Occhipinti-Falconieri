package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.ResourceType;
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

	public void chooseResources()
	{
		action.chooseResources();
	}

	public void getOperationResultMessage(String message)
	{
		System.out.println(message);
	}

	public void getBoughtResources(List<ResourceType> boughtResources)
	{
		System.out.print("Received the following resources: ");

		for (int i = 0; i < boughtResources.size(); i++)
			System.out.print(PrintMethods.convertResTypeToString(boughtResources.get(i)) + " ");

		System.out.print("\n");
	}

	public void getBoughtDevCard(DevCard boughtCard)
	{
		System.out.print("Received the following devcard:\n");
		PrintMethods.printDevCard(boughtCard);
	}
}
