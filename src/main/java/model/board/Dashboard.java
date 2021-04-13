package model.board;

import model.Resource;
import model.cards.LeaderCard;
import model.cards.PopeToken;
import tools.XML_Serialization;
import java.io.IOException;

public class Dashboard
{
	private String nickname;
	private Track track;
	private Vault vault;
	private Storage storage;
	private LeaderCard[] leaderCards = new LeaderCard[4];
	private DevCardArea[] devCardAreas = new DevCardArea[3];

	public Dashboard() throws IOException
	{
		/* Server tells us what leader cards we get, we get four integers from which the user picks two */

		XML_Serialization xmlDecode = new XML_Serialization();		/* Initialize leader/dev cards getting values from the respective XML files */
		leaderCards[0] = (LeaderCard) xmlDecode.deserialize("resources/leadercards/leadercard1.xml");
		/* ... */

	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public Track getTrack()
	{
		return track;
	}

	public void setTrack(Track track)
	{
		this.track = track;
	}

	public Vault getVault()
	{
		return vault;
	}

	public void setVault(Vault vault)
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
}
