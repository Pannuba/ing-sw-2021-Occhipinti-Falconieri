package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

import java.util.Collections;
import java.util.List;

public class ActionBlack1 extends ActionToken		/* I can also do an instanceof check in the controller, and if it's an ActionBlack1 manually shuffle the tokens... we'll see */
{													/* but then it doesn't really make sense to use an abstract method, either do one thing or the other, no inbetweens */
	private final Track track;
	private final List<ActionToken> tokens;

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
}
