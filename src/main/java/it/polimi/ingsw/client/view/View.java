package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.MessageIO;

import java.util.Observable;
import java.util.Observer;

public abstract class View implements Observer
{
	protected MessageExecutor action;

	protected MessageIO messageHandler;

	@Override
	public abstract void update(Observable obs, Object obj);
}
