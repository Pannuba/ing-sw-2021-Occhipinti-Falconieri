package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * MarbleMarket test
 * @author Chiara Falconieri
 */

public class MarblesMarketTest {

	private MarblesMarket marblesMarket;
	private List<MarbleType> boughtMarbles = new ArrayList<>();
	private Marble[][] marblesBoard = new Marble[3][4];
	private Marble spareMarble;
	private Marble oldSpareMarble;

	/**
	 * Buy first row and check bought marbles, old spare marble and new spare marble
	 * buyMarbleRow method
	 */

	@Test
	public void buyMarblesRow()
	{
		marblesMarket = new MarblesMarket();
		marblesBoard = marblesMarket.getMarblesBoard();
		for (int i = 0; i < 4; i++)
			boughtMarbles.add(marblesBoard[0][i].getMarbleType());
		spareMarble = new Marble();
		spareMarble = marblesBoard[0][0];
		oldSpareMarble = new Marble();
		oldSpareMarble = marblesMarket.getSpareMarble();
		assertEquals("Error", boughtMarbles, marblesMarket.buyMarblesRow(0));
		assertEquals("Error", spareMarble, marblesMarket.getSpareMarble());
		assertEquals("Error", oldSpareMarble, marblesBoard[0][3]);
	}

	/**
	 * Buy fourth column and check bought marbles, old spare marble and new spare marble
	 * buyMarbleCol method
	 */


	@Test
	public void buyMarblesCol()
	{
		marblesMarket = new MarblesMarket();
		marblesBoard = marblesMarket.getMarblesBoard();
		for (int i = 0; i < 3; i++)
			boughtMarbles.add(marblesBoard[i][3].getMarbleType());
		spareMarble = new Marble();
		spareMarble = marblesBoard[0][3];
		oldSpareMarble = new Marble();
		oldSpareMarble = marblesMarket.getSpareMarble();
		assertEquals("Error", boughtMarbles, marblesMarket.buyMarblesCol(3));
		assertEquals("Error", spareMarble, marblesMarket.getSpareMarble());
		assertEquals("Error", oldSpareMarble, marblesBoard[2][3]);
	}
}