package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.gui.controllers.GameStartController;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.client.view.gui.controllers.MarketsController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ActionGUI extends MessageExecutor
{
	private final GUIModel gui;
	private final NetworkHandler networkHandler;		/* To send commands to server */
	private final Scene gameStartScene;
	private final GameStartController gsc;
	private final MainViewController mvc;						/* To update the scenes when a new gamestate is received */
	private final MarketsController mmc;

	public ActionGUI(GUIModel gui, NetworkHandler networkHandler, Scene gameStartScene, GameStartController gsc, MainViewController mvc, MarketsController mmc)
	{
		this.gui = gui;
		this.networkHandler = networkHandler;
		this.gameStartScene = gameStartScene;
		this.gsc = gsc;
		this.mvc = mvc;		/* Pack all loaders in a hashmap? */
		this.mmc = mmc;
	}

	public void updateView(GameState gameState)
	{
		mvc.update(gameState, gui.getUsername());
		//lcc.update(gameState, gui.getUsername());
		mmc.updateMarket(gameState.getCurrMarblesMarket(), gameState.getCurrDevCardsMarket(), gameState.getPlayerByName(gui.getUsername()).isMyTurn());
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)	/* Open small window? */
	{

	}

	@Override
	public void startMatch()
	{
		Stage mainStage = (Stage) ((Node) gui.getEvent().getSource()).getScene().getWindow();

		Platform.runLater(() ->
		{
			mainStage.setTitle("Masters of Renaissance - Setup");
			mainStage.setScene(gameStartScene);
			mainStage.show();
		});
	}

	@Override
	public void initialResources(int playerID)
	{
		gsc.showResourcesMessage(playerID);
	}

	@Override
	public void vaticanReport(int popeBoxNum, List<Player> players)
	{
		mvc.getConsole().setText("Vatican report on box #" + popeBoxNum + "!");
	}

	@Override
	public void chooseLeaderCards(List<LeaderCard> leaderCards)
	{
		gsc.setLeaderCards(leaderCards);
	}

	@Override
	public void getBoughtResources(List<Resource> boughtResources)
	{
		mvc.printToConsole("Received " + boughtResources.size() + " resources");
	}

	@Override
	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{
		if (!playerWhoDiscarded.equals(gui.getUsername()))
			mvc.printToConsole(playerWhoDiscarded + " discarded " + discardedResNum + " resources, so you gained " + discardedResNum + " faith points!");

		else
			mvc.printToConsole(discardedResNum + " resources couldn't fit in the storage, so they have been discarded");
	}

	@Override
	public void getBoughtDevCard(DevCard boughtCard)
	{
		mvc.printToConsole("Received dev card #" + boughtCard.getCardNumber());
	}

	@Override
	public void getOperationResultMessage(String message, boolean isFailed)
	{
		mvc.printToConsole(message);

		if (!isFailed)		/* If the action fails the client doesn't receive a gamestate */
			mvc.disableButtons();
	}

	@Override
	public void getActionToken(ActionToken token)
	{
		mvc.printToConsole("Flipped next action token");
	}

	@Override
	public void matchOver(String winnerName, List<Player> players)
	{
		if (gui.getUsername().equals(winnerName))
			mvc.printToConsole("You win!");

		else
			mvc.printToConsole("Game over! The winner is " + winnerName);

		for (int i = 0; i < players.size(); i++)
			mvc.printToConsole(players.get(i).getUsername() + ": " + players.get(i).getVictoryPoints() + " points");
	}

	@Override
	public void singlePlayerGameOver(String message)
	{
		mvc.printToConsole(message);
	}
}
