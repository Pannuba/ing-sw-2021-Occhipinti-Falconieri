package model;

public class LeaderCard
{
	private int points;
	private String requirements;
	private Skill skill;

	public LeaderCard()				/* For some reason, XML_Serialization throws exceptions without this */
	{

	}

	public LeaderCard(Skill skill)			/* Is this where we put the difference between skills? Where else? */
	{
		switch (skill)
		{
			case SKILL_ONE:
				break;

			case SKILL_TWO:
				break;

			case SKILL_THREE:
				break;

			case SKILL_FOUR:
				break;

			default:
				break;

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
