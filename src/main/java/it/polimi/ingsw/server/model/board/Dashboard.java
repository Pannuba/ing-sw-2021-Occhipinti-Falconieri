package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.tools.XML_Serialization;
import java.io.IOException;

public class Dashboard
{
	private Vault vault;
	private Storage storage;
	private DevCardArea[] devCardAreas = new DevCardArea[3];

	public Dashboard() throws IOException
	{
		/* Server tells us what leader cards we get, we get four integers from which the user picks two */

		/* ... */

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
