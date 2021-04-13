package model;

/*	13 total marbles, 12 in matrix and 1 spare:
	4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red

	Hardcode or xml file for marble amounts? Need to change grid size if so
*/

/* TODO: Marble class. If we use Resource the quantity field is pointless, and there's no clean way to add the white marble */

import java.util.concurrent.ThreadLocalRandom;

public class MarblesMarket
{
	private Resource[][] marblesBoard = new Resource[3][4];
	private Resource spareMarble;

	public MarblesMarket()		/* TODO: tests */
	{
		int[] marblesToAssign = {4, 2, 2, 2, 2, 1};
		ResourceType[] resourcesToAssign = new ResourceType[13];

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
					//resourceToAssign[i] = ResourceType.WHITE;
					break;

				case 1:

					if (marblesToAssign[1] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[1]--;
					resourcesToAssign[i] = ResourceType.BLUE;
					break;

				case 2:

					if (marblesToAssign[2] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[2]--;
					resourcesToAssign[i] = ResourceType.GREY;
					break;

				case 3:

					if (marblesToAssign[3] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[3]--;
					resourcesToAssign[i] = ResourceType.PURPLE;
					break;

				case 4:

					if (marblesToAssign[4] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[4]--;
					resourcesToAssign[i] = ResourceType.YELLOW;
					break;

				case 5:

					if (marblesToAssign[5] == 0)
					{
						i--;
						break;
					}
					marblesToAssign[5]--;
					resourcesToAssign[i] = ResourceType.RED;
					break;

				default:
					break;
			}
		}

		for (int i = 0; i < 13;   )
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
					i++;
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
