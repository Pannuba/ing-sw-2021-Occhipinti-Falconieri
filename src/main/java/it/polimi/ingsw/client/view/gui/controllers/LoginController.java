package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController
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
			networkHandler.send(numPlayersField.getText());		/* TODO: check if field is not empty */
			return;
		}

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		GUI gui = new GUI(nameField.getText(), this, mainStage);

		networkHandler = new NetworkHandler(gui, ipField.getText(), Integer.parseInt(portField.getText()));
		networkHandler.connect();
		networkHandler.send(nameField.getText());

		gui.setNetworkHandler(networkHandler);
		gui.createScenes();
	}

	public void firstPlayer()
	{
		numPlayersField.setVisible(true);
	}

	public TextField getIpField()
	{
		return ipField;
	}

	public TextField getPortField()
	{
		return portField;
	}
}
