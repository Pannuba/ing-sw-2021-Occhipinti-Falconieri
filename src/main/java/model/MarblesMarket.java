package model;

/*	13 total marbles:
	4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red

	Hardcode or xml file for marble amounts? Need to change grid size if so
*/

/* TODO: Marble class. If we use Resource the quantity field is pointless, and there's no clean way to add the white marble */

import java.util.concurrent.ThreadLocalRandom;

public class MarblesMarket
{
	private Resource[][] marblesBoard = new Resource[3][4];
	private Resource spareMarble;

	public MarblesMarket()
	{
		int[] marblesToAssign = new int[5];
		ResourceType[] resourcesToAssign = new ResourceType[5];

		marblesToAssign[0] = 4;
		marblesToAssign[1] = 2;
		marblesToAssign[2] = 2;
		marblesToAssign[3] = 2;
		marblesToAssign[4] = 2;
		marblesToAssign[5] = 1;

		switch (ThreadLocalRandom.current().nextInt(0, 5))			/* fill resourcesToAssign[], maybe use a while. Last one is spareMarble */
		{
				case 0:

					if (marblesToAssign[0] == 0)
					{
						i--;	/* So it iterates another time, doesn't count because we ran out of white marbles */
						break;
					}
					marblesToAssign[0]--;
					//resourceToAssign = ResourceType.WHITE;
					break;

				case 1:
					marblesToAssign[1]--;
					resourceToAssign = ResourceType.BLUE;
					break;

				case 2:
					marblesToAssign[2]--;
					// ...

				case 3:

				case 4:

				case 5:

				default:
					break;
			}

		for (int i = 0; i < 13; i++)
		{
			if (i == 12)		/* TODO: test this in a separate program , should exit all for loops because the last marble to assign is the spare one */
			{
				spareMarble.setResourceType(resourcesToAssign[i]);
				break;
			}

			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 4; k++)
				{
					marblesBoard[j][k] = new Resource();
					marblesBoard[j][k].setResourceType(resourcesToAssign[i]);        /* Need to use MarbleType enum and Marble class */
				}
			}
		}

	}

	public Resource[][] getMarblesBoard()
	{
		return marblesBoard;
	}

	public void setMarblesBoard(Resource[][] marblesBoard)
	{
		this.marblesBoard = marblesBoard;
	}

	public Resource getSpareMarble()
	{
		return spareMarble;
	}

	public void setSpareMarble(Resource spareMarble)
	{
		this.spareMarble = spareMarble;
	}
}
