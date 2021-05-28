package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.PopeToken;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/* One track shared among all players */

public class Track implements Serializable
{
	private final Box[] faithTrack = new Box[25];
	private final PopeToken[] popeTokens = new PopeToken[3];		/* Track has 3 popetokens, discarded when a player calls a vatican report for each token */
	private HashMap<Integer, Integer> redPawns;				/* Key: ID, value: position */
	private final int numPlayers;
	private int blackPawn;

	public Track(List<Player> players)
	{
		System.out.println("Creating track...");

		numPlayers = players.size();
		redPawns = new HashMap<>();

		for (int i = 0; i < players.size(); i++)		/* Initialize player pawns, player ID 0 has redPawns[0], ID 1 has redPawns[1] and so on */
			redPawns.put(players.get(i).getId(), 0);

		blackPawn = 0;

		popeTokens[0] = new PopeToken(2);
		popeTokens[1] = new PopeToken(3);
		popeTokens[2] = new PopeToken(4);

		/* Initialize track here, hardcoded (at least for now) because 1 xml for each box is too much */

		faithTrack[0]  = new Box(BoxType.NORMAL, 0,  0);
		faithTrack[1]  = new Box(BoxType.NORMAL, 1,  0);
		faithTrack[2]  = new Box(BoxType.NORMAL, 2,  0);
		faithTrack[3]  = new Box(BoxType.POINTS, 3,  1);
		faithTrack[4]  = new Box(BoxType.NORMAL, 4,  0);
		faithTrack[5]  = new Box(BoxType.NORMAL, 5,  0);
		faithTrack[6]  = new Box(BoxType.POINTS, 6,  2);
		faithTrack[7]  = new Box(BoxType.NORMAL, 7,  0);
		faithTrack[8]  = new Box(BoxType.POPE,   8,  0);
		faithTrack[9]  = new Box(BoxType.POINTS, 9,  4);
		faithTrack[10] = new Box(BoxType.NORMAL, 10, 0);
		faithTrack[11] = new Box(BoxType.NORMAL, 11, 0);
		faithTrack[12] = new Box(BoxType.POINTS, 12, 6);
		faithTrack[13] = new Box(BoxType.NORMAL, 13, 0);
		faithTrack[14] = new Box(BoxType.NORMAL, 14, 0);
		faithTrack[15] = new Box(BoxType.POINTS, 15, 9);
		faithTrack[16] = new Box(BoxType.POPE,   16, 0);
		faithTrack[17] = new Box(BoxType.NORMAL, 17, 0);
		faithTrack[18] = new Box(BoxType.POINTS, 18, 12);
		faithTrack[19] = new Box(BoxType.NORMAL, 19, 0);
		faithTrack[20] = new Box(BoxType.NORMAL, 20, 0);
		faithTrack[21] = new Box(BoxType.POINTS, 21, 16);
		faithTrack[22] = new Box(BoxType.NORMAL, 22, 0);
		faithTrack[23] = new Box(BoxType.NORMAL, 23, 0);
		faithTrack[24] = new Box(BoxType.POPE,   24, 20);
	}

	public int checkVaticanReport()		/* Returns the number of the pope box that triggered the vatican report. 0 if no vatican report */
	{
		for (int i = 0; i < numPlayers; i++)		/* redPawns.get(i) returns the position of player ID i */
		{
			if (redPawns.get(i) >= 8  && !popeTokens[0].isDiscarded())	return 8;

			if (redPawns.get(i) >= 16 && !popeTokens[1].isDiscarded())	return 16;

			if (redPawns.get(i) >= 24 && !popeTokens[0].isDiscarded())	return 24;
		}

		return 0;
	}

	public Box[] getFaithTrack()
	{
		return faithTrack;
	}

	public PopeToken[] getPopeTokens()
	{
		return popeTokens;
	}

	public HashMap<Integer, Integer> getRedPawns()
	{
		return redPawns;
	}

	public void setRedPawns(HashMap<Integer, Integer> redPawns)
	{
		this.redPawns = redPawns;
	}

	public int getBlackPawn()
	{
		return blackPawn;
	}

	public void setBlackPawn(int blackPawn)
	{
		this.blackPawn = blackPawn;
	}
}

