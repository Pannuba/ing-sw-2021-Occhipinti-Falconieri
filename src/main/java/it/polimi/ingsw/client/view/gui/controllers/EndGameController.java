package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ActionGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EndGameController {

	@FXML
	private ImageView win;

	@FXML
	private Label winnerPoints;

	@FXML
	private Label secondPlacePoints;

	@FXML
	private Label thirdPlacePoints;

	@FXML
	private Label fourthPlacePoints;

	@FXML
	private ImageView playAgain;

	@FXML
	private ImageView lose;

	@FXML
	void playAgain(MouseEvent event)
	{

	}

	public void setup(ActionGUI actionGUI) throws IOException {

	}

	public ImageView getWin() { return win; }

	public ImageView getLose() { return lose; }
}
