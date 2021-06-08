package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*	13 total marbles, 12 in matrix and 1 spare:
	4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red

	Hardcode or xml file for marble amounts? Need to change grid size if so


	O O O O <		Spare marble is pushed from the end of rows and columns
	O O O O <
	O O O O	<		[row][col]
	^ ^ ^ ^

	[0][0]	[0][1]	[0][2]	[0][3]

	[1][0]	[1][1]	[1][2]	[1][3]

	[2][0]	[2][1]	[2][2]	[2][3]

*/

public class MarblesMarket implements Serializable
{
	private Marble[][] marblesBoard = new Marble[3][4];
	private Marble spareMarble;

	public MarblesMarket()
	{

	}

	public void create()
	{
		System.out.println("Creating marbles market...");

		List<String> marblesToAssign = Arrays.asList("W", "W", "W", "W", "B", "B", "G", "G", "P", "P", "Y", "Y", "R");
		Collections.shuffle(marblesToAssign);

		spareMarble = new Marble();		/* spareMarble is the first marbleToAssign, 0 */
		spareMarble.setMarbleType(MarbleType.convertStringToMarbleType(marblesToAssign.get(0)));

		int i = 1;		/* Other marbles to assign are [1, 12] */

		for (int j = 0; j < 3; j++)
		{
			for (int k = 0; k < 4; k++)
			{
				marblesBoard[j][k] = new Marble();
				marblesBoard[j][k].setMarbleType(MarbleType.convertStringToMarbleType(marblesToAssign.get(i)));
				i++;
			}
		}
	}

	public List<MarbleType> buyMarblesRow(int row)			/* Returns a list of marbletypes to the controller, which then acts for each marble */
	{
		List<MarbleType> boughtMarbles = new ArrayList<>();			/* Can be significantly simplified with a hashmap */

		for (int i = 0; i < 4; i++)
			boughtMarbles.add(marblesBoard[row][i].getMarbleType());

		Marble oldSpareMarble = spareMarble;					/* Changes the marbles grid pushing the spare marble in */
		spareMarble = marblesBoard[row][0];

		for (int i = 0; i < 3; i++)
			marblesBoard[row][i] = marblesBoard[row][i+1];

		marblesBoard[row][3] = oldSpareMarble;

		return boughtMarbles;
	}

	public List<MarbleType> buyMarblesCol(int col)
	{
		List<MarbleType> boughtMarbles = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			boughtMarbles.add(marblesBoard[i][col].getMarbleType());

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

	public void setMarblesBoard(Marble[][] marblesBoard)		/* For deserialization when matches are recovered */
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
