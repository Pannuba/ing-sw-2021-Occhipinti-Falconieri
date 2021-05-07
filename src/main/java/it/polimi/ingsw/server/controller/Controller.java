package it.polimi.ingsw.server.controller;

/* TODO: add possible actions the client can do */

import it.polimi.ingsw.model.Model;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer
{
	private final Model model;

	public Controller(Model model)
	{
		System.out.println("Creating controller");
		this.model = model;
	}

	public void parseInput(String message)
	{
		if (message.equals("BUY_CARD"))
		{
			// change model
		}
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		parseInput((String)obj);
	}
}