package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

public class MarblesMarket implements Serializable
{
	private final Marble[][] marblesBoard = new Marble[3][4];			/* Final? but it keeps changing */
	private Marble spareMarble;

	public MarblesMarket()		/* TODO: tests */
	{
		System.out.println("Creating marbles market");
		int[] marblesToAssign = {4, 2, 2, 2, 2, 1};
		MarbleType[] resourcesToAssign = new MarbleType[13];
		spareMarble = new Marble();

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

		for (int i = 0; i < 13;  /**/  )
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

	public List<Marble> buyMarblesRow(int row)			/* Returns a list of marbles to the controller, which then acts for each marble */
	{
		List<Marble> boughtMarbles = new ArrayList<>();			/* Can be significantly simplified with a hashmap */

		for (int i = 0; i < 6; i++)		/* Create one resource for each type. 0 -> blue, 1 -> grey, 2 -> yellow, 3 -> purple */
		{
			boughtMarbles.add(new Marble());		/* Quantity is set to 0 in constructor */
		}

		boughtMarbles.get(0).setMarbleType(MarbleType.RED);			/* The marble object itself isn't needed, only the amount of each marble */
		boughtMarbles.get(1).setMarbleType(MarbleType.WHITE);
		boughtMarbles.get(2).setMarbleType(MarbleType.BLUE);
		boughtMarbles.get(3).setMarbleType(MarbleType.GREY);
		boughtMarbles.get(4).setMarbleType(MarbleType.YELLOW);
		boughtMarbles.get(5).setMarbleType(MarbleType.PURPLE);

		for (int i = 0; i < 3; i++)
		{
			switch(marblesBoard[row][i].getMarbleType())
			{
				case RED:
					boughtMarbles.get(0).setQuantity(boughtMarbles.get(0).getQuantity() + 1);
					break;

				case WHITE:
					boughtMarbles.get(1).setQuantity(boughtMarbles.get(1).getQuantity() + 1);
					break;

				case BLUE:
					boughtMarbles.get(2).setQuantity(boughtMarbles.get(2).getQuantity() + 1);
					break;

				case GREY:
					boughtMarbles.get(3).setQuantity(boughtMarbles.get(3).getQuantity() + 1);
					break;

				case YELLOW:
					boughtMarbles.get(4).setQuantity(boughtMarbles.get(4).getQuantity() + 1);
					break;

				case PURPLE:
					boughtMarbles.get(5).setQuantity(boughtMarbles.get(5).getQuantity() + 1);
					break;

				default:
					break;
			}
		}

		for (int i = 0; i < boughtMarbles.size(); i++)			/* Remove marble types that haven't been bought even once */
		{
			if (boughtMarbles.get(i).getQuantity() == 0)
			{
				boughtMarbles.remove(i);
				i--;
			}
		}

		Marble oldSpareMarble = spareMarble;					/* Changes the marbles grid pushing the spare marble in */
		spareMarble = marblesBoard[row][0];

		for (int i = 0; i < 3; i++)
			marblesBoard[row][i] = marblesBoard[row][i+1];

		marblesBoard[row][3] = oldSpareMarble;

		return boughtMarbles;
	}

	public List<Marble> buyMarblesCol(int col)
	{
		List<Marble> boughtMarbles = new ArrayList<>();

		for (int i = 0; i < 6; i++)		/* Create one resource for each type. 0 -> blue, 1 -> grey, 2 -> yellow, 3 -> purple */
		{
			boughtMarbles.add(new Marble());		/* Quantity is set to 0 in constructor */
		}

		boughtMarbles.get(0).setMarbleType(MarbleType.RED);
		boughtMarbles.get(1).setMarbleType(MarbleType.WHITE);
		boughtMarbles.get(2).setMarbleType(MarbleType.BLUE);
		boughtMarbles.get(3).setMarbleType(MarbleType.GREY);
		boughtMarbles.get(4).setMarbleType(MarbleType.YELLOW);
		boughtMarbles.get(5).setMarbleType(MarbleType.PURPLE);

		for (int i = 0; i < 3; i++)
		{
			switch(marblesBoard[i][col].getMarbleType())
			{
				case RED:
					boughtMarbles.get(0).setQuantity(boughtMarbles.get(0).getQuantity() + 1);
					break;

				case WHITE:
					boughtMarbles.get(1).setQuantity(boughtMarbles.get(1).getQuantity() + 1);
					break;

				case BLUE:
					boughtMarbles.get(2).setQuantity(boughtMarbles.get(2).getQuantity() + 1);
					break;

				case GREY:
					boughtMarbles.get(3).setQuantity(boughtMarbles.get(3).getQuantity() + 1);
					break;

				case YELLOW:
					boughtMarbles.get(4).setQuantity(boughtMarbles.get(4).getQuantity() + 1);
					break;

				case PURPLE:
					boughtMarbles.get(5).setQuantity(boughtMarbles.get(5).getQuantity() + 1);
					break;

				default:
					break;
			}
		}

		for (int i = 0; i < boughtMarbles.size(); i++)			/* Remove marble types that haven't been bought even once */
		{
			if (boughtMarbles.get(i).getQuantity() == 0)
			{
				boughtMarbles.remove(i);
				i--;
			}
		}

		Marble oldSpareMarble = spareMarble;
		spareMarble = marblesBoard[0][col];

		for (int i = 0; i < 2; i++)
			marblesBoard[i][col] = marblesBoard[i+1][col];

		marblesBoard[2][col] = oldSpareMarble;

		return boughtMarbles;
	}

	public Marble[][] getMarblesBoard()
	{
		return marblesBoard;
	}
}
