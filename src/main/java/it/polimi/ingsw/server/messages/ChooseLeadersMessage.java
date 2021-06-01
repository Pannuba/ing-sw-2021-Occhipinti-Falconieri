package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.view.ActionExecutor;
import it.polimi.ingsw.client.view.cli.ActionCLI;
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
	public void process(ActionExecutor action)
	{
		action.chooseLeaderCards(leaderCards);
	}
}
