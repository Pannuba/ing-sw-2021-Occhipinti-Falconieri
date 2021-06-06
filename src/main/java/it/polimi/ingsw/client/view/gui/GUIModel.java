package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.*;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GUIModel implements Observer        /* Has gamestate, action instance, observes NetworkHandler */
{
	private final ActionGUI action;				/* ActionGUI instance to pass it the commands received by the NetworkHandler */
	private final String username;
	private final Event event;

	/* FIXME: create main view (and other views?) in game start to avoid NullPointerException if update() is called before a player chose the leader cards */

	public GUIModel(String username, LauncherController lc, NetworkHandler networkHandler, Event event) throws IOException		/* /a/14190310 on how to pass parameters to controllers */
	{
		this.username = username;
		this.event = event;

		FXMLLoader gameStartLoader = new FXMLLoader();
		gameStartLoader.setLocation(getClass().getResource("/scenes/gamestart.fxml"));
		Parent gameStartRoot = gameStartLoader.load();
		Scene gameStartScene = new Scene(gameStartRoot);

		FXMLLoader mainViewLoader = new FXMLLoader();
		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);

		FXMLLoader marketsLoader = new FXMLLoader();
		marketsLoader.setLocation(getClass().getResource("/scenes/markets.fxml"));		/* Put in createScenes()? */
		Parent marketsRoot = marketsLoader.load();
		Scene marketsScene = new Scene(marketsRoot);

		FXMLLoader leaderCardsLoader = new FXMLLoader();
		leaderCardsLoader.setLocation(getClass().getResource("/scenes/leadercards.fxml"));
		Parent leaderCardsRoot = leaderCardsLoader.load();
		Scene leaderCardsScene = new Scene(leaderCardsRoot);

		GameStartController gsc = gameStartLoader.getController();
		LeaderCardsController lcc = leaderCardsLoader.getController();
		MarketsController mc = marketsLoader.getController();
		MainViewController mvc = mainViewLoader.getController();

		gsc.setup(mainViewScene, mvc, networkHandler);
		lcc.setup(mainViewScene, networkHandler);
		mc.setup(mainViewScene, mvc, networkHandler);
		mvc.setup(marketsScene, leaderCardsScene, networkHandler, username);

		action = new ActionGUI(this, networkHandler, gameStartScene, lc, gsc, lcc, mvc, mc);
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in ActionGUI specified in the message */

		if (obj instanceof GameState)
			action.updateView((GameState) obj);		/* Or call updateTrack, updateStorage, updateMarkets... here? */
	}

	public String getUsername()
	{
		return username;
	}

	public Event getEvent()
	{
		return event;
	}
}
