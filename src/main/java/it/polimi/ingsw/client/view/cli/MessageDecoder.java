package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.server.messages.Message;

import javax.swing.*;
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
	public void getBoughtResources(List<ResourceType> boughtResources)
	{
		System.out.println("Received the following resources: " + boughtResources);
	}
}
