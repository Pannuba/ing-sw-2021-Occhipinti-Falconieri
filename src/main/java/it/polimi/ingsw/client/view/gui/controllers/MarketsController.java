package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.DevCardsMarket;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.MarblesMarket;
import it.polimi.ingsw.model.cards.DevCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.*;


public class MarketsController        /* Send command directly from here? Get whiteMarbleResource from gamestate and keep it as instance variable? YES */
{
	private Scene mainViewScene;
	private MainViewController mvc;
	private NetworkHandler networkHandler;

	private DevCardsMarket currDevCardsMarket;
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
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void updateMarket(MarblesMarket market, DevCardsMarket devCardsMarket, boolean isMyTurn)
	{
		System.out.println("Updating marbles market... isMyTurn = " + isMyTurn);

		currDevCardsMarket = devCardsMarket;

		Marble[][] marblesBoard = market.getMarblesBoard();
		Marble spare = market.getSpareMarble();

		if (isMyTurn)		/* When it's the player's turn, make buttons pressable to send the command BUY_RESOURCES */
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(false);

			for (int i = 0; i < devCards.size(); i++)
				devCards.get(i).setDisable(false);
		}
		//the disable checkbox in scenebuilder disables the button permanently, meaning setdisable(false) wont work
		else
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(true);

			for (int i = 0; i < devCards.size(); i++)
				devCards.get(i).setDisable(true);
		}

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
				devCards.get(i).setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(i).get(0).getCardNumber() + ".png")));
		}
	}

	@FXML
	void selectRowOne(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "ROW", "1"));
	}

	@FXML
	void selectRowTwo(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "ROW", "2"));
	}

	@FXML
	void selectRowThree(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "ROW", "3"));
	}

	@FXML
	void selectColumnOne(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "COLUMN", "1"));
	}

	@FXML
	void selectColumnTwo(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "COLUMN", "2"));
	}

	@FXML
	void selectColumnThree(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "COLUMN", "3"));
	}

	@FXML
	void selectColumnFour(ActionEvent event)
	{
		networkHandler.send(Arrays.asList("BUY_RESOURCES", "COLUMN", "4"));
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

	private void buyDevCard(int cardToBuyNum, MouseEvent event)
	{
		mvc.setDevCardToBuy(cardToBuyNum);
		mvc.setBuyingDevcard(true);
		mvc.getDevCardArea1().setDisable(false);
		mvc.getDevCardArea2().setDisable(false);
		mvc.getDevCardArea3().setDisable(false);

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();		/* TODO: instance variable? */

		mainStage.setTitle("Masters of Renaissance - Select Dev Card Area");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void setup(Scene mainViewScene, MainViewController mvc, NetworkHandler networkHandler)
	{
		this.mainViewScene = mainViewScene;
		this.mvc = mvc;
		this.networkHandler = networkHandler;

		devCards = new ArrayList<>();
		marbles = new ArrayList<>();
		buttons = new ArrayList<>();

		Collections.addAll(	devCards,	devCard1, devCard2, devCard3,  devCard4,  devCard5, devCard6,		/* So much cleaner, no need to copypaste 12 lines every time */
										devCard7, devCard8, devCard9, devCard10, devCard11, devCard12	);
		Collections.addAll(	marbles,	marble00, marble01, marble02, marble03, marble10, marble11,
										marble12, marble13, marble20, marble21, marble22, marble23	);
		Collections.addAll(	buttons,	rowOneButton, rowTwoButton, rowThreeButton, colOneButton, colTwoButton, colThreeButton);
	}
}
