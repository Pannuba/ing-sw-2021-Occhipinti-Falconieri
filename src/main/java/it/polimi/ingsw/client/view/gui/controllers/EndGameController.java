package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ActionGUI;
import it.polimi.ingsw.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class EndGameController {

	@FXML
	private ImageView matchOutcome;

	@FXML
	private Label nameAndPoint;

	@FXML
	private ImageView playAgain;

	@FXML
	void playAgain(MouseEvent event)
	{

	}

	public void setup(String winnerName, List<Player> players, String username)
	{
		if (username.equals(winnerName))
		{
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/win!.png")));
			matchOutcome.setVisible(true);
		}

		if (winnerName.isEmpty())
		{
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/Tie!.png")));
			matchOutcome.setVisible(true);
		}

		else
		{
			matchOutcome.setFitWidth(409);
			matchOutcome.setFitHeight(183);
			AnchorPane.setLeftAnchor(matchOutcome, 76.0);
			AnchorPane.setRightAnchor(matchOutcome, 76.0);
			AnchorPane.setTopAnchor(matchOutcome, 70.0);
			matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/lose...png")));
			matchOutcome.setVisible(true);
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

	public void setupSingle(String message)
	{
		matchOutcome.setFitWidth(409);
		matchOutcome.setFitHeight(183);
		AnchorPane.setLeftAnchor(matchOutcome, 76.0);
		AnchorPane.setRightAnchor(matchOutcome, 76.0);
		AnchorPane.setTopAnchor(matchOutcome, 70.0);
		matchOutcome.setImage(new Image(getClass().getResourceAsStream("/img/writings/lose...png")));
		matchOutcome.setVisible(true);

		nameAndPoint.setText(message);
	}

	public ImageView getMatchOutcome() {
		return matchOutcome;
	}
}
