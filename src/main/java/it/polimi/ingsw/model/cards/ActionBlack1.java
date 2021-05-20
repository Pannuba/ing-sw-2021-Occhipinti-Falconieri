package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Track;

import java.util.Collections;
import java.util.List;

public class ActionBlack1 extends ActionToken
{
	public void moveBlackPawn(Track track)
	{
		track.setBlackPawn(track.getBlackPawn() + 1);
	}

	public List<ActionToken> shuffleTokens(List<ActionToken> tokens)
	{
		Collections.shuffle(tokens);		/* Feels like cheating */
		return tokens;
	}
}
