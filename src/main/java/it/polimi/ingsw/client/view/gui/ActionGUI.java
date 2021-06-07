package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.gui.controllers.*;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class ActionGUI extends MessageExecutor
{
	private final GUIModel gui;
	private final NetworkHandler networkHandler;		/* To send commands to server */
	private final Scene gameStartScene;
	private final LauncherController lc;
	private final GameStartController gsc;
	private final LeaderCardsController lcc;
	private final MainViewController mvc;						/* To update the scenes when a new gamestate is received */
	private final MarketsController mmc;

	public ActionGUI(GUIModel gui, NetworkHandler networkHandler, Scene gameStartScene, LauncherController lc,
					 GameStartController gsc, LeaderCardsController lcc, MainViewController mvc, MarketsController mmc)
	{
		this.gui = gui;
		this.networkHandler = networkHandler;
		this.gameStartScene = gameStartScene;
		this.lc = lc;
		this.gsc = gsc;
		this.lcc = lcc;
		this.mvc = mvc;		/* Pack all loaders in a hashmap? */
		this.mmc = mmc;
	}

	public void updateView(GameState gameState)
	{
		Platform.runLater(() -> {
			mvc.update(gameState, gui.getUsername());
			lcc.update(gameState.getPlayerByName(gui.getUsername()).getLeaderCards(), gameState.getPlayerByName(gui.getUsername()).isMyTurn());
			mmc.updateMarket(gameState.getCurrMarblesMarket(), gameState.getCurrDevCardsMarket(), gameState.getPlayerByName(gui.getUsername()).isMyTurn());
		});
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)
	{
		if (isFirstPlayer)
			lc.firstPlayer();
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
