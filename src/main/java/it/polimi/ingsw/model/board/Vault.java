package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;

public class Vault implements Serializable
{
	private int[] resourceAmounts = new int[4];		/* 0: blue, 1: grey, 2: yellow, 3: purple */

	public Vault()
	{
		resourceAmounts[0] = 0;
		resourceAmounts[1] = 0;
		resourceAmounts[2] = 0;
		resourceAmounts[3] = 0;
	}

	public void addResource(ResourceType resourceToAdd)
	{
		switch (resourceToAdd)
		{
			case BLUE:
				resourceAmounts[0]++;
				break;

			case GREY:
				resourceAmounts[1]++;
				break;

			case YELLOW:
				resourceAmounts[2]++;
				break;

			case PURPLE:
				resourceAmounts[3]++;
				break;

			default:
				System.out.println("Vault: addResource error");
				break;
		}

		return;
	}

	public int getTotalResources()
	{
		int totalResources = 0;

		for (int i = 0; i < 4; i++)
			totalResources += resourceAmounts[i];

		return totalResources;
	}

	public int[] getResourceAmounts()
	{
		return resourceAmounts;
	}

	public void setResourceAmounts(int[] resourceAmounts)
	{
		this.resourceAmounts = resourceAmounts;
	}
}
