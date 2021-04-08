package model;

public class LeaderCard
{
	private int points;
	private String requirements;
	private Skill skill;

	public LeaderCard()			/* Is this where we put the difference between skills? Where else? */
	{
		switch (skill)
		{
			case SKILL_ONE:

			case SKILL_TWO:

			case SKILL_THREE:

			case SKILL_FOUR:

		}
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

	public Skill getSkill()
	{
		return skill;
	}

	public void setSkill(Skill skill)
	{
		this.skill = skill;
	}
}
