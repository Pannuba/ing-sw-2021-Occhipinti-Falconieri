package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.NetworkHandler;

import java.util.Observable;
import java.util.Observer;

public abstract class View implements Observer
{
	protected MessageExecutor action;

	protected NetworkHandler networkHandler;

	@Override
	public abstract void update(Observable obs, Object obj);
}
