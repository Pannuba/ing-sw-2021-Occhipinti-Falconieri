package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LauncherController
{
	@FXML
	private TextField ipField;

	@FXML
	private TextField portField;

	@FXML
	void connectToServer(ActionEvent event)
	{
		System.out.println("IP: " + ipField.getText());
		System.out.println("Port: " + portField.getText());
	}

	public void initialize()
	{

	}
}
