package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ActionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LauncherController
{
	@FXML
	private TextField nameField;

	@FXML
	private TextField ipField;

	@FXML
	private TextField portField;

	@FXML
	void connectToServer(ActionEvent event)
	{
		NetworkHandler networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		new Thread(networkHandler).start();
		//ActionGUI action = new ActionGUI(event, networkHandler);
	}

	public void initialize()
	{

	}
}
