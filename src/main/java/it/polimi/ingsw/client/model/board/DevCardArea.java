package it.polimi.ingsw.client.model.board;

import it.polimi.ingsw.client.model.cards.DevCard;

public class DevCardArea
{
	private DevCard devCard;
	private int layer;

	public DevCard getDevCard()
	{
		return devCard;
	}

	public void setDevCard(DevCard devCard)
	{
		this.devCard = devCard;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
}
