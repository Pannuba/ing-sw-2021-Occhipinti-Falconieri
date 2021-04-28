package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Shelf;

public class SkillStorage extends LeaderCard
{
	private Shelf additionalStorage;		/* shelfSize always 2, initial quantity 0 */

	public SkillStorage()		/* Put constructor or setQuantity(0) in xml? Can LeaderCard objects be created if it's abstract? */
	{
		additionalStorage.getShelfResource().setQuantity(0);
	}

	public Shelf getAdditionalStorage()
	{
		return additionalStorage;
	}

	public void setAdditionalStorage(Shelf additionalStorage)
	{
		this.additionalStorage = additionalStorage;
	}
}
