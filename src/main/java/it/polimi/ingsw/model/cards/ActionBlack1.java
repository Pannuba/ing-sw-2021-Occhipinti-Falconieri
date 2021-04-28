package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

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
