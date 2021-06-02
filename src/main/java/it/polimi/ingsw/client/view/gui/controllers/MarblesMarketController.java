package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarblesMarket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MarblesMarketController
{
	private Scene mainViewScene;

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
		Marble[][] marblesBoard = market.getMarblesBoard();

		if (isMyTurn)
		{
			colOneButton.setDisable(false);
			colTwoButton.setDisable(false);
			colThreeButton.setDisable(false);
			colFourButton.setDisable(false);

			rowOneButton.setDisable(false);
			rowTwoButton.setDisable(false);
			colOneButton.setDisable(false);
		}

		else
		{
			colOneButton.setDisable(true);
			colTwoButton.setDisable(true);
			colThreeButton.setDisable(true);
			colFourButton.setDisable(true);

			rowOneButton.setDisable(true);
			rowTwoButton.setDisable(true);
			colOneButton.setDisable(true);
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

	public void setMainViewScene(Scene mainViewScene)
	{
		this.mainViewScene = mainViewScene;
	}
}
