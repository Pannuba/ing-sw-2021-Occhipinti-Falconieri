package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.localmatch.LocalMatchIO;
import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.localmatch.LocalMatch;
import it.polimi.ingsw.client.localmatch.LocalModel;
import it.polimi.ingsw.client.localmatch.controller.LocalController;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.controllers.*;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.messages.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends View        /* Has gamestate, action instance, observes NetworkHandler */
{
	private ActionGUI action;				/* ActionGUI instance to pass it the commands received by the NetworkHandler */
	private final String username;
	private final LoginController lc;
	private MessageIO messageHandler;
	private final Stage mainStage;

	/* FIXME: create main view (and other views?) in game start to avoid NullPointerException if update() is called before a player chose the leader cards */

	public GUI(String username, LoginController lc, Stage mainStage)		/* /a/14190310 on how to pass parameters to controllers */
	{
		this.mainStage = mainStage;
		this.username = username;
		this.lc = lc;
	}

	public synchronized void update(Object obj)
	{
		System.out.println("GUI: Received " + obj.getClass().getSimpleName());
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in ActionGUI specified in the message */

		if (obj instanceof GameState)
			action.updateView((GameState) obj);		/* Or call updateTrack, updateStorage, updateMarkets... here? */
	}

	public void createScenes() throws IOException
	{
		FXMLLoader gameStartLoader = new FXMLLoader();
		gameStartLoader.setLocation(getClass().getResource("/scenes/gamestart.fxml"));
		Parent gameStartRoot = gameStartLoader.load();
		Scene gameStartScene = new Scene(gameStartRoot);

		FXMLLoader mainViewLoader = new FXMLLoader();
		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);

		FXMLLoader marketsLoader = new FXMLLoader();
		marketsLoader.setLocation(getClass().getResource("/scenes/markets.fxml"));
		Parent marketsRoot = marketsLoader.load();
		Scene marketsScene = new Scene(marketsRoot);

		FXMLLoader leaderCardsLoader = new FXMLLoader();
		leaderCardsLoader.setLocation(getClass().getResource("/scenes/leadercards.fxml"));
		Parent leaderCardsRoot = leaderCardsLoader.load();
		Scene leaderCardsScene = new Scene(leaderCardsRoot);

		FXMLLoader endGameLoader = new FXMLLoader();
		endGameLoader.setLocation(getClass().getResource("/scenes/endgame.fxml"));
		Parent endGameRoot = endGameLoader.load();
		Scene endGameScene = new Scene(endGameRoot);

		GameStartController gsc = gameStartLoader.getController();
		EndGameController egc = endGameLoader.getController();
		LeaderCardsController lcc = leaderCardsLoader.getController();
		MarketsController mc = marketsLoader.getController();
		MainViewController mvc = mainViewLoader.getController();

		action = new ActionGUI(this, gameStartScene, endGameScene, lc, gsc, egc, lcc, mvc, mc);

		if (lc.isLocalMatch())
		{
			Player player = new Player(username);
			LocalModel model = new LocalModel(player, this);
			LocalController lc = new LocalController(model, this);
			messageHandler = new LocalMatchIO(lc);
			LocalMatch match = new LocalMatch(model, this, lc);		/* M, V, C */
			new Thread(match).start();
		}

		else
			new Thread((Runnable) messageHandler).start();

		gsc.setup(this, mainViewScene, mvc);		/* Only pass "this", pass mvc and mainViewScene through getters? */
		lcc.setup(this, mainViewScene);		/* TODO: remove mainViewScene, use getter */
		mc.setup(this, mainViewScene, mvc);
		mvc.setup(this, mainViewScene, marketsScene, leaderCardsScene);
	}

	public MessageIO getMessageHandler()
	{
		return messageHandler;
	}

	public void setMessageHandler(MessageIO messageHandler)
	{
		this.messageHandler = messageHandler;
	}

	public String getUsername()
	{
		return username;
	}

	public Stage getMainStage()
	{
		return mainStage;
	}
}
