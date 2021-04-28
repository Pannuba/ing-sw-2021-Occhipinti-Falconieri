package it.polimi.ingsw.client.model.board;

public class Dashboard
{
	private Vault vault;
	private Storage storage;
	private DevCardArea[] devCardAreas = new DevCardArea[3];

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

	public DevCardArea[] getDevCardAreas()
	{
		return devCardAreas;
	}

	public void setDevCardAreas(DevCardArea[] devCardAreas)
	{
		this.devCardAreas = devCardAreas;
	}
}
