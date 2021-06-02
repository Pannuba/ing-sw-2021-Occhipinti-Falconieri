package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ActionGUI;
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
		NetworkHandler networkHandler = new NetworkHandler(ipField.getText(), Integer.parseInt(portField.getText()));
		networkHandler.connect();
		networkHandler.send(nameField.getText());
		new Thread(networkHandler).start();
		//ActionGUI action = new ActionGUI(event, networkHandler);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/mainview.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(scene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void initialize()
	{

	}
}
