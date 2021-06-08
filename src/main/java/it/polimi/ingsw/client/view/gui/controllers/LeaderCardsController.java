package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeaderCardsController		/* TODO: add resources ImageViews in both leader card areas that are set for SkillStorage cards */
{
	private Stage mainStage;
	private Scene mainViewScene;
	private MainViewController mvc;
	private MessageIO messageHandler;
	private List<LeaderCard> leaderCards;
	private List<Button> buttons;

	@FXML private ImageView leaderCard1;
	@FXML private ImageView leaderCard2;

	@FXML private Button activateLeaderOneButton;
	@FXML private Button activateLeaderTwoButton;
	@FXML private Button discardLeaderOneButton;
	@FXML private Button discardLeaderTwoButton;

	@FXML private Button backToBoardButton;

	@FXML
	void activateLeaderCard1(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
	}

	@FXML
	void activateLeaderCard2(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_LEADER", String.valueOf(leaderCards.get(1).getCardNumber())));
	}

	@FXML
	void discardLeaderCard1(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("DISCARD_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
	}

	@FXML
	void discardLeaderCard2(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("DISCARD_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
	}

	@FXML
	void backToBoard(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void update(List<LeaderCard> leaderCards, boolean isMyTurn)
	{
		this.leaderCards = leaderCards;

		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));

		if (!isMyTurn)
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(true);
		}
	}

	public void setup(GUI gui, Scene mainViewScene)
	{
		this.mainViewScene = mainViewScene;
		this.mainStage = gui.getMainStage();
		//this.mvc = mvc;
		this.messageHandler = gui.getMessageHandler();

		buttons = new ArrayList<>();
		Collections.addAll(buttons, activateLeaderOneButton, activateLeaderTwoButton, discardLeaderOneButton, discardLeaderTwoButton);
	}
}