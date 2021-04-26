package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.board.Track;

public class ActionBlack2 extends ActionToken
{
	public void moveBlackPawn(Track track)		/* Where is this function called? */
	{
		track.setBlackPawn(track.getBlackPawn() + 2);
	}
}
