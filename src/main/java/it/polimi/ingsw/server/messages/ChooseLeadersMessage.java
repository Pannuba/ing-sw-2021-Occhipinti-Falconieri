package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.Serializable;
import java.util.List;

public class ChooseLeadersMessage implements Message, Serializable
{
	List<LeaderCard> leaderCards;

	public ChooseLeadersMessage(List<LeaderCard> leaderCards)
	{
		this.leaderCards = leaderCards;
	}

	@Override
	public void process(MessageExecutor action)
	{
		action.chooseLeaderCards(leaderCards);
	}
}
