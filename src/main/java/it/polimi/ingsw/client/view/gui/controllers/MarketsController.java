package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Arrays;


public class MarketsController        /* Send command directly from here? Get whiteMarbleResource from gamestate and keep it as instance variable? YES */
{
	private Scene mainViewScene;
	private NetworkHandler networkHandler;

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

	public void updateMarket(MarblesMarket market, boolean isMyTurn)
	{
		System.out.println("Updating marbles market... isMyTurn = " + isMyTurn);
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

		/* TODO: add spareMarble */
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

	public void setup(Scene mainViewScene, NetworkHandler networkHandler)
	{
		this.mainViewScene = mainViewScene;
		this.networkHandler = networkHandler;
	}
}
