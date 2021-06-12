package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Scene controller for the end of the game
 * Called from mainViewController if end-of-game conditions occur
 * Shows if the player has won, lost or tied, the names and points of all players and a button to start a new game
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class EndGameController
{
	private Stage mainStage;

	@FXML
	private ImageView matchOutcome;

	@FXML
	private Label nameAndPoint;

	@FXML
	private ImageView playAgain;

	/**
	 * Start a new game, call the launcher scene
	 */

	@FXML
	void playAgain(MouseEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/launcher.fxml"));
		Parent root = loader.load();

		LauncherController lpc = loader.getController();
		lpc.setup();

		Scene scene = new Scene(root);

		mainStage.setTitle("Masters of Renaissance Launcher");
		mainStage.getIcons().add(new Image("/img/inkwell.png"));		/* Check if it works from .jar */
		mainStage.setScene(scene);
		//mainStage.setResizable(false);
		mainStage.show();
	}

	/**
	 * Set images of win, tie or loss and the names and points of all players
	 */

	public void setup(String winnerName, List<Player> players, String username, Stage mainStage)
	{
		this.mainStage = mainStage;

		if (username.equals(winnerName))
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/win!.png")));

		if (winnerName.isEmpty())
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/Tie!.png")));

		else
		{
			matchOutcome.setFitWidth(409);
			matchOutcome.setFitHeight(183);
			AnchorPane.setLeftAnchor(matchOutcome, 76.0);
			AnchorPane.setRightAnchor(matchOutcome, 76.0);
			AnchorPane.setTopAnchor(matchOutcome, 70.0);
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/lose...png")));
		}

		switch (players.size())
		{
			case 1:
				nameAndPoint.setText(players.get(0).getUsername() + ": " + players.get(0).getVictoryPoints() + " points\n");
				break;
			case 2:
				nameAndPoint.setText(players.get(0).getUsername() + ": " + players.get(0).getVictoryPoints() + " points\n"
									+ players.get(1).getUsername() + ": " + players.get(1).getVictoryPoints() + " points\n");
				break;
			case 3:
				nameAndPoint.setText(players.get(0).getUsername() + ": " + players.get(0).getVictoryPoints() + " points\n"
									+ players.get(1).getUsername() + ": " + players.get(1).getVictoryPoints() + " points\n"
									+ players.get(2).getUsername() + ": " + players.get(2).getVictoryPoints() + " points\n");
				break;
			case 4:
				nameAndPoint.setText(players.get(0).getUsername() + ": " + players.get(0).getVictoryPoints() + " points\n"
									+ players.get(1).getUsername() + ": " + players.get(1).getVictoryPoints() + " points\n"
									+ players.get(2).getUsername() + ": " + players.get(2).getVictoryPoints() + " points\n"
									+ players.get(3).getUsername() + ": " + players.get(3).getVictoryPoints() + " points\n");
				break;
		}
	}

	/**
	 * Set loss image in single game and show why he lost
	 */

	public void setupSingle(String message, Stage mainStage)
	{
		this.mainStage = mainStage;
		matchOutcome.setFitWidth(409);
		matchOutcome.setFitHeight(183);
		AnchorPane.setLeftAnchor(matchOutcome, 76.0);
		AnchorPane.setRightAnchor(matchOutcome, 76.0);
		AnchorPane.setTopAnchor(matchOutcome, 70.0);
		matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/lose...png")));
		matchOutcome.setVisible(true);

		message = message.substring(10);
		nameAndPoint.setText(message);
	}

	public ImageView getMatchOutcome() {
		return matchOutcome;
	}
}
