package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.GUIModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LauncherController
{
	private NetworkHandler networkHandler;

	@FXML private TextField nameField;
	@FXML private TextField ipField;
	@FXML private TextField portField;
	@FXML private TextField numPlayersField;

	@FXML private ImageView startButton;

	@FXML
	void connectToServer(MouseEvent event) throws IOException
	{
		if (numPlayersField.isVisible())		/* Temporary solution, but what do? */
		{
			networkHandler.send(numPlayersField.getText());
			return;
		}

		networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));

		GUIModel gui = new GUIModel(nameField.getText(), this, networkHandler, event);

		networkHandler.addObserver(gui);
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		new Thread(networkHandler).start();
	}

	public void firstPlayer()
	{
		numPlayersField.setVisible(true);
	}

	public TextField getIpField() { return ipField; }

	public TextField getPortField() { return portField; }
}
