package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

/**
 * This token increases the black pawn's position by two
 * @author Giulio Occhipinti
 */

public class ActionBlack2 extends ActionToken
{
	private Track track;

	/**
	 * Empty constructor required by XMLEncoder for the "persistence" advanced feature
	 */

	public ActionBlack2()
	{

	}

	/**
	 * Creates the token
	 * @param track the match's track
	 */

	public ActionBlack2(Track track)
	{
		this.track = track;
	}

	@Override
	public void doAction()
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
