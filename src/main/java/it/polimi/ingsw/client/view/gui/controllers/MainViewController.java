package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.board.DevCardArea;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.board.Vault;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

/* Empty images that are updated with image.setImage("...") each gamestate update */

public class MainViewController
{
	private Scene marblesScene;

	@FXML private ImageView dashboard;

	@FXML private Button marketsButton;
	@FXML private Button leaderCardsButton;

	@FXML private TextArea console;

	@FXML private ImageView redPawn;
	@FXML private ImageView blackPawn;

	@FXML private ImageView popeTokenOne;
	@FXML private ImageView popeTokenTwo;
	@FXML private ImageView popeTokenThree;

	@FXML private ImageView topShelfResource;

	@FXML private ImageView middleShelfResource1;
	@FXML private ImageView middleShelfResource2;

	@FXML private ImageView bottomShelfResource1;
	@FXML private ImageView bottomShelfResource2;
	@FXML private ImageView bottomShelfResource3;

	@FXML private ImageView vaultResourceBlue;
	@FXML private ImageView vaultResourcePurple;
	@FXML private ImageView vaultResourceYellow;
	@FXML private ImageView vaultResourceGrey;

	@FXML private Label vaultBlueAmount;
	@FXML private Label vaultPurpleAmount;
	@FXML private Label vaultYellowAmount;
	@FXML private Label vaultGreyAmount;

	@FXML private ImageView devCardArea1;
	@FXML private ImageView devCardArea2;
	@FXML private ImageView devCardArea3;

	@FXML
	void showLeaderCards(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance - Leader Cards");
		//mainStage.setScene(leaderCardsScene);
		mainStage.sizeToScene();
		mainStage.show();
	}

