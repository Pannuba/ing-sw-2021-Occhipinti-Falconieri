package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.board.DevCardArea;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Track;
import it.polimi.ingsw.model.board.Vault;
import it.polimi.ingsw.model.cards.ActionDevCard;
import it.polimi.ingsw.model.cards.ActionToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Empty images that are updated with image.setImage("...") each gamestate update */

public class MainViewController
{
	private Stage mainStage;
	private Scene mainViewScene;
	private Scene marketsScene;
	private Scene leaderCardsScene;
	private MessageIO messageHandler;
	private String username;
	private GameState gameState;
	private List<String> defaultProdRes;
	private boolean isBuyingDevCard;

	private int devCardToBuy;

	@FXML private ImageView dashboard;

	@FXML private Button marketsButton;
	@FXML private Button leaderCardsButton;

	@FXML private TextArea console;

	@FXML private ImageView redPawn;
	@FXML private ImageView blackPawn;

	@FXML private ImageView popeTokenOne;			/* Rename to 1, 2, 3 */
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

	@FXML private ImageView actionTokenFront;
	@FXML private ImageView actionTokenBack;

	@FXML private Button defaultProductionButton;

	@FXML private Button otherPlayersButton;

	public void update(GameState gameState, String username)
	{
		this.gameState = gameState;

		if (gameState.getCurrPlayers().size() == 1)
		{
			otherPlayersButton.setVisible(false);
			actionTokenFront.setVisible(true);
			actionTokenBack.setVisible(true);
		}

		updateStorage(gameState.getPlayerByName(username).getDashboard().getStorage());
		updateVault(gameState.getPlayerByName(username).getDashboard().getVault());
		updateTrack(gameState.getCurrTrack(), gameState.getCurrPlayers(), gameState.getPlayerByName(username).getId());
		updateDevCardAreas(gameState.getPlayerByName(username).getDashboard().getDevCardAreas());

		if (gameState.getPlayerByName(username).isMyTurn())
		{
			printToConsole("It's your turn! ");
			enableButtons();
		}

		else
		{
			printToConsole("It's " + gameState.getCurrPlayerName() + "'s turn!");
			disableButtons();
		}
	}

	@FXML	/* TODO: add boolean isDoingDefaultProduction to disable by pressing the same button */
	void startDefaultProduction(ActionEvent event)		/* Triggered by default production button */
	{
		Platform.runLater(() -> {
			mainStage.setTitle("Masters of Renaissance - Select 2 resources");
			printToConsole("Click the 2 resources you want to convert, then the resource you want to make\n(Vault icons)");
			vaultResourceBlue.setDisable(false);
			vaultResourcePurple.setDisable(false);
			vaultResourceYellow.setDisable(false);
			vaultResourceGrey.setDisable(false);
		});

		defaultProdRes = new ArrayList<>();
	}

	@FXML
	void selectBlueResource(MouseEvent event)
	{
		defaultProduction("B");
	}

	@FXML
	void selectPurpleResource(MouseEvent event)
	{
		defaultProduction("P");
	}

	@FXML
	void selectYellowResource(MouseEvent event)
	{
		defaultProduction("Y");
	}

	@FXML
	void selectGreyResource(MouseEvent event)
	{
		defaultProduction("G");
	}

	private void defaultProduction(String resourceToAdd)
	{
		defaultProdRes.add(resourceToAdd);

		if (defaultProdRes.size() == 3)
		{
			messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "DEFAULT", defaultProdRes.get(0), defaultProdRes.get(1), defaultProdRes.get(2)));

