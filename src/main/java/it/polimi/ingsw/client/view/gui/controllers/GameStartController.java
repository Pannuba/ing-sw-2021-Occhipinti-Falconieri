package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class GameStartController
{
	private Scene mainViewScene;
	private MainViewController mvc;

	@FXML private ImageView leaderCard1;
	@FXML private ImageView leaderCard2;
	@FXML private ImageView leaderCard3;
	@FXML private ImageView leaderCard4;

	@FXML private ImageView purpleResource;		/* TODO: color effect brightness -0.2 for selected resources */
	@FXML private ImageView greyResource;
	@FXML private ImageView blueResource;
	@FXML private ImageView yellowResource;

	@FXML private Label resourcesMessage;

	@FXML
	void chooseBlueResource(MouseEvent event)
	{

	}

	@FXML
	void chooseGreyResource(MouseEvent event)
	{

	}

	@FXML
	void choosePurpleResource(MouseEvent event)
	{

	}

	@FXML
	void chooseYellowResource(MouseEvent event)
	{

	}

	@FXML
	void selectLeaderCard1(MouseEvent event)
	{
		/* Send command */
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		mainStage.setScene(mainViewScene);
		mainStage.show();
	}

	@FXML
	void selectLeaderCard2(MouseEvent event)
	{

	}

	@FXML
	void selectLeaderCard3(MouseEvent event)
	{

	}

	@FXML
	void selectLeaderCard4(MouseEvent event)
	{

	}

	public void setFourLeaderCards(List<LeaderCard> fourLeaderCards)		/* Display leadercards */
	{
		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + fourLeaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + fourLeaderCards.get(1).getCardNumber() + ".png")));
		leaderCard3.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + fourLeaderCards.get(2).getCardNumber() + ".png")));
		leaderCard4.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + fourLeaderCards.get(3).getCardNumber() + ".png")));
	}

	public void showResourcesMessage(int playerID)
	{
		switch (playerID)
		{
			case 0:
				Platform.runLater(() -> {resourcesMessage.setText("You're the first player, so you get nothing!");});
				break;

			case 1:
				resourcesMessage.setText("Choose 1 starting resource");
				break;
		}
	}

	public void setup(Scene mainViewScene, MainViewController mvc)
	{
		this.mainViewScene = mainViewScene;
		this.mvc = mvc;
	}
}
