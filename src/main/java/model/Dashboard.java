package model;

// Import XML_Serialization??

public class Dashboard
{
	private String nickname;
	private Box[]track = new Box[25];
	private int redPawn;
	private int blackPawn;
	private Resource[]vault = new Resource[4];		/* 4 resources with an amount [0, inf] */
	private Storage storage;
	private LeaderCard leaderCardOne;
	private LeaderCard leaderCardTwo;
	private LeaderCard leaderCardThree;
	private DevCardArea devCard;
	private PopeToken popeToken;

	public Dashboard()
	{
		/* Server tells us what leader cards we get, we get four integers from which the user picks two */
		leaderCardOne = new LeaderCard();
		leaderCardTwo = new LeaderCard();


	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public Box[] getTrack()
	{
		return track;
	}

	public void setTrack(Box[] track)
	{
		this.track = track;
	}

	public int getRedPawn()
	{
		return redPawn;
	}

	public void setRedPawn(int redPawn)
	{
		this.redPawn = redPawn;
	}

	public int getBlackPawn()
	{
		return blackPawn;
	}

	public void setBlackPawn(int blackPawn)
	{
		this.blackPawn = blackPawn;
	}

	public Resource[] getVault()
	{
		return vault;
	}

	public void setVault(Resource[] vault)
	{
		this.vault = vault;
	}

	public Storage getStorage()
	{
		return storage;
	}

	public void setStorage(Storage storage)
	{
		this.storage = storage;
	}

	public LeaderCard getLeaderCardOne()
	{
		return leaderCardOne;
	}

	public void setLeaderCardOne(LeaderCard leaderCardOne)
	{
		this.leaderCardOne = leaderCardOne;
	}

	public LeaderCard getLeaderCardTwo()
	{
		return leaderCardTwo;
	}

	public void setLeaderCardTwo(LeaderCard leaderCardTwo)
	{
		this.leaderCardTwo = leaderCardTwo;
	}

	public LeaderCard getLeaderCardThree()
	{
		return leaderCardThree;
	}

	public void setLeaderCardThree(LeaderCard leaderCardThree)
	{
		this.leaderCardThree = leaderCardThree;
	}

	public DevCardArea getDevCard()
	{
		return devCard;
	}

	public void setDevCard(DevCardArea devCard)
	{
		this.devCard = devCard;
	}

	public PopeToken getPopeToken()
	{
		return popeToken;
	}

	public void setPopeToken(PopeToken popeToken)
	{
		this.popeToken = popeToken;
	}
}
