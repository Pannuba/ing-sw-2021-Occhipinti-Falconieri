package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The MarblesMarket created in Model
 *
 * 13 total marbles, 12 in matrix and 1 spare: 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
 *
 * O O O O <		Spare marble is pushed from the end of rows and columns
 * O O O O <
 * O O O O	<		[row][col]
 * ^ ^ ^ ^
 *
 * [0][0]	[0][1]	[0][2]	[0][3]
 *
 * [1][0]	[1][1]	[1][2]	[1][3]
 *
 * [2][0]	[2][1]	[2][2]	[2][3]
 *
 * @author Giulio Occhipinti
 */

public class MarblesMarket implements Serializable
{
	private Marble[][] marblesBoard = new Marble[3][4];
	private Marble spareMarble;

	/**
	 * Empty constructor required by the "persistence" advanced feature
	 */

	public MarblesMarket()
	{

	}

	/**
	 * Creates the marbles grid and spare marble. Uses a list of strings where each character corresponds to a MarbleType.
	 * The list is shuffled and then iterated by two nested for loops which assign the converted MarbleTypes to each slot in the matrix.
	 * The strings are converted to MarbleType by using MarbleType's method convertStringToMarbleType
	 */

	public void create()
	{
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

	/**
	 * Called by the controller when the user wants to buy from the MarblesMarket and selects a row
	 * @param row the chosen row's number (1-indexed, between 1 and 3)
	 * @return the list of MarbleTypes that were on the selected row
	 */

	public List<MarbleType> buyMarblesRow(int row)			/* Returns a list of marbletypes to the controller, which then acts for each marble */
	{
		List<MarbleType> boughtMarbles = new ArrayList<>();

		for (int i = 0; i < 4; i++)
			boughtMarbles.add(marblesBoard[row][i].getMarbleType());

		Marble oldSpareMarble = spareMarble;					/* Changes the marbles grid pushing the spare marble in */
		spareMarble = marblesBoard[row][0];

		for (int i = 0; i < 3; i++)
			marblesBoard[row][i] = marblesBoard[row][i+1];

		marblesBoard[row][3] = oldSpareMarble;

		return boughtMarbles;
	}

	/**
	 * Called by the controller when the user wants to buy from the MarblesMarket and selects a column
	 * @param col the chosen row's number (1-indexed, between 1 and 4)
	 * @return the list of MarbleTypes that were on the selected column
	 */

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