	@FXML
	void showMarkets(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance - Marbles Market");
		mainStage.setScene(marblesScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void updateTrack(Track track, List<Player> players, int playerID)		/* Also print other players */
	{
		if (players.size() == 1)
		{
			blackPawn.setVisible(true);
			movePawn(blackPawn, track.getBlackPawn());
		}

		movePawn(redPawn, track.getRedPawns().get(playerID));

		if (players.get(playerID).getPopeTokens()[0].isActive())		/* Put popeTokens ImageView in a list and update them with a for loop? */
			popeTokenOne.setImage(new Image(getClass().getResourceAsStream("/img/popetokens/pope-token-1-front.png")));

		if (players.get(playerID).getPopeTokens()[0].isDiscarded())		/* Back tokens (not active nor discarded) are displayed by default in the scene's fxml */
			popeTokenOne.setImage(null);

		if (players.get(playerID).getPopeTokens()[1].isActive())
			popeTokenTwo.setImage(new Image(getClass().getResourceAsStream("/img/popetokens/pope-token-2-front.png")));

		if (players.get(playerID).getPopeTokens()[1].isDiscarded())
			popeTokenTwo.setImage(null);

		if (players.get(playerID).getPopeTokens()[2].isActive())
			popeTokenThree.setImage(new Image(getClass().getResourceAsStream("/img/popetokens/pope-token-3-front.png")));

		if (players.get(playerID).getPopeTokens()[2].isDiscarded())
			popeTokenThree.setImage(null);
	}

	public void updateStorage(Storage storage)
	{
		switch (storage.getShelves()[0].getShelfResourceQuantity())
		{
			case 0:
				topShelfResource.setImage(null);
				break;

			case 1:
				topShelfResource.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[0].getShelfResourceType()))));
		}

		switch (storage.getShelves()[1].getShelfResource().getQuantity())
		{
			case 0:
				middleShelfResource1.setImage(null);
				middleShelfResource2.setImage(null);
				break;

			case 1:
				middleShelfResource1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[1].getShelfResourceType()))));
				middleShelfResource2.setImage(null);
				break;

			case 2:
				middleShelfResource1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[1].getShelfResourceType()))));
				middleShelfResource2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[1].getShelfResourceType()))));
				break;
		}

		switch (storage.getShelves()[2].getShelfResource().getQuantity())
		{
			case 0:
				bottomShelfResource1.setImage(null);
				bottomShelfResource2.setImage(null);
				bottomShelfResource3.setImage(null);
				break;

			case 1:
				bottomShelfResource1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				bottomShelfResource2.setImage(null);
				bottomShelfResource3.setImage(null);
				break;

			case 2:
				bottomShelfResource1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				bottomShelfResource2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				bottomShelfResource3.setImage(null);
				break;

			case 3:
				bottomShelfResource1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				bottomShelfResource2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				bottomShelfResource3.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[2].getShelfResourceType()))));
				break;
		}
	}

	public void updateVault(Vault vault)
	{
		vaultBlueAmount.setText(vault.getResourceAmounts().get(ResourceType.BLUE).toString());
		vaultYellowAmount.setText(vault.getResourceAmounts().get(ResourceType.YELLOW).toString());
		vaultGreyAmount.setText(vault.getResourceAmounts().get(ResourceType.GREY).toString());
		vaultPurpleAmount.setText(vault.getResourceAmounts().get(ResourceType.PURPLE).toString());
	}

	public void updateDevCardAreas(DevCardArea[] devCardAreas)
	{
		if (!devCardAreas[0].isEmpty())
			devCardArea1.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[0].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea1.setImage(null);

		if (!devCardAreas[1].isEmpty())
			devCardArea2.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[1].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea2.setImage(null);

		if (!devCardAreas[2].isEmpty())
			devCardArea3.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[2].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea3.setImage(null);
	}

	public void movePawn(ImageView pawn, int boxNumber)
	{
		switch (boxNumber)		/* setX vs setLayoutX? */
		{
			case 0:	 pawn.setLayoutX(22);	pawn.setLayoutY(112);	break;
			case 1:  pawn.setLayoutX(72);	pawn.setLayoutY(112);	break;
			case 2:	 pawn.setLayoutX(122);	pawn.setLayoutY(112);	break;
			case 3:	 pawn.setLayoutX(122);	pawn.setLayoutY(64);	break;
			case 4:	 pawn.setLayoutX(122);	pawn.setLayoutY(15);	break;
			case 5:	 pawn.setLayoutX(170);	pawn.setLayoutY(15);	break;
			case 6:	 pawn.setLayoutX(220);	pawn.setLayoutY(15);	break;
			case 7:	 pawn.setLayoutX(269);	pawn.setLayoutY(15);	break;
			case 8:	 pawn.setLayoutX(316);	pawn.setLayoutY(15);	break;
			case 9:	 pawn.setLayoutX(367);	pawn.setLayoutY(15);	break;
			case 10: pawn.setLayoutX(367);	pawn.setLayoutY(64);	break;
			case 11: pawn.setLayoutX(367);	pawn.setLayoutY(113);	break;
			case 12: pawn.setLayoutX(418);	pawn.setLayoutY(113);	break;
			case 13: pawn.setLayoutX(466);	pawn.setLayoutY(113);	break;
			case 14: pawn.setLayoutX(516);	pawn.setLayoutY(113);	break;
			case 15: pawn.setLayoutX(565);	pawn.setLayoutY(113);	break;
			case 16: pawn.setLayoutX(615);	pawn.setLayoutY(113);	break;
			case 17: pawn.setLayoutX(615);	pawn.setLayoutY(64);	break;
			case 18: pawn.setLayoutX(615);	pawn.setLayoutY(15);	break;
			case 19: pawn.setLayoutX(663);	pawn.setLayoutY(15);	break;
			case 20: pawn.setLayoutX(713);	pawn.setLayoutY(15);	break;
			case 21: pawn.setLayoutX(762);	pawn.setLayoutY(15);	break;
			case 22: pawn.setLayoutX(811);	pawn.setLayoutY(15);	break;
			case 23: pawn.setLayoutX(860);	pawn.setLayoutY(15);	break;
			case 24:
			default: pawn.setLayoutX(910);	pawn.setLayoutY(15);	break;
		}
	}

	public TextArea getConsole()
	{
		return console;
	}

	public ImageView getTopShelfResource()
	{
		return topShelfResource;
	}

	public ImageView getMiddleShelfResource1()
	{
		return middleShelfResource1;
	}

	public ImageView getMiddleShelfResource2()
	{
		return middleShelfResource2;
	}

	public ImageView getBottomShelfResource1()
	{
		return bottomShelfResource1;
	}

	public ImageView getBottomShelfResource2()
	{
		return bottomShelfResource2;
	}

	public ImageView getBottomShelfResource3()
	{
		return bottomShelfResource3;
	}

	public ImageView getDevCardArea1()
	{
		return devCardArea1;
	}

	public ImageView getDevCardArea2()
	{
		return devCardArea2;
	}

	public ImageView getDevCardArea3()
	{
		return devCardArea3;
	}

	public ImageView getDashboard()
	{
		return dashboard;
	}

	public void setMarblesScene(Scene marblesScene)
	{
		this.marblesScene = marblesScene;
	}
}
