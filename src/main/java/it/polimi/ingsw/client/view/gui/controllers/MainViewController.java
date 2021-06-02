package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/* Empty images that are updated with image.setImage("...") each gamestate update */

public class MainViewController
{
	@FXML
	private ImageView dashboard;

	@FXML
	private Button marblesMarketButton;

	@FXML
	private Button devCardsMarketButton;

	@FXML
	private Button leaderCardsButton;

	@FXML
	private TextArea console;

	@FXML
	private ImageView topShelfResource;

	@FXML
	private ImageView middleShelfResource1;

	@FXML
	private ImageView middleShelfResource2;

	@FXML
	private ImageView bottomShelfResource1;

	@FXML
	private ImageView bottomShelfResource2;

	@FXML
	private ImageView bottomShelfResource3;

	@FXML
	private ImageView devCardArea1;

	@FXML
	private ImageView devCardArea2;

	@FXML
	private ImageView devCardArea3;

	@FXML
	void showDevCardsMarket(ActionEvent event)
	{

	}

	@FXML
	void showLeaderCards(ActionEvent event)
	{

	}

	@FXML
	void showMarblesMarket(ActionEvent event)
	{

	}

	public void initialize()
	{

	}
}
