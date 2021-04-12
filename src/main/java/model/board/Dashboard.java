package model.board;

import model.Resource;
import model.cards.LeaderCard;
import model.cards.PopeToken;
import tools.XML_Serialization;
import java.io.IOException;

public class Dashboard
{
	private String nickname;
	private Box[]track = new Box[25];
	private int redPawn;
	private int blackPawn;
	private Vault vault;
	private Storage storage;
	private LeaderCard[]leaderCards = new LeaderCard[4];
	private DevCardArea[]devCardAreas = new DevCardArea[3];
	private PopeToken[]popeToken = new PopeToken[3];

	public Dashboard() throws IOException
	{
		/* Server tells us what leader cards we get, we get four integers from which the user picks two */

		XML_Serialization xmlDecode = new XML_Serialization();		/* Initialize leader/dev cards getting values from the respective XML files */
		leaderCards[0] = (LeaderCard) xmlDecode.deserialize("resources/leadercards/leadercard1.xml");
		/* ... */

		redPawn = 0;
		blackPawn = 0;


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
