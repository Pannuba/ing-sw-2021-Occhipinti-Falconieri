package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.model.board.DevCardArea;
import it.polimi.ingsw.model.board.Storage;
import it.polimi.ingsw.model.board.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/* Empty images that are updated with image.setImage("...") each gamestate update */

public class MainViewController
{
	private Scene marblesScene;

	@FXML private ImageView dashboard;

	@FXML private Button marblesMarketButton;
	@FXML private Button devCardsMarketButton;
	@FXML private Button leaderCardsButton;

	@FXML private TextArea console;

	@FXML private ImageView topShelfResource;

	@FXML private ImageView middleShelfResource1;
	@FXML private ImageView middleShelfResource2;

	@FXML private ImageView bottomShelfResource1;
	@FXML private ImageView bottomShelfResource2;
	@FXML private ImageView bottomShelfResource3;

	@FXML private ImageView devCardArea1;
	@FXML private ImageView devCardArea2;
	@FXML private ImageView devCardArea3;

	@FXML
	void showDevCardsMarket(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance - Dev Cards Market");
		//mainStage.setScene(devCardsMarketScene);
		mainStage.sizeToScene();
		mainStage.show();
	}

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
	void showMarblesMarket(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance - Marbles Market");
		mainStage.setScene(marblesScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	public void updateTrack(Track track)	/* TODO: create an ImageView for each box */
	{

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

	public void updateDevCardAreas(DevCardArea[] devCardAreas)
	{
		if (devCardAreas[0] != null)
			devCardArea1.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[0].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea1.setImage(null);

		if (devCardAreas[1] != null)
			devCardArea2.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[1].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea2.setImage(null);

		if (devCardAreas[2] != null)
			devCardArea3.setImage(new Image(getClass().getResourceAsStream("/img/devcards/" + devCardAreas[2].getTopDevCard().getCardNumber() + ".png")));

		else
			devCardArea3.setImage(null);
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