			Platform.runLater(() -> {
				vaultResourceBlue.setDisable(true);
				vaultResourcePurple.setDisable(true);
				vaultResourceYellow.setDisable(true);
				vaultResourceGrey.setDisable(true);
			});
		}
	}

	@FXML
	void showLeaderCards(ActionEvent event)
	{
		mainStage.setTitle("Masters of Renaissance - Leader Cards");
		mainStage.setScene(leaderCardsScene);
		mainStage.sizeToScene();
		mainStage.show();
	}

	@FXML
	void showMarkets(ActionEvent event)
	{
		mainStage.setTitle("Masters of Renaissance - Markets");
		mainStage.setScene(marketsScene);
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

		for (int i = 0; i < players.size(); i++)
		{
			if (!players.get(i).getUsername().equals(username))
				printToConsole(players.get(i).getUsername() + " is at position " + track.getRedPawns().get(i) + "/24");
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

	public void updateActionToken(ActionToken token)
	{
		switch (token.getClass().getSimpleName())
		{
			case "ActionBlack1":
				actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-black-1.png")));
				break;
			case "ActionBlack2":
				actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-black-2.png")));
				break;
			case  "ActionDevCard":
				switch (((ActionDevCard) token).getColor())
				{
					case GREEN:
						actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-dev-card-green.png")));
						break;
					case BLUE:
						actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-dev-card-blue.png")));
						break;
					case PURPLE:
						actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-dev-card-purple.png")));
						break;
					case YELLOW:
						actionTokenFront.setImage(new Image(getClass().getResourceAsStream("/img/actiontokens/action-dev-card-yellow.png")));
						break;
				}
				break;
		}
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

	@FXML
	void selectDevCardArea1(MouseEvent event)
	{
		selectDevCardArea(1, event);
	}

	@FXML
	void selectDevCardArea2(MouseEvent event)
	{
		selectDevCardArea(2, event);
	}

	@FXML
	void selectDevCardArea3(MouseEvent event)
	{
		selectDevCardArea(3, event);
	}

	void selectDevCardArea(int devCardAreaNum, MouseEvent event)
	{
		if (isBuyingDevCard)
			messageHandler.send(Arrays.asList("BUY_DEVCARD", String.valueOf(devCardToBuy), String.valueOf(devCardAreaNum)));

		else		/* This way the server should never receive a null devcard number, also in CLI */
		{
			if (gameState.getPlayerByName(username).getDashboard().getDevCardAreas()[devCardAreaNum - 1].isEmpty())
				printToConsole("You don't have any dev cards in this area!");

			else
			{
				int cardNum = gameState.getPlayerByName(username).getDashboard().getDevCardAreas()[devCardAreaNum - 1].getTopDevCard().getCardNumber();
				messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "DEVCARD", String.valueOf(cardNum)));
			}
		}

		isBuyingDevCard = false;

		Platform.runLater(() -> {	/* Necessary here? Maybe only for buttons/ImageViews */
			mainStage.setTitle("Masters of Renaissance");
		});
	}

	public void enableButtons()
	{
		defaultProductionButton.setDisable(false);

		devCardArea1.setDisable(false);        /* For production using devcards */
		devCardArea2.setDisable(false);
		devCardArea3.setDisable(false);
	}

	public void disableButtons()
	{
		defaultProductionButton.setDisable(true);

		devCardArea1.setDisable(true);
		devCardArea2.setDisable(true);
		devCardArea3.setDisable(true);
	}

	@FXML
	void showOtherPlayers(ActionEvent event) throws IOException
	{
		FXMLLoader otherBoardsLoader = new FXMLLoader();
		otherBoardsLoader.setLocation(getClass().getResource("/scenes/otherboards.fxml"));
		Parent otherBoardsRoot = otherBoardsLoader.load();
		Scene otherBoardsScene = new Scene(otherBoardsRoot);
		OtherBoardsController obc = otherBoardsLoader.getController();

		String playerToShow = null;

		for (int i = 0; i < gameState.getCurrPlayers().size(); i++)
		{
			if (!gameState.getCurrPlayers().get(i).getUsername().equals(username))		/* If it's not my username */
				playerToShow = gameState.getCurrPlayers().get(i).getUsername();
		}

		obc.setup(mainViewScene, mainStage, username);
		obc.update(gameState, playerToShow);
		mainStage.setScene(otherBoardsScene);
		mainStage.sizeToScene();
		mainStage.show();
	}


	public ImageView getDashboard()
	{
		return dashboard;
	}

	public TextArea getConsole()
	{
		return console;
	}

	public void printToConsole(String message)
	{
		Platform.runLater(() -> {
			console.setText(console.getText() + "\n" + message);
		});
	}

	public void setup(GUI gui, Scene mainViewScene, Scene marketsScene, Scene leaderCardsScene)
	{
		this.username = gui.getUsername();
		this.mainStage = gui.getMainStage();
		this.mainViewScene = mainViewScene;
		this.marketsScene = marketsScene;
		this.leaderCardsScene = leaderCardsScene;
		this.messageHandler = gui.getMessageHandler();
	}

	public void setDevCardToBuy(int devCardToBuy)
	{
		this.devCardToBuy = devCardToBuy;
	}

	public void setBuyingDevCard(boolean buyingDevCard)
	{
		isBuyingDevCard = buyingDevCard;
	}
}
