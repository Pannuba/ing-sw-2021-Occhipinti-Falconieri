package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ActionGUI;
import it.polimi.ingsw.client.view.gui.GUIModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;

public class LauncherController
{
	@FXML private TextField nameField;

	@FXML private TextField ipField;

	@FXML private TextField portField;

	@FXML
	void connectToServer(ActionEvent event) throws IOException
	{
		FXMLLoader boardLoader = new FXMLLoader();


		boardLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent boardRoot = boardLoader.load();
		Scene boardScene = new Scene(boardRoot);

		FXMLLoader marblesLoader = new FXMLLoader();

		marblesLoader.setLocation(getClass().getResource("/scenes/marblesmarket.fxml"));
		Parent marblesRoot = marblesLoader.load();
		Scene marblesScene = new Scene(marblesRoot);

		NetworkHandler networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));

		ActionGUI action = new ActionGUI(networkHandler, boardScene, marblesScene);		/* ActionGUI runnable? */
		GUIModel gui = new GUIModel(action);

		networkHandler.addObserver(gui);
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		new Thread(networkHandler).start();

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(boardScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void initialize()
	{

	}
}
