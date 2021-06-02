package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.client.view.gui.controllers.MarblesMarketController;
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
	private GameState gameState;				/* Local gamestate accessed by action and scenes through get method */

	public GUIModel(NetworkHandler networkHandler, Event event) throws IOException
	{
		FXMLLoader mainViewLoader = new FXMLLoader();
		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FXMLLoader marblesLoader = new FXMLLoader();
		marblesLoader.setLocation(getClass().getResource("/scenes/marblesmarket.fxml"));		/* Put in createScenes()? */
		Parent marblesRoot = marblesLoader.load();
		Scene marblesScene = new Scene(marblesRoot);

		MarblesMarketController mmc = marblesLoader.getController();
		mmc.setMainViewScene(mainViewScene);
		MainViewController mvc = mainViewLoader.getController();
		mvc.setMarblesScene(marblesScene);

		action = new ActionGUI(this, networkHandler, mainViewLoader, marblesLoader);		/* ActionGUI runnable? */

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
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
