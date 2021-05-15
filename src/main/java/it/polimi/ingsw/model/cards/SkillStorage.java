package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.Shelf;

public class SkillStorage extends LeaderCard
{
	private Shelf additionalStorage;		/* shelfSize always 2, initial quantity 0 */
	private Resource cost;					/* Requires 5 resources of the same type. 5 can be hardcoded in constructor, but I put it in the .xmls */

	public SkillStorage()			/* Can LeaderCard objects be created if it's abstract? */
	{
		cost = new Resource();		/* May not be needed, cost is assigned an already initialized Resource object during the deserialization */
		additionalStorage = new Shelf(2);
	}

	public Shelf getAdditionalStorage()
	{
		return additionalStorage;
	}

	public void setAdditionalStorage(Shelf additionalStorage)
	{
		this.additionalStorage = additionalStorage;
	}

	public Resource getCost()
	{
		return cost;
	}

	public void setCost(Resource cost)
	{
		this.cost = cost;
	}
}
