package model.cards;

import model.Resource;

public abstract class LeaderCard		/* Can't do new LeaderCard(); because it's abstract */
{
	private int points;
	private String requirements;		/* Can be devcards or resources, so it's a string that will be decoded elsewhere */

	public LeaderCard()				/* For some reason, XML_Serialization throws exceptions without this */
	{

	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public String getRequirements()
	{
		return requirements;
	}

	public void setRequirements(String requirements)
	{
		this.requirements = requirements;
	}
}
