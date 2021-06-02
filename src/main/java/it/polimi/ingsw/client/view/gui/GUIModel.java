package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;

import java.util.Observable;
import java.util.Observer;

public class GUIModel implements Observer        /* Has gamestate, action instance, observes NetworkHandler */
{
	private final ActionGUI action;				/* ActionGUI instance to pass it the commands received by the NetworkHandler */
	private GameState gameState;				/* Local gamestate accessed by action and scenes through get method */

	public GUIModel(ActionGUI action)
	{
		this.action = action;
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in cli specified in the message */

		if (obj instanceof GameState)
			this.gameState = (GameState) obj;		/* Gamestate is needed in game loop, not during setup */
	}

	public GameState getGameState()
	{
		return gameState;
	}
}
