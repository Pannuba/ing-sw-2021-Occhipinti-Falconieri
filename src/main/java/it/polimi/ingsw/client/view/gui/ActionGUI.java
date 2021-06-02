package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.MessageExecutor;
import it.polimi.ingsw.client.view.gui.controllers.MainViewController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.ActionToken;
import it.polimi.ingsw.model.cards.DevCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.util.List;

public class ActionGUI extends MessageExecutor
{
	private final GUIModel gui;
	private final NetworkHandler networkHandler;		/* To send commands to server */
	private final FXMLLoader mainViewLoader;						/* To update the scenes when a new gamestate is received */
	private final FXMLLoader marblesLoader;

	/* TODO: add controller instance variables, get controllers from loaders in constructors */

	public ActionGUI(GUIModel gui, NetworkHandler networkHandler, FXMLLoader mainViewLoader, FXMLLoader marblesLoader)
	{
		this.gui = gui;
		this.networkHandler = networkHandler;
		this.mainViewLoader = mainViewLoader;		/* Pack all loaders in a hashmap? */
		this.marblesLoader = marblesLoader;
	}

	public void updateView(GameState gameState)
	{
		MainViewController mvc = mainViewLoader.getController();
		mvc.updateStorage(gameState.getPlayerByName(gui.getUsername()).getDashboard().getStorage());
	}

	@Override
	public void firstPlayer(boolean isFirstPlayer)
	{
		MainViewController mvc = mainViewLoader.getController();			/* IT WORKSSSSSSSSSSS */
		mvc.getMiddleShelfResource1().setImage(new Image(getClass().getResourceAsStream("/img/resources/blue.png")));
		mvc.getMiddleShelfResource1().setImage(null);		/* null works */
	}

	@Override
	public void startMatch()
	{

	}

	@Override
	public void chooseResources(int playerID)
	{
		MainViewController mvc = mainViewLoader.getController();
		mvc.getConsole().setText("You're the first player, so you get nothing!");
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
