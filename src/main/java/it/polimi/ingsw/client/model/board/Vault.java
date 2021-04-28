package it.polimi.ingsw.client.model.board;

public class Vault
{
	private int[] resourceAmounts = new int[4];

	public int[] getResourceAmounts()
	{
		return resourceAmounts;
	}

	public void setResourceAmounts(int[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}
}
