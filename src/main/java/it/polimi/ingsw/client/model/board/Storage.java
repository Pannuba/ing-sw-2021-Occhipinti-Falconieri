package it.polimi.ingsw.client.model.board;

import it.polimi.ingsw.client.model.Shelf;

public class Storage
{
	private Shelf[] shelves = new Shelf[3];

	public Shelf[] getShelves()
	{
		return shelves;
	}

	public void setShelves(Shelf[] shelves)
	{
		this.shelves = shelves;
	}
}
