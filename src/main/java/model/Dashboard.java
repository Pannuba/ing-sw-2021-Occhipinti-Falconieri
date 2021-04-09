package model;

import tools.XML_Serialization;

public class Dashboard
{
	private String nickname;
	private Box[]track = new Box[25];
	private int redPawn;
	private int blackPawn;
	private Resource[]vault = new Resource[4];		/* 4 resources with an amount [0, inf] */
	private Storage storage;
	private LeaderCard[]leaderCards = new LeaderCard[4];
	private DevCardArea[]devCardAreas = new DevCardArea[3];
	private PopeToken[]popeToken = new PopeToken[3];

	public Dashboard()
	{
		/* Server tells us what leader cards we get, we get four integers from which the user picks two */

		XML_Serialization xmlDecode = new XML_Serialization();		/* Initialize leader/dev cards getting values from the respective XML files */
		leaderCards[0] = xmlDecode.deserialize("resources/leadercards/leadercard1.xml");
		leaderCards[1] = new LeaderCard();						/* Should this go in LeaderCard constructor? */
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

	public LeaderCard[] getLeaderCards()
	{
		return leaderCards;
	}

	public void setLeaderCards(LeaderCard[] leaderCards)
	{
		this.leaderCards = leaderCards;
	}

	public DevCardArea[] getDevCardAreas()
	{
		return devCardAreas;
	}

	public void setDevCardAreas(DevCardArea[] devCardAreas)
	{
		this.devCardAreas = devCardAreas;
	}

	public PopeToken[] getPopeToken()
	{
		return popeToken;
	}

	public void setPopeToken(PopeToken[] popeToken)
	{
		this.popeToken = popeToken;
	}
}
