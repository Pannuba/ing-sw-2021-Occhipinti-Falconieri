package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	@FXML private Button rowThreeButton;
	@FXML private Button rowFourButton;

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
	void backToBoard(ActionEvent event) throws IOException		/* Set the already existing scene, otherwise it resets everything. How to pass? */
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void setMainViewScene(Scene mainViewScene)
	{
		this.mainViewScene = mainViewScene;
	}
}
