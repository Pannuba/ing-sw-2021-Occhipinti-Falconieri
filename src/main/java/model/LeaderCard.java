package model;

public class LeaderCard
{
	private int points;
	private String requirements;
	private Skills skill;

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

	public Skills getSkill()
	{
		return skill;
	}

	public void setSkill(Skills skill)
	{
		this.skill = skill;
	}
}
