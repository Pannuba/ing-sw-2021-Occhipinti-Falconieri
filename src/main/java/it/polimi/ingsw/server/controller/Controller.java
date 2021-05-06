package it.polimi.ingsw.server.controller;

/*	InputParser class that converts messages from client to methods calls in controller */

/* TODO: add possible actions the client can do */

import it.polimi.ingsw.model.Model;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer
{
	private final Model model;

	public Controller(Model model)
	{
		this.model = model;
	}

	public void parseInput(String message)
	{
		if (message.equals("COMPRA_CARTA"))
		{
			// ...
		}
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		parseInput((String)obj);
	}
}