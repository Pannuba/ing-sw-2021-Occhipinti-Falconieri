package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.cards.DevCard;

import java.io.Serializable;

public class BoughtDevCardMessage implements Message, Serializable
{
	private final DevCard boughtCard;

	public BoughtDevCardMessage(DevCard boughtCard)
	{
		this.boughtCard = boughtCard;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.getBoughtDevCard(boughtCard);
	}
}
