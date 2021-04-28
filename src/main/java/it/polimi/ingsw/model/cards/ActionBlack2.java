package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

public class ActionBlack2 extends ActionToken
{
	public void moveBlackPawn(Track track)		/* Where is this function called? */
	{
		track.setBlackPawn(track.getBlackPawn() + 2);
	}
}
