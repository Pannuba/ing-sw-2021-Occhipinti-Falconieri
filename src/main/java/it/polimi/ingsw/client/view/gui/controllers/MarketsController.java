package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.DevCardsMarket;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MarketsController        /* Send command directly from here? Get whiteMarbleResource from gamestate and keep it as instance variable? YES */
{
	private Scene mainViewScene;
	private MainViewController mvc;
	private NetworkHandler networkHandler;

	private DevCardsMarket currDevCardsMarket;

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

	@FXML private Button backToBoardButton;

	@FXML private ImageView devCard00;
	@FXML private ImageView devCard10;
	@FXML private ImageView devCard20;
	@FXML private ImageView devCard01;
	@FXML private ImageView devCard11;
	@FXML private ImageView devCard21;
	@FXML private ImageView devCard02;
	@FXML private ImageView devCard12;
	@FXML private ImageView devCard22;
	@FXML private ImageView devCard03;
	@FXML private ImageView devCard13;
	@FXML private ImageView devCard23;

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

		if (isMyTurn)		/* When it's the player's turn, make buttons pressable to send the command BUY_RESOURCES */
		{
			colOneButton.setDisable(false);
			colTwoButton.setDisable(false);
			colThreeButton.setDisable(false);
			colFourButton.setDisable(false);

			rowOneButton.setDisable(false);
			rowTwoButton.setDisable(false);
			rowThreeButton.setDisable(false);

			devCard00.setDisable(false);			/* Put them in a list, and enable/disable with a for? */
			devCard01.setDisable(false);
			devCard02.setDisable(false);
			devCard03.setDisable(false);
			devCard10.setDisable(false);
			devCard11.setDisable(false);
			devCard12.setDisable(false);
			devCard13.setDisable(false);
			devCard20.setDisable(false);
			devCard21.setDisable(false);
			devCard22.setDisable(false);
			devCard23.setDisable(false);
		}
//the disable checkbox in scenebuilder disables the button permanently, meaning setdisable(false) wont work
		else
		{
			colOneButton.setDisable(true);
			colTwoButton.setDisable(true);
			colThreeButton.setDisable(true);
			colFourButton.setDisable(true);

			rowOneButton.setDisable(true);
			rowTwoButton.setDisable(true);
			rowThreeButton.setDisable(true);

			devCard00.setDisable(true);			/* Put them in a list, and enable/disable with a for? */
			devCard01.setDisable(true);
			devCard02.setDisable(true);
			devCard03.setDisable(true);
			devCard10.setDisable(true);
			devCard11.setDisable(true);
			devCard12.setDisable(true);
			devCard13.setDisable(true);
			devCard20.setDisable(true);
			devCard21.setDisable(true);
			devCard22.setDisable(true);
			devCard23.setDisable(true);
		}

		marble00.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[0][0].getMarbleType()))));
		marble01.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[0][1].getMarbleType()))));
		marble02.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[0][2].getMarbleType()))));
		marble03.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[0][3].getMarbleType()))));
		marble10.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[1][0].getMarbleType()))));
		marble11.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[1][1].getMarbleType()))));
		marble12.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[1][2].getMarbleType()))));
		marble13.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[1][3].getMarbleType()))));
		marble20.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[2][0].getMarbleType()))));
		marble21.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[2][1].getMarbleType()))));
		marble22.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[2][2].getMarbleType()))));
		marble23.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertMarbleTypeToPath(marblesBoard[2][3].getMarbleType()))));

		/* TODO: add spareMarble, order indexes of devcards/stacks/level... */
		List<ImageView> devcards = new ArrayList<>();
		devcards.add(devCard20);
		devcards.add(devCard10);
		devcards.add(devCard00);
		devcards.add(devCard21);
		devcards.add(devCard11);
		devcards.add(devCard01);
		devcards.add(devCard22);
		devcards.add(devCard12);
		devcards.add(devCard02);
		devcards.add(devCard23);
		devcards.add(devCard13);
		devcards.add(devCard03);

		for (int i = 0; i < devcards.size(); i++)
		{
			if (devCardsMarket.getDevCardStacks().get(i).isEmpty())
				devcards.get(i).setImage(null);

			else
				devcards.get(i).setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(i).get(0).getCardNumber() + ".png")));
		}

		/*devCard20.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(0).get(0).getCardNumber() + ".png")));
		devCard10.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(1).get(0).getCardNumber() + ".png")));
		devCard00.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(2).get(0).getCardNumber() + ".png")));
		devCard21.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(3).get(0).getCardNumber() + ".png")));
		devCard11.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(4).get(0).getCardNumber() + ".png")));
		devCard01.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(5).get(0).getCardNumber() + ".png")));
		devCard22.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(6).get(0).getCardNumber() + ".png")));
		devCard12.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(7).get(0).getCardNumber() + ".png")));
		devCard02.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(8).get(0).getCardNumber() + ".png")));
		devCard23.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(9).get(0).getCardNumber() + ".png")));
		devCard13.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(10).get(0).getCardNumber() + ".png")));
		devCard03.setImage(new Image(getClass().getResourceAsStream("/img/devcards/front/" + devCardsMarket.getDevCardStacks().get(11).get(0).getCardNumber() + ".png")));*/
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
	void selectDevCard00(MouseEvent event)
	{
		mvc.setDevCardToBuy(currDevCardsMarket.getDevCardStacks().get(2).get(0).getCardNumber());
		mvc.getDevCardArea1().setDisable(false);
		mvc.getDevCardArea2().setDisable(false);
		mvc.getDevCardArea3().setDisable(false);

		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();		/* TODO: instance variable? */

		mainStage.setTitle("Masters of Renaissance - Select Dev Card Area");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	@FXML
	void selectDevCard20(MouseEvent event)
	{
		mvc.setDevCardToBuy(currDevCardsMarket.getDevCardStacks().get(0).get(0).getCardNumber());
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
	}
}
