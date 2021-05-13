package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.cli.CLI;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer
{
	private CLI view;
	private final NetworkHandler networkHandler;

	public Controller(NetworkHandler networkHandler)
	{
		this.networkHandler = networkHandler;
	}

	public void sendCommand(List<String> command)
	{
		networkHandler
	}

	@Override
	public void update(Observable observable, Object o)
	{

	}
}
