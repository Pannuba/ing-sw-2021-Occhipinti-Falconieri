package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.board.Track;

public class ActionBlack1 extends ActionToken
{
	public void moveBlackPawn(Track track)
	{
		track.setBlackPawn(track.getBlackPawn() + 1);
	}

	public void shuffleTokens()
	{

	}
}
