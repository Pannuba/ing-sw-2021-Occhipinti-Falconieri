package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStartController		/* The player selects 2 leadercards first, then the resources */
{
	private NetworkHandler networkHandler;
	private Scene mainViewScene;
	private MainViewController mvc;
	private int playerID;
	private List<LeaderCard> leaderCards;
	private int resourcesToChoose;
	private String chosenResources;
	private List<String> chosenLeaderCards;

	private ColorAdjust selectedEffect;

	@FXML private ImageView leaderCard1;
	@FXML private ImageView leaderCard2;
	@FXML private ImageView leaderCard3;
	@FXML private ImageView leaderCard4;

	@FXML private ImageView purpleResource;
	@FXML private ImageView greyResource;
	@FXML private ImageView blueResource;
	@FXML private ImageView yellowResource;

	@FXML private Label resourcesMessage;

	@FXML
	void chooseBlueResource(MouseEvent event)
	{
		chooseResource("B", event);
	}

	@FXML
	void chooseGreyResource(MouseEvent event)
	{
		chooseResource("G", event);
	}

	@FXML
	void choosePurpleResource(MouseEvent event)
	{
		chooseResource("P", event);
	}

	@FXML
	void chooseYellowResource(MouseEvent event)
	{
		chooseResource("Y", event);
	}

	@FXML
	void selectLeaderCard1(MouseEvent event)
	{
		leaderCard1.setEffect(selectedEffect);
		leaderCard1.setDisable(true);
		chooseLeaderCard(leaderCards.get(0).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard2(MouseEvent event)
	{
		leaderCard2.setEffect(selectedEffect);
		leaderCard2.setDisable(true);
		chooseLeaderCard(leaderCards.get(1).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard3(MouseEvent event)
	{
		leaderCard3.setEffect(selectedEffect);
		leaderCard3.setDisable(true);
		chooseLeaderCard(leaderCards.get(2).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard4(MouseEvent event)
	{
		leaderCard4.setEffect(selectedEffect);
		leaderCard4.setDisable(true);
		chooseLeaderCard(leaderCards.get(3).getCardNumber(), event);
	}

	private void chooseLeaderCard(int cardNum, MouseEvent event)
	{
		chosenLeaderCards.add(String.valueOf(cardNum));

		if (chosenLeaderCards.size() == 2)
		{
			networkHandler.send(Arrays.asList("SELECT_LEADERCARDS", chosenLeaderCards.get(0), chosenLeaderCards.get(1)));
			purpleResource.setDisable(false);
			greyResource.setDisable(false);
			blueResource.setDisable(false);
			yellowResource.setDisable(false);

			if (playerID == 0)
			{
				networkHandler.send(Arrays.asList("INITIAL_RESOURCES", "", ""));
				Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				mainStage.setScene(mainViewScene);
				mainStage.show();
			}
		}
	}

	private void chooseResource(String resType, MouseEvent event)
	{
		chosenResources += resType;

		if (chosenResources.length() == resourcesToChoose)
		{
			String initialFaithPoints = "";

			switch (playerID)
			{
				case 0:		/* Never happens because the scene immediately changes after picking the 2 cards for singleplayer matches */
				case 1:
					break;

				case 2:
					initialFaithPoints = "1";
					break;

				case 3:
					initialFaithPoints = "2";
					break;
			}

			networkHandler.send(Arrays.asList("INITIAL_RESOURCES", chosenResources, initialFaithPoints));

			Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			mainStage.setScene(mainViewScene);
			mainStage.show();
		}
	}

	public void setLeaderCards(List<LeaderCard> leaderCards)		/* Display leadercards */
	{
		this.leaderCards = leaderCards;

		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));
		leaderCard3.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(2).getCardNumber() + ".png")));
		leaderCard4.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(3).getCardNumber() + ".png")));
	}

	public void showResourcesMessage(int playerID)
	{
		this.playerID = playerID;

		switch (playerID)
		{
			case 0:

				Platform.runLater(() -> {resourcesMessage.setText("You're the first player, so you get nothing!");});
				resourcesToChoose = 0;
				break;

			case 1:
			case 2:

				Platform.runLater(() -> {resourcesMessage.setText("Choose one starting resource");});
				resourcesToChoose = 1;
				break;

			case 3:

				Platform.runLater(() -> {resourcesMessage.setText("Choose two starting resources");});
				resourcesToChoose = 2;
				break;
		}
	}

	public void setup(Scene mainViewScene, MainViewController mvc, NetworkHandler networkHandler)
	{
		this.mainViewScene = mainViewScene;
		this.mvc = mvc;
		this.networkHandler = networkHandler;
		chosenLeaderCards = new ArrayList<>();

		selectedEffect = new ColorAdjust();
		selectedEffect.setBrightness(-0.5);
	}
}
