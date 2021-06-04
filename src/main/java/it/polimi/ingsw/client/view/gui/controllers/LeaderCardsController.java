package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public class LeaderCardsController
{
	private Scene mainViewScene;
	private MainViewController mvc;
	private NetworkHandler networkHandler;
	private List<LeaderCard> leaderCards;

	@FXML private ImageView leaderCard1;
	@FXML private ImageView leaderCard2;

	@FXML private Button playLeaderCard1;
	@FXML private Button playLeaderCard2;
	@FXML private Button discardLeaderCard1;
	@FXML private Button discardLeaderCard2;

	@FXML private Button backToBoardButton;

	@FXML
	void backToBoard(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void update(List<LeaderCard> leaderCards)
	{
		//leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		//leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));
	}

	public void setup(Scene mainViewScene, NetworkHandler networkHandler)
	{
		this.mainViewScene = mainViewScene;
		//this.mvc = mvc;
		this.networkHandler = networkHandler;
	}
}