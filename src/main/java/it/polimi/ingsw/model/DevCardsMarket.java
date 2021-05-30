package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.DevCardColor;
import it.polimi.ingsw.util.XML_Serialization;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevCardsMarket implements Serializable
{
	private List<DevCard> allDevCards;
	private List<List<DevCard>> devCardStacks;			/* TODO: adjust PrintMethods, ActionDevCard and client-server actions */

	public DevCardsMarket()
	{
		System.out.println("Creating devcards...");
		allDevCards = new ArrayList<>();
		devCardStacks = new ArrayList<>();

		for (int i = 0; i < 48; i++)
		{
			try
			{
				allDevCards.add((DevCard) XML_Serialization.deserialize("src/main/resources/xml/devcards/devcard" + (i + 1) + ".xml"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		List<DevCard> tempList;				/* For each devcard color, make 3 stacks of devcards for a total of 12 stacks */

		for (int j = 0; j < 3; j++)
		{
			tempList = new ArrayList<>();

			for (int i = 0; i < 48; i++)
			{
				if (allDevCards.get(i).getColor() == DevCardColor.GREEN && allDevCards.get(i).getLevel() == j)
					tempList.add(allDevCards.get(i));
			}

			devCardStacks.add(tempList);
		}

		for (int j = 0; j < 3; j++)
		{
			tempList = new ArrayList<>();

			for (int i = 0; i < 48; i++)
			{
				if (allDevCards.get(i).getColor() == DevCardColor.GREEN && allDevCards.get(i).getLevel() == j)
					tempList.add(allDevCards.get(i));
			}

			devCardStacks.add(tempList);
		}

		for (int j = 0; j < 3; j++)
		{
			tempList = new ArrayList<>();

			for (int i = 0; i < 48; i++)
			{
				if (allDevCards.get(i).getColor() == DevCardColor.GREEN && allDevCards.get(i).getLevel() == j)
					tempList.add(allDevCards.get(i));
			}

			devCardStacks.add(tempList);
		}

		for (int j = 0; j < 3; j++)
		{
			tempList = new ArrayList<>();

			for (int i = 0; i < 48; i++)
			{
				if (allDevCards.get(i).getColor() == DevCardColor.GREEN && allDevCards.get(i).getLevel() == j)
					tempList.add(allDevCards.get(i));
			}

			devCardStacks.add(tempList);
		}

		for (int i = 0; i < devCardStacks.size(); i++)			/* Shuffle each stack */
			Collections.shuffle(devCardStacks.get(i));
	}

	public DevCard buyCardFromMarket(int boughtCardNum)
	{
		System.out.println("DevCardMarket: buying card #" + boughtCardNum);
		DevCard boughtCard = getDevCardByNumber(boughtCardNum);
		allDevCards.remove(boughtCard);

		return boughtCard;
	}

	public DevCard getDevCardByNumber(int cardNumber)
	{
		for (int i = 0; i < allDevCards.size(); i++)
		{
			if (allDevCards.get(i).getCardNumber() == cardNumber)
				return allDevCards.get(i);
		}

		return null;
	}

	public List<DevCard> getAllDevCards()
	{
		return allDevCards;
	}

	public void setAllDevCards(List<DevCard> allDevCards)
	{
		this.allDevCards = allDevCards;
	}
}
