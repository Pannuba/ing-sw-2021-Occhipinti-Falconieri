package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/* Empty images that are updated with image.setImage("...") each gamestate update */

public class MainViewController
{
	@FXML private ImageView dashboard;

	@FXML private Button marblesMarketButton;
	@FXML private Button devCardsMarketButton;
	@FXML private Button leaderCardsButton;

	@FXML private TextArea console;

	@FXML private ImageView topShelfResource;

	@FXML private ImageView middleShelfResource1;
	@FXML private ImageView middleShelfResource2;

	@FXML private ImageView bottomShelfResource1;
	@FXML private ImageView bottomShelfResource2;
	@FXML private ImageView bottomShelfResource3;

	@FXML private ImageView devCardArea1;
	@FXML private ImageView devCardArea2;
	@FXML private ImageView devCardArea3;

	@FXML
	void showDevCardsMarket(ActionEvent event)
	{

	}

	@FXML
	void showLeaderCards(ActionEvent event)
	{

	}

	@FXML
	void showMarblesMarket(ActionEvent event)  throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/marblesmarket.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance - Marbles Market");
		mainStage.setScene(scene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void initialize()
	{

	}


	public ImageView getMiddleShelfResource1()
	{
		return middleShelfResource1;
	}

	public void setMiddleShelfResource1(ImageView middleShelfResource1)
	{
		this.middleShelfResource1 = middleShelfResource1;
	}

	public ImageView getDashboard()
	{
		return dashboard;
	}

	public void setDashboard(ImageView dashboard)
	{
		this.dashboard = dashboard;
	}
}
