package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

public class ActionBlack2 extends ActionToken
{
	private Track track;

	public ActionBlack2()
	{

	}

	public ActionBlack2(Track track)
	{
		this.track = track;
	}

	@Override
	public void doAction()			/* Where is this function called? In model, probably. No, controller! */
	{
		System.out.println("Activating action of ActionBlack2");
		track.setBlackPawn(track.getBlackPawn() + 2);
	}
}
