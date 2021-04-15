package model.board;

import model.cards.PopeToken;

public class Track
{
	private Box[] faithTrack = new Box[25];        /* new Box[25] goes here or in constructor? */
	private PopeToken[] popeTokens = new PopeToken[3];
	private int redPawn;
	private int blackPawn;

	public Track()
	{
		redPawn = 0;
		blackPawn = 0;

		for (int i = 0; i < 25; i++)
			faithTrack[i] = new Box();

		/* Initialize track here, hardcoded (at least for now) because 1 xml for each box is too much */
		faithTrack[0].setPosition(0);
		faithTrack[0].setCategory(BoxType.NORMAL);
		faithTrack[1].setPosition(1);        /* Is this even needed? */
		faithTrack[1].setCategory(BoxType.NORMAL);
		faithTrack[2].setCategory(BoxType.NORMAL);
		faithTrack[3].setCategory(BoxType.POINTS);
		faithTrack[3].setVictoryPoints(1);
		/* ... */

		for (int i = 0; i < 15; i++)        /* 15 normal boxes */
		{
			faithTrack[i].setVictoryPoints(0);
		}
	}
}

