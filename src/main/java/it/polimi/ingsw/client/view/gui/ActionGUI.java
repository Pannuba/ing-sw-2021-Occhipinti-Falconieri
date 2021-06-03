package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.client.view.gui.controllers.MarblesMarketController;
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
	private final MarblesMarketController mmc;

	public ActionGUI(GUIModel gui, NetworkHandler networkHandler, MainViewController mvc, MarblesMarketController mmc)
	{
		this.gui = gui;
		this.networkHandler = networkHandler;
		this.mvc = mvc;		/* Pack all loaders in a hashmap? */
		this.mmc = mmc;
	}

	public void updateView(GameState gameState)
	{
		mvc.updateStorage(gameState.getPlayerByName(gui.getUsername()).getDashboard().getStorage());
		mvc.updateTrack(gameState.getCurrTrack());
		mvc.updateDevCardAreas(gameState.getPlayerByName(gui.getUsername()).getDashboard().getDevCardAreas());
		mmc.updateMarket(gameState.getCurrMarblesMarket(), gameState.getPlayerByName(gui.getUsername()).isMyTurn());
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

	}

	@Override
	public void initialResources(int playerID)		/* TODO: make a scene with leaders choice and/or the initial resources choice */
	{
		mvc.getConsole().setText(mvc.getConsole().getText() + "\nPlayer ID: " + playerID);		/* TODO: mvc.updateConsole("String") */
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

	}

	@Override
	public void getDiscardedResources(int discardedResNum, String playerWhoDiscarded)
	{

	}

	@Override
	public void getBoughtDevCard(DevCard boughtCard)
	{

	}

	@Override
	public void getOperationResultMessage(String message, boolean isFailed)
	{

	}

	@Override
	public void getActionToken(ActionToken token)
	{

	}

	@Override
	public void matchOver(String winnerName, List<Player> players)
	{

	}

	@Override
	public void singlePlayerGameOver(String message)
	{

	}
}
