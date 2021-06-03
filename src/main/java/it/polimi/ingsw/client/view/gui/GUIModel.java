package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.client.view.gui.controllers.MarketsController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.server.messages.Message;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GUIModel implements Observer        /* Has gamestate, action instance, observes NetworkHandler */
{
	private final ActionGUI action;				/* ActionGUI instance to pass it the commands received by the NetworkHandler */
	private final String username;
	private GameState gameState;				/* Local gamestate accessed by action and scenes through get method */

	public GUIModel(String username, NetworkHandler networkHandler, Event event) throws IOException		/* /a/14190310 on how to pass parameters to controllers */
	{
		this.username = username;

		FXMLLoader mainViewLoader = new FXMLLoader();
		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FXMLLoader marketsLoader = new FXMLLoader();
		marketsLoader.setLocation(getClass().getResource("/scenes/markets.fxml"));		/* Put in createScenes()? */
		Parent marketsRoot = marketsLoader.load();
		Scene marketsScene = new Scene(marketsRoot);

		MarketsController mc = marketsLoader.getController();
		MainViewController mvc = mainViewLoader.getController();
		mc.setup(mainViewScene, mvc, networkHandler);						/* TODO: create setup() in all scene controllers */
		mvc.setup(marketsScene, networkHandler);

		action = new ActionGUI(this, networkHandler, mvc, mc);

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.setWidth(1280);
		mainStage.setHeight(720);
		mainStage.show();
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in cli specified in the message */

		if (obj instanceof GameState)
		{
			this.gameState = (GameState) obj;		/* Gamestate is needed in game loop, not during setup */
			action.updateView(gameState);		/* Or call updateTrack, updateStorage, updateMarkets... here? */
		}
	}

	public GameState getGameState()
	{
		return gameState;
	}

	public String getUsername()
	{
		return username;
	}
}
