package server.model.board;

import server.model.cards.PopeToken;

/* One track shared among all players */

public class Track
{
	private Box[] faithTrack = new Box[25];        /* new Box[25] goes here or in constructor? */
	private PopeToken[] popeTokens = new PopeToken[3];
	private int[] redPawns;
	private int blackPawn;

	public Track()
	{
		redPawns = new int[4];			/* numPlayers */

		for (int i = 0; i < 4; i++)			/* Need to get numPlayers, but where should GameState be initialized? */
			redPawns[i] = 0;

		blackPawn = 0;

		popeTokens[0] = new PopeToken(2, false);
		popeTokens[1] = new PopeToken(3, false);
		popeTokens[2] = new PopeToken(4, false);

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

	public boolean checkVaticanReport()
	{

		for (int i = 0; i < 4; i++)			/* Need numPlayers, 4 is temporary */
		{

			if (redPawns[i] >= 8  && popeTokens[0].isUsed() == false ||
				redPawns[i] >= 16 && popeTokens[1].isUsed() == false ||
				redPawns[i] == 24 && popeTokens[2].isUsed() == false )
				return true;

			else
				return false;
		}

		return false;
	}

	public void vaticanReport(Box popeBox)
	{

		for (int i = 0; i < 4; i++)		/* Need numPlayers */
		{
			if (popeBox.getPosition() == 8)                /* First pope box has been reached */
			{
				popeTokens[0].setUsed(true);

				if (redPawns[i] >= 5 && redPawns[i] <= 8)
				{
					// player[i].setVictoryPoints(player[i].getVictoryPoints() + popeTokens[0].getPoints());
				}

				return;
			}

			else if (popeBox.getPosition() == 16)        /* Second pope box */
			{
				popeTokens[1].setUsed(true);

				if (redPawns[i] >= 12 && redPawns[i] <= 16)
				{
					// player[i].setVictoryPoints(player[i].getVictoryPoints() + popeTokens[1].getPoints());
				}

				return;

			}

			else if (popeBox.getPosition() == 24)        /* Third pope box */
			{
				popeTokens[2].setUsed(true);

				if (redPawns[i] >= 19 && redPawns[i] <= 24)
				{
					// player[i].setVictoryPoints(player[i].getVictoryPoints() + popeTokens[2].getPoints());
				}

				return;
			}

			else
			{
				System.out.println("Error");
				return;
			}
		}
	}

	public Box[] getFaithTrack()
	{
		return faithTrack;
	}

	public void setFaithTrack(Box[] faithTrack)
	{
		this.faithTrack = faithTrack;
	}

	public int[] getRedPawns()
	{
		return redPawns;
	}

	public void setRedPawns(int[] redPawns)
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

