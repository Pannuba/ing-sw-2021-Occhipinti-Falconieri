package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.cli.MessageDecoder;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseLeadersMessage implements Message, Serializable
{
	List<LeaderCard> leaderCards = new ArrayList<>();

	public ChooseLeadersMessage(List<LeaderCard> leaderCards)
	{
		this.leaderCards = leaderCards;
	}

	@Override
	public void process(MessageDecoder decoder)
	{
		decoder.chooseLeaderCards(leaderCards);
	}
}
