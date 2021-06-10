package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

/**
 * Action black 2: extends Action Token
 * Use in single game
 * @author Giulio Occhipinti
 */

public class ActionBlack2 extends ActionToken
{
	private Track track;

	public ActionBlack2()
	{

	}

	/**
	 * Creates the token
	 */

	public ActionBlack2(Track track)
	{
		this.track = track;
	}

	/**
	 * Activate the action: advances the blackPawn two box
	 */

	@Override
	public void doAction()			/* Where is this function called? In model, probably. No, controller! */
	{
		System.out.println("Activating action of ActionBlack2");
		track.setBlackPawn(track.getBlackPawn() + 2);
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack(Track track)
	{
		this.track = track;
	}
}
