public class DevCardArea extends DevCard
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

	@Override
	public int getLayer()
	{
		return layer;
	}

	@Override
	public void setLayer(int layer)
	{
		this.layer = layer;
	}
}
