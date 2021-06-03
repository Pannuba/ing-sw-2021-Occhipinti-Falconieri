package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.GUIModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LauncherController
{
	@FXML private TextField nameField;

	@FXML private TextField ipField;

	@FXML private TextField portField;

	@FXML
	void connectToServer(ActionEvent event) throws IOException
	{

		NetworkHandler networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));

		GUIModel gui = new GUIModel(nameField.getText(), networkHandler, event);

		networkHandler.addObserver(gui);
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		networkHandler.send("1");				/* Always send numPlayers = 1 for now */
		new Thread(networkHandler).start();
	}
}
