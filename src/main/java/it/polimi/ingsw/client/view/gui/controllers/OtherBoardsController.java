package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.board.DevCardArea;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.board.Vault;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public class OtherBoardsController
{
	private GameState gameState;
	private Scene mainViewScene;
	private Stage mainStage;
	private String myUsername;
	private int currentID;

	@FXML private ImageView dashboard;

	@FXML private TextArea console;

	@FXML private ImageView redPawn;

	@FXML private ImageView topShelfResource;

	@FXML private ImageView popeTokenOne;
	@FXML private ImageView popeTokenTwo;
	@FXML private ImageView popeTokenThree;

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

	@FXML private Button nextButton;

	@FXML private Button backButton;

	@FXML private Label usernameLabel;

	@FXML
	void backToBoard(ActionEvent event)
	{
		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.show();
	}

	@FXML
	void showNextBoard(ActionEvent event)
	{
		String nextUsername = null;

		do
		{
			if (currentID == gameState.getCurrPlayers().size() - 1)
				currentID = 0;

			else
				currentID += 1;

		} while (gameState.getCurrPlayers().get(currentID).getUsername().equals(myUsername));

		for (int i = 0; i < gameState.getCurrPlayers().size(); i++)
		{
			if (gameState.getCurrPlayers().get(i).getId() == currentID)
				nextUsername = gameState.getCurrPlayers().get(i).getUsername();
		}

		update(gameState, nextUsername);
	}

	public void update(GameState gameState, String username)		/* Username of the player to show */
	{
		this.gameState = gameState;
		currentID = gameState.getPlayerByName(username).getId();

		mainStage.setTitle("Masters of Renaissance - " + username + "'s Board");
		usernameLabel.setText(username + "'s Board");

		updateStorage(gameState.getPlayerByName(username).getDashboard().getStorage());
		updateVault(gameState.getPlayerByName(username).getDashboard().getVault());
		updateTrack(gameState.getCurrTrack(), gameState.getCurrPlayers(), gameState.getPlayerByName(username).getId());
		updateDevCardAreas(gameState.getPlayerByName(username).getDashboard().getDevCardAreas());
	}

	public void updateTrack(Track track, List<Player> players, int playerID)		/* Also print other players */
	{
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

			case 1:									/* getResource vs getResourceAsStream? */
				topShelfResource.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(storage.getShelves()[0].getShelfResourceType()))));
		}

		switch (storage.getShelves()[1].getShelfResourceQuantity())
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

		switch (storage.getShelves()[2].getShelfResourceQuantity())
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

		else devCardArea1.setImage(null);

		if (!devCardAreas[1].isEmpty())
			devCardArea2.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[1].getTopDevCard().getCardNumber() + ".png")));

		else devCardArea2.setImage(null);

		if (!devCardAreas[2].isEmpty())
			devCardArea3.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[2].getTopDevCard().getCardNumber() + ".png")));

		else devCardArea3.setImage(null);
	}

	public void movePawn(ImageView pawn, int boxNumber)
	{
		switch (boxNumber)
		{
			case 0:	 pawn.setLayoutX(26);	pawn.setLayoutY(128);	break;
			case 1:  pawn.setLayoutX(75);	pawn.setLayoutY(128);	break;
			case 2:	 pawn.setLayoutX(124);	pawn.setLayoutY(128);	break;
			case 3:	 pawn.setLayoutX(124);	pawn.setLayoutY(80);	break;
			case 4:	 pawn.setLayoutX(124);	pawn.setLayoutY(29);	break;
			case 5:	 pawn.setLayoutX(173);	pawn.setLayoutY(29);	break;
			case 6:	 pawn.setLayoutX(223);	pawn.setLayoutY(29);	break;
			case 7:	 pawn.setLayoutX(272);	pawn.setLayoutY(29);	break;
			case 8:	 pawn.setLayoutX(320);	pawn.setLayoutY(29);	break;
			case 9:	 pawn.setLayoutX(370);	pawn.setLayoutY(29);	break;
			case 10: pawn.setLayoutX(370);	pawn.setLayoutY(79);	break;
			case 11: pawn.setLayoutX(370);	pawn.setLayoutY(127);	break;
			case 12: pawn.setLayoutX(420);	pawn.setLayoutY(127);	break;
			case 13: pawn.setLayoutX(470);	pawn.setLayoutY(127);	break;
			case 14: pawn.setLayoutX(520);	pawn.setLayoutY(127);	break;
			case 15: pawn.setLayoutX(569);	pawn.setLayoutY(127);	break;
			case 16: pawn.setLayoutX(617);	pawn.setLayoutY(127);	break;
			case 17: pawn.setLayoutX(617);	pawn.setLayoutY(79);	break;
			case 18: pawn.setLayoutX(617);	pawn.setLayoutY(29);	break;
			case 19: pawn.setLayoutX(667);	pawn.setLayoutY(29);	break;
			case 20: pawn.setLayoutX(717);	pawn.setLayoutY(29);	break;
			case 21: pawn.setLayoutX(766);	pawn.setLayoutY(29);	break;
			case 22: pawn.setLayoutX(815);	pawn.setLayoutY(29);	break;
			case 23: pawn.setLayoutX(864);	pawn.setLayoutY(29);	break;
			case 24:
			default: pawn.setLayoutX(914);	pawn.setLayoutY(15);	break;
		}
	}

	public void setup(Scene mainViewScene, Stage mainStage, String myUsername)
	{
		this.mainViewScene = mainViewScene;
		this.mainStage = mainStage;
		this.myUsername = myUsername;
	}
}
