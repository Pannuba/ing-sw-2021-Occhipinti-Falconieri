package model.board;

import model.Resource;

public class Vault
{
	private int[] resourceAmounts = new int[4];		/* 0: grey, 1: yellow, 2: blue, 3: purple */

	public int[] getResourceAmounts()
	{
		return resourceAmounts;
	}

	public void setResourceAmounts(int[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}
}
