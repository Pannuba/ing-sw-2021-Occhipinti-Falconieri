package it.polimi.ingsw.client.model.cards;

public abstract class LeaderCard
{
	private int cardNumber;
	private int points;
	private String requirements;
	private boolean isDiscarded;

	public int getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(int cardNumber)
	{
		this.cardNumber = cardNumber;
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

	public boolean isDiscarded()
	{
		return isDiscarded;
	}

	public void setDiscarded(boolean discarded)
	{
		isDiscarded = discarded;
	}
}
