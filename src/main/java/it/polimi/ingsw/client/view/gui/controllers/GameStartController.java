package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

/**
 * Scene controller to choose the leader cards among the four received
 * In case of multiplayer allows you to choose the resource to receive
 * @author Giulio Ochhipinti
 * @author Chiara Falconieri
 */

public class GameStartController		/* The player selects 2 leaderCards first, then the resources */
{
	private Stage mainStage;
	private MessageIO messageHandler;
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
	@FXML private Label selectLeaderCards;

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
		chooseLeaderCard(leaderCard1, leaderCards.get(0).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard2(MouseEvent event)
	{
		chooseLeaderCard(leaderCard2, leaderCards.get(1).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard3(MouseEvent event)
	{
		chooseLeaderCard(leaderCard3, leaderCards.get(2).getCardNumber(), event);
	}

	@FXML
	void selectLeaderCard4(MouseEvent event)
	{
		chooseLeaderCard(leaderCard4, leaderCards.get(3).getCardNumber(), event);
	}

	/**
	 * Send a message to the networkHandler with the two chosen cards, deactivate the MouseEvent of the cards and activate that of the resources
	 * If single game calls the game start scene directly (mainView scene)
	 */

	private void chooseLeaderCard(ImageView cardImg, int cardNum, MouseEvent event)
	{
		cardImg.setEffect(selectedEffect);
		cardImg.setDisable(true);

		chosenLeaderCards.add(String.valueOf(cardNum));

		if (chosenLeaderCards.size() == 2)	/* && hasSelectedTwoCards */
		{
			messageHandler.send(Arrays.asList("SELECT_LEADERCARDS", chosenLeaderCards.get(0), chosenLeaderCards.get(1)));
			purpleResource.setDisable(false);
			greyResource.setDisable(false);
			blueResource.setDisable(false);
			yellowResource.setDisable(false);

			if (playerID == 0)
			{
				messageHandler.send(Arrays.asList("INITIAL_RESOURCES", "", ""));

				mainStage.setTitle("Masters of Renaissance");
				mainStage.setScene(mainViewScene);
				mainStage.centerOnScreen();
				mainStage.show();
			}
		}

		if (chosenLeaderCards.size() >= 2)
		{
			leaderCard1.setDisable(true);
			leaderCard2.setDisable(true);
			leaderCard3.setDisable(true);
			leaderCard4.setDisable(true);
			selectLeaderCards.setVisible(false);
		}
	}

	/**
	 * In case of two or more players it allows to choose the starting resources and to assign the faith points
	 * After calls the game start scene (mainView scene)
	 */

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

			messageHandler.send(Arrays.asList("INITIAL_RESOURCES", chosenResources, initialFaithPoints));

			mainStage.setTitle("Masters of Renaissance");
			mainStage.setScene(mainViewScene);
			mainStage.centerOnScreen();
			mainStage.show();
		}
	}

	/**
	 * Set the leaderCards ImageView images based on those that have been randomly assigned to the player
	 */

	public void setLeaderCards(List<LeaderCard> leaderCards)		/* Display leaderCards */
	{
		this.leaderCards = leaderCards;

		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));
		leaderCard3.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(2).getCardNumber() + ".png")));
		leaderCard4.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(3).getCardNumber() + ".png")));
	}

	/**
	 * Shows the message of how many resources to select based on the player ID
	 */

	public void showResourcesMessage(int playerID)
	{
		this.playerID = playerID;

		switch (playerID)
		{
			case 0:

				Platform.runLater(() -> {resourcesMessage.setText("You're the first player!");});
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

	public void setup(GUI gui, MainViewController mvc)
	{
		this.mainStage = gui.getMainStage();
		this.mainViewScene = gui.getMainViewScene();
		this.mvc = mvc;
		this.messageHandler = gui.getMessageHandler();
		chosenLeaderCards = new ArrayList<>();
		chosenResources = "";

		selectedEffect = new ColorAdjust();
		selectedEffect.setBrightness(-0.5);
	}
}
