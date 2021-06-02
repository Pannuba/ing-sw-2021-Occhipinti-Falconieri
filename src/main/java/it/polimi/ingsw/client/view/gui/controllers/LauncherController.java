package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
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

public class LauncherController
{
	@FXML private TextField nameField;

	@FXML private TextField ipField;

	@FXML private TextField portField;

	@FXML
	void connectToServer(ActionEvent event) throws IOException
	{
		FXMLLoader mainViewLoader = new FXMLLoader();

		mainViewLoader.setLocation(getClass().getResource("/scenes/mainview.fxml"));
		Parent mainViewRoot = mainViewLoader.load();
		Scene mainViewScene = new Scene(mainViewRoot);
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		NetworkHandler networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));

		GUIModel gui = new GUIModel(networkHandler, mainViewLoader);

		networkHandler.addObserver(gui);
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		networkHandler.send("1");				/* Always send numPlayers = 1 for debugging */
		new Thread(networkHandler).start();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void initialize()
	{

	}
}
