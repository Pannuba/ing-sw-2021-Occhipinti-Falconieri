package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.GameStartController;
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
	private Event event;

	public GUIModel(String username, NetworkHandler networkHandler, Event event) throws IOException		/* /a/14190310 on how to pass parameters to controllers */
	{
		this.username = username;
		this.event = event;

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader gameStartLoader = new FXMLLoader();
		gameStartLoader.setLocation(getClass().getResource("/scenes/gamestart.fxml"));
		Parent gameStartRoot = gameStartLoader.load();
		GameStartController gsc = gameStartLoader.getController();
		Scene gameStartScene = new Scene(gameStartRoot);

		FXMLLoader mainViewLoader = new FXMLLoader();
		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);

		FXMLLoader marketsLoader = new FXMLLoader();
		marketsLoader.setLocation(getClass().getResource("/scenes/markets.fxml"));		/* Put in createScenes()? */
		Parent marketsRoot = marketsLoader.load();
		Scene marketsScene = new Scene(marketsRoot);

		MarketsController mc = marketsLoader.getController();
		MainViewController mvc = mainViewLoader.getController();
		gsc.setup(mainViewScene, mvc, networkHandler);
		mc.setup(mainViewScene, mvc, networkHandler);						/* TODO: create setup() in all scene controllers */
		mvc.setup(marketsScene, networkHandler, username);

		action = new ActionGUI(this, networkHandler, gameStartScene, gsc, mvc, mc);

		/*mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.setWidth(1280);
		mainStage.setHeight(720);
		mainStage.show();*/
	}

	@Override
	public void update(Observable obs, Object obj)
	{
		if (obj instanceof Message)
			((Message) obj).process(action);		/* Calls method in ActionGUI specified in the message */

		if (obj instanceof GameState)
		{
			this.gameState = (GameState) obj;		/* Gamestate is needed in game loop, not during setup */
			action.updateView(gameState);		/* Or call updateTrack, updateStorage, updateMarkets... here? */
		}
	}

	public GameState getGameState()		/* Just pass the gamestate to ActionGUI */
	{
		return gameState;
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
