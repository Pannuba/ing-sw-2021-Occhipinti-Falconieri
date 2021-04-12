package model.board;

import model.Resource;

public class Vault
{
	private int greyAmount;				/* Array? */
	private int yellowAmount;
	private int blueAmount;
	private int purpleAbout;

	public int getGreyAmount()
	{
		return greyAmount;
	}

	public void setGreyAmount(int greyAmount)
	{
		this.greyAmount = greyAmount;
	}

	public int getYellowAmount()
	{
		return yellowAmount;
	}

	public void setYellowAmount(int yellowAmount)
	{
		this.yellowAmount = yellowAmount;
	}

	public int getBlueAmount()
	{
		return blueAmount;
	}

	public void setBlueAmount(int blueAmount)
	{
		this.blueAmount = blueAmount;
	}

	public int getPurpleAbout()
	{
		return purpleAbout;
	}

	public void setPurpleAbout(int purpleAbout)
	{
		this.purpleAbout = purpleAbout;
	}
}
