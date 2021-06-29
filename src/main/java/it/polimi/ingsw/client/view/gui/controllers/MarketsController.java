package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.*;

/**
 * Scene controller for marbles and devCars markets
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class MarketsController        /* Send command directly from here? Get whiteMarbleResource from gamestate and keep it as instance variable? YES */
{
	private Stage mainStage;
	private Scene mainViewScene;
	private MainViewController mvc;
	private MessageIO messageHandler;

	private DevCardsMarket currDevCardsMarket;
	private List<ResourceType> whiteMarbleResources;

	private String rowOrCol;
	private String number;

	private List<ImageView> devCards;
	private List<ImageView> marbles;
	private List<Button> buttons;

	@FXML private ImageView marblesMarket;

	@FXML private Button colOneButton;
	@FXML private Button colTwoButton;
	@FXML private Button colThreeButton;
	@FXML private Button colFourButton;

	@FXML private Button rowOneButton;
	@FXML private Button rowTwoButton;
	@FXML private Button rowThreeButton;

	@FXML private ImageView marble00;
	@FXML private ImageView marble01;
	@FXML private ImageView marble02;
	@FXML private ImageView marble03;
	@FXML private ImageView marble10;
	@FXML private ImageView marble11;
	@FXML private ImageView marble12;
	@FXML private ImageView marble13;
	@FXML private ImageView marble20;
	@FXML private ImageView marble21;
	@FXML private ImageView marble22;
	@FXML private ImageView marble23;
	@FXML private ImageView spareMarble;

	@FXML private ImageView whiteMarblesLabel;
	@FXML private ImageView whiteMarbleResourceOne;
	@FXML private ImageView whiteMarbleResourceTwo;

	@FXML private Button backToBoardButton;

	@FXML private ImageView devCard1;
	@FXML private ImageView devCard2;
	@FXML private ImageView devCard3;
	@FXML private ImageView devCard4;
	@FXML private ImageView devCard5;
	@FXML private ImageView devCard6;
	@FXML private ImageView devCard7;
	@FXML private ImageView devCard8;
	@FXML private ImageView devCard9;
	@FXML private ImageView devCard10;
	@FXML private ImageView devCard11;
	@FXML private ImageView devCard12;

	@FXML
	void backToBoard(ActionEvent event)		/* Set the already existing scene, otherwise it resets everything. How to pass? */
	{
		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	/**
	 * Updates markets when resources are bought from the market or when a card is bought from the devCard market
	 */

	public void updateMarket(MarblesMarket market, DevCardsMarket devCardsMarket, Player player)
	{
		currDevCardsMarket = devCardsMarket;

		Marble[][] marblesBoard = market.getMarblesBoard();
		Marble spare = market.getSpareMarble();

		whiteMarbleResources = player.getWhiteMarbleTypes();
		System.out.println("updateMarket called. whiteMarbleResources.size = " + whiteMarbleResources.size());
		if (player.isMyTurn())		/* When it's the player's turn, make buttons pressable to send the command BUY_RESOURCES */
			enableButtons();
		/* The disable checkbox in sceneBuilder disables the button permanently, meaning setDisable(false) won't work */
		else
			disableButtons();

		int j = 0, k = 0;

		for (int i = 0; i < marbles.size(); i++)
		{
			marbles.get(i).setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[j][k].getMarbleType()))));

			if (k == 3)
			{
				k = 0;	j++;
			}

			else k++;
		}

		spareMarble.setImage((new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(spare.getMarbleType())))));

		for (int i = 0; i < devCards.size(); i++)
		{
			if (devCardsMarket.getDevCardStacks().get(i).isEmpty())
				devCards.get(i).setImage(null);

			else
				devCards.get(i).setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardsMarket.getDevCardStacks().get(i).get(0).getCardNumber() + ".png")));
		}
	}

	@FXML
	void selectRowOne(ActionEvent event)
	{
		rowOrCol = "ROW";
		number = "1";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectRowTwo(ActionEvent event)
	{
		rowOrCol = "ROW";
		number = "2";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectRowThree(ActionEvent event)
	{
		rowOrCol = "ROW";
		number = "3";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectColumnOne(ActionEvent event)
	{
		rowOrCol = "COLUMN";
		number = "1";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectColumnTwo(ActionEvent event)
	{
		rowOrCol = "COLUMN";
		number = "2";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectColumnThree(ActionEvent event)
	{
		rowOrCol = "COLUMN";
		number = "3";
		buyResources(rowOrCol, number);
	}

	@FXML
	void selectColumnFour(ActionEvent event)
	{
		rowOrCol = "COLUMN";
		number = "4";
		buyResources(rowOrCol, number);
	}

	public void buyResources(String rowOrCol, String number)
	{
		if (whiteMarbleResources.size() < 2)
			messageHandler.send(Arrays.asList("BUY_RESOURCES", rowOrCol, number));

		else
		{
			whiteMarblesLabel.setVisible(true);
			whiteMarbleResourceOne.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(whiteMarbleResources.get(0)))));
			whiteMarbleResourceTwo.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(whiteMarbleResources.get(1)))));
			whiteMarbleResourceOne.setVisible(true);
			whiteMarbleResourceTwo.setVisible(true);
		}

	}

	@FXML
	void selectWhiteMarbleResourceOne(MouseEvent event)
	{
		String selectedWhiteMarbleRes = String.valueOf(whiteMarbleResources.get(0).toString().charAt(0));
		messageHandler.send(Arrays.asList("BUY_RESOURCES", rowOrCol, number, selectedWhiteMarbleRes));
		whiteMarblesLabel.setVisible(false);
		whiteMarbleResourceOne.setVisible(false);
		whiteMarbleResourceTwo.setVisible(false);
	}

	@FXML
	void selectWhiteMarbleResourceTwo(MouseEvent event)
	{
		String selectedWhiteMarbleRes = String.valueOf(whiteMarbleResources.get(1).toString().charAt(0));
		messageHandler.send(Arrays.asList("BUY_RESOURCES", rowOrCol, number, selectedWhiteMarbleRes));
		whiteMarblesLabel.setVisible(false);
		whiteMarbleResourceOne.setVisible(false);
		whiteMarbleResourceTwo.setVisible(false);
	}

	@FXML
	void selectDevCard1(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(0).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard2(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(1).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard3(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(2).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard4(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(3).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard5(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(4).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard6(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(5).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard7(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(6).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard8(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(7).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard9(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(8).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard10(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(9).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard11(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(10).get(0).getCardNumber(), event);
	}

	@FXML
	void selectDevCard12(MouseEvent event)
	{
		buyDevCard(currDevCardsMarket.getDevCardStacks().get(11).get(0).getCardNumber(), event);

	}

	/**
	 * When you buy a card from the devCard market it returns to the mainView scene to place the card in a devCard area
	 */

	private void buyDevCard(int cardToBuyNum, MouseEvent event)
	{
		mvc.setDevCardToBuy(cardToBuyNum);
		mvc.setBuyingDevCard(true);

		mvc.printToConsole("Click the area where you want to put the card");

		mainStage.setTitle("Masters of Renaissance - Select Dev Card Area");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void enableButtons()
	{
		for (int i = 0; i < buttons.size(); i++)
			buttons.get(i).setDisable(false);

		for (int i = 0; i < devCards.size(); i++)
			devCards.get(i).setDisable(false);
	}

	public void disableButtons()
	{
		for (int i = 0; i < buttons.size(); i++)
			buttons.get(i).setDisable(true);

		for (int i = 0; i < devCards.size(); i++)
			devCards.get(i).setDisable(true);
	}

	public void setup(GUI gui, MainViewController mvc)
	{
		this.mainStage = gui.getMainStage();
		this.mainViewScene = gui.getMainViewScene();
		this.mvc = mvc;
		this.messageHandler = gui.getMessageHandler();

		devCards = new ArrayList<>();
		marbles = new ArrayList<>();
		buttons = new ArrayList<>();

		Collections.addAll(	devCards,	devCard1, devCard2, devCard3,  devCard4,  devCard5, devCard6,		/* So much cleaner, no need to copypaste 12 lines every time */
										devCard7, devCard8, devCard9, devCard10, devCard11, devCard12 );
		Collections.addAll(	marbles,	marble00, marble01, marble02, marble03, marble10, marble11,
										marble12, marble13, marble20, marble21, marble22, marble23 );
		Collections.addAll(	buttons,	rowOneButton, rowTwoButton, rowThreeButton, colOneButton, colTwoButton, colThreeButton, colFourButton);
	}
}
