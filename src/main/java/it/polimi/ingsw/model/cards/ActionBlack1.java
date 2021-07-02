package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

import java.util.Collections;
import java.util.List;

/**
 * This token increases the black pawn's position by one and shuffles the list of action tokens
 * @author Giulio Occhipinti
 */

public class ActionBlack1 extends ActionToken
{
	private Track track;
	private List<ActionToken> tokens;

	/**
	 * Empty constructor required by XMLEncoder for the "persistence" advanced feature
	 */

	public ActionBlack1()
	{

	}

	/**
	 * Creates the token
	 * @param track the match's track
	 * @param tokens list of all action tokens
	 */

	public ActionBlack1(Track track, List<ActionToken> tokens)
	{
		this.track = track;
		this.tokens = tokens;
	}

	@Override
	public void doAction()
	{
		System.out.println("Activating action of ActionBlack1");
		track.setBlackPawn(track.getBlackPawn() + 1);
		Collections.shuffle(tokens);
		setFlipped(false);			/* After shuffling the tokens, getNextActionToken returns the token after this one */
									/* Setting this to false makes the function return the token at index 0, the "first" one of the stack */
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack(Track track)
	{
		this.track = track;
	}

	public List<ActionToken> getTokens()
	{
		return tokens;
	}

	public void setTokens(List<ActionToken> tokens)
	{
		this.tokens = tokens;
	}
}
