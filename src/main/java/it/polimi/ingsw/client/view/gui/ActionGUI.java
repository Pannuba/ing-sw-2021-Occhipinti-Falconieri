package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.client.view.gui.controllers.MarketsController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.scene.image.Image;

import java.util.List;

public class ActionGUI extends MessageExecutor
{
	private final GUIModel gui;
	private final NetworkHandler networkHandler;		/* To send commands to server */
	private final MainViewController mvc;						/* To update the scenes when a new gamestate is received */
	private final MarketsController mmc;

	public ActionGUI(GUIModel gui, NetworkHandler networkHandler, MainViewController mvc, MarketsController mmc)
	{
		this.gui = gui;
		this.networkHandler = networkHandler;
		this.mvc = mvc;		/* Pack all loaders in a hashmap? */
		this.mmc = mmc;
	}

	public void updateView(GameState gameState)
	{
		mvc.updateStorage(gameState.getPlayerByName(gui.getUsername()).getDashboard().getStorage());
		mvc.updateVault(gameState.getPlayerByName(gui.getUsername()).getDashboard().getVault());
		mvc.updateTrack(gameState.getCurrTrack(), gameState.getCurrPlayers(), gameState.getPlayerByName(gui.getUsername()).getId());
		mvc.updateDevCardAreas(gameState.getPlayerByName(gui.getUsername()).getDashboard().getDevCardAreas());
		mvc.getDefaultProductionButton().setDisable(false);
		mmc.updateMarket(gameState.getCurrMarblesMarket(), gameState.getCurrDevCardsMarket(), gameState.getPlayerByName(gui.getUsername()).isMyTurn());
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)
	{
		mvc.getMiddleShelfResource1().setImage(new Image(getClass().getResourceAsStream("/img/resources/blue.png")));
		mvc.getMiddleShelfResource1().setImage(null);		/* null works */
	}

	@Override
	public void startMatch()
	{
		mvc.printToConsole("Match started");
	}

	@Override
	public void initialResources(int playerID)		/* TODO: make a scene with leaders choice and/or the initial resources choice */
	{
		mvc.printToConsole("Player ID: " + playerID);		/* TODO: mvc.updateConsole("String") */
	}

	@Override
	public void vaticanReport(int popeBoxNum, List<Player> players)
	{

	}

	@Override
	public void chooseLeaderCards(List<LeaderCard> leaderCards)
	{

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
			mvc.printToConsole("You win! ");

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
