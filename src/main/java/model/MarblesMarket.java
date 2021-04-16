package model;

/*	13 total marbles, 12 in matrix and 1 spare:
	4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red

	Hardcode or xml file for marble amounts? Need to change grid size if so
*/

/*	O O O O <		Spare marble is pushed from the end of rows and columns
	O O O O <
	O O O O	<		[row][col]
	^ ^ ^ ^

	[0][0]	[0][1]	[0][2]	[0][3]

	[1][0]	[1][1]	[1][2]	[1][3]

	[2][0]	[2][1]	[2][2]	[2][3]

 */

import java.util.concurrent.ThreadLocalRandom;

public class MarblesMarket
{
	private Marble[][] marblesBoard = new Marble[3][4];
	private Marble spareMarble;

	public MarblesMarket()		/* TODO: tests */
	{
		int[] marblesToAssign = {4, 2, 2, 2, 2, 1};
		MarbleType[] resourcesToAssign = new MarbleType[13];

		for (int i = 0; i < 13; i++)
		{
			switch (ThreadLocalRandom.current().nextInt(0, 5+1))            /* 5+1 because bound is not included */
			{
				case 0:

					if (marblesToAssign[0] == 0)
					{
						i--;    /* So it iterates another time, doesn't count because we ran out of white marbles */
						break;
					}

					marblesToAssign[0]--;
					resourcesToAssign[i] = MarbleType.WHITE;
					break;

				case 1:

					if (marblesToAssign[1] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[1]--;
					resourcesToAssign[i] = MarbleType.BLUE;
					break;

				case 2:

					if (marblesToAssign[2] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[2]--;
					resourcesToAssign[i] = MarbleType.GREY;
					break;

				case 3:

					if (marblesToAssign[3] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[3]--;
					resourcesToAssign[i] = MarbleType.PURPLE;
					break;

				case 4:

					if (marblesToAssign[4] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[4]--;
					resourcesToAssign[i] = MarbleType.YELLOW;
					break;

				case 5:

					if (marblesToAssign[5] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[5]--;
					resourcesToAssign[i] = MarbleType.RED;
					break;

				default:
					break;
			}
		}

		for (int i = 0; i < 13;   )
		{
			if (i == 12)		/* TODO: test this in a separate program , should exit all for loops because the last marble to assign is the spare one */
			{
				spareMarble.setMarbleType(resourcesToAssign[i]);
				break;
			}

			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 4; k++)
				{
					marblesBoard[j][k] = new Marble();
					marblesBoard[j][k].setMarbleType(resourcesToAssign[i]);        /* Need to use MarbleType enum and Marble class */
					i++;
				}
			}
		}

	}

	public void pushSpareMarbleRow(int row)			/* Not tested yet, also Col */
	{
		Marble oldSpareMarble = spareMarble;
		spareMarble = marblesBoard[row][0];

		for (int i = 0; i < 3; i++)
			marblesBoard[row][i] = marblesBoard[row][i+1];

		marblesBoard[row][3] = oldSpareMarble;

	}

	public void pushSpareMarbleCol(int col)
	{
		Marble oldSpareMarble = spareMarble;
		spareMarble = marblesBoard[0][col];

		for (int i = 0; i < 2; i++)
			marblesBoard[i][col] = marblesBoard[i+1][col];

		marblesBoard[2][col] = oldSpareMarble;
	}

	public Marble[][] getMarblesBoard()
	{
		return marblesBoard;
	}

	public void setMarblesBoard(Marble[][] marblesBoard)
	{
		this.marblesBoard = marblesBoard;
	}

	public Marble getSpareMarble()
	{
		return spareMarble;
	}

	public void setSpareMarble(Marble spareMarble)
	{
		this.spareMarble = spareMarble;
	}
}
