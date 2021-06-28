package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;
import it.polimi.ingsw.model.cards.SkillStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Scene controller to see your leader cards and to activate or discard them
 * @author Giulio Occhipinti
 * @author Chiara Falconieri
 */

public class LeaderCardsController
{
	private Stage mainStage;
	private Scene mainViewScene;
	private MainViewController mvc;
	private MessageIO messageHandler;
	private List<LeaderCard> leaderCards;
	private List<Button> buttons;
	private Lighting lighting = new Lighting();

	@FXML private ImageView leaderCard1;
	@FXML private ImageView leaderCard2;

	@FXML private Button activateLeaderOneButton;
	@FXML private Button activateLeaderTwoButton;
	@FXML private Button discardLeaderOneButton;
	@FXML private Button discardLeaderTwoButton;

	@FXML private Button backToBoardButton;

	@FXML private ImageView resL1;
	@FXML private ImageView resL2;
	@FXML private ImageView resR1;
	@FXML private ImageView resR2;

	@FXML private ImageView productionPL;
	@FXML private ImageView productionYL;
	@FXML private ImageView productionGL;
	@FXML private ImageView productionBL;
	@FXML private ImageView productionPR;
	@FXML private ImageView productionYR;
	@FXML private ImageView productionGR;
	@FXML private ImageView productionBR;

	@FXML private Label chooseResource1;
	@FXML private Label chooseResource2;

	@FXML
	void selectBlueResource1(MouseEvent event)
	{
		activeProduction1("B");
	}

	@FXML
	void selectPurpleResource1(MouseEvent event)
	{
		activeProduction1("P");
	}

	@FXML
	void selectYellowResource1(MouseEvent event)
	{
		activeProduction1("Y");
	}

	@FXML
	void selectGreyResource1(MouseEvent event)
	{
		activeProduction1("G");
	}

	@FXML
	void selectBlueResource2(MouseEvent event)
	{
		activeProduction2("B");
	}

	@FXML
	void selectPurpleResource2(MouseEvent event)
	{
		activeProduction2("P");
	}

	@FXML
	void selectYellowResource2(MouseEvent event)
	{
		activeProduction2("Y");
	}

	@FXML
	void selectGreyResource2(MouseEvent event)
	{
		activeProduction2("G");
	}

	@FXML
	void activateLeaderCard1(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
		activateLeaderOneButton.setVisible(false);
		discardLeaderOneButton.setVisible(false);
	}

	@FXML
	void activateLeaderCard2(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_LEADER", String.valueOf(leaderCards.get(1).getCardNumber())));
		activateLeaderTwoButton.setVisible(false);
		discardLeaderTwoButton.setVisible(false);
	}

	@FXML
	void discardLeaderCard1(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("DISCARD_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
		activateLeaderOneButton.setVisible(false);
		discardLeaderOneButton.setVisible(false);
		lighting.setDiffuseConstant(0.5);
		lighting.setSpecularConstant(0.0);
		leaderCard1.setEffect(lighting);
	}

	@FXML
	void discardLeaderCard2(ActionEvent event)
	{
		messageHandler.send(Arrays.asList("DISCARD_LEADER", String.valueOf(leaderCards.get(1).getCardNumber())));
		activateLeaderTwoButton.setVisible(false);
		discardLeaderTwoButton.setVisible(false);
		lighting.setDiffuseConstant(0.5);
		lighting.setSpecularConstant(0.0);
		leaderCard2.setEffect(lighting);
	}

	@FXML
	void backToBoard(ActionEvent event)
	{
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		mainStage.setTitle("Masters of Renaissance");
		mainStage.setScene(mainViewScene);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}

	/**
	 * If left leader card is a skillProduction it activates the production
	 */

	private void activeProduction1(String resourceToAdd)
	{
		if (!mvc.isDoingProduction())
			mvc.startProduction();

		messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "LEADER_SKILL", String.valueOf(leaderCards.get(0).getCardNumber()), resourceToAdd));

		setLeftProductionImagesDisabled(true);
		chooseResource1.setVisible(false);
	}

	/**
	 * If right leader card is a skillProduction it activates the production
	 */

	private void activeProduction2(String resourceToAdd)
	{
		if (!mvc.isDoingProduction())
			mvc.startProduction();

		messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "LEADER_SKILL", String.valueOf(leaderCards.get(1).getCardNumber()), resourceToAdd));

		setRightProductionImagesDisabled(true);
		chooseResource2.setVisible(false);
	}

	/**
	 * Updates the scene when a leader card is activated or discarded
	 * If it is a leader card with production skill and it has been activated, it shows the four resources to be able to choose which one to receive from production
	 * If it is a leader card with storage skill and has been activated, it makes the resources in its storage visible
	 */

	public void update(List<LeaderCard> leaderCards, boolean isMyTurn)
	{
		this.leaderCards = leaderCards;

		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));

		if (leaderCards.get(0).isActive() || leaderCards.get(0).isDiscarded())
		{
			activateLeaderOneButton.setVisible(false);
			discardLeaderOneButton.setVisible(false);

			if (leaderCards.get(0).isActive() && leaderCards.get(0) instanceof SkillStorage)
			{
				resL1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceType()))));
				resL2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceType()))));

				switch (((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceQuantity())
				{
					case 0:
						resL1.setVisible(false);
						resL2.setVisible(false);
						break;

					case 1:
						resL1.setVisible(true);
						resL2.setVisible(false);
						break;

					case 2:
						resL1.setVisible(true);
						resL2.setVisible(true);
						break;
				}
			}
		}

		if (leaderCards.get(1).isActive() || leaderCards.get(1).isDiscarded())
		{
			activateLeaderTwoButton.setVisible(false);
			discardLeaderTwoButton.setVisible(false);

			if (leaderCards.get(1).isActive() && leaderCards.get(1) instanceof SkillStorage)
			{
				resR1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceType()))));
				resR2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceType()))));

				switch (((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceQuantity())
				{
					case 0:
						resR1.setVisible(false);
						resR2.setVisible(false);
						break;

					case 1:
						resR1.setVisible(true);
						resR2.setVisible(false);
						break;

					case 2:
						resR1.setVisible(true);
						resR2.setVisible(true);
						break;
				}
			}
		}

		checkSkillProduction();		/* Checks if the player has any active SkillProduction cards, if so makes the resource images visible */

		if (!isMyTurn)		/* Need to check card type and if it's active, to prevent the client from sending a command when they don't have a SkillProduction */
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(true);

			setLeftProductionImagesDisabled(true);
			setRightProductionImagesDisabled(true);
		}

		else	/* It's my turn */
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(false);

			setLeftProductionImagesDisabled(false);
			setRightProductionImagesDisabled(false);
		}
	}

	public void enableButtonsForProduction()		/* Don't care about SkillProduction resources, always keep them visibile since they're not buttons */
	{
		if (!leaderCards.get(0).isActive() && !leaderCards.get(0).isDiscarded())
		{
			activateLeaderOneButton.setVisible(true);
			discardLeaderOneButton.setVisible(true);
		}

		if (!leaderCards.get(1).isActive() && !leaderCards.get(1).isDiscarded())
		{
			activateLeaderTwoButton.setVisible(true);
			discardLeaderTwoButton.setVisible(true);
		}

		checkSkillProduction();
	}

	public void disableButtonsForProduction()
	{
		activateLeaderOneButton.setVisible(false);
		discardLeaderOneButton.setVisible(false);
		activateLeaderTwoButton.setVisible(false);
		discardLeaderTwoButton.setVisible(false);
	}

	public void checkSkillProduction()
	{
		if (leaderCards.get(0).isActive() && leaderCards.get(0) instanceof SkillProduction)
			setLeftProductionImagesVisible(true);

		else
			setLeftProductionImagesVisible(false);

		if (leaderCards.get(1).isActive() && leaderCards.get(1) instanceof SkillProduction)
			setRightProductionImagesVisible(true);

		else
			setRightProductionImagesVisible(false);
	}

	public void setLeftProductionImagesVisible(boolean visible)
	{
		productionBL.setVisible(visible);
		productionGL.setVisible(visible);
		productionPL.setVisible(visible);
		productionYL.setVisible(visible);
	}

	public void setRightProductionImagesVisible(boolean visible)
	{
		productionBR.setVisible(visible);
		productionGR.setVisible(visible);
		productionPR.setVisible(visible);
		productionYR.setVisible(visible);
	}

	public void setLeftProductionImagesDisabled(boolean disabled)
	{
		productionBL.setDisable(disabled);
		productionGL.setDisable(disabled);
		productionPL.setDisable(disabled);
		productionYL.setDisable(disabled);
	}

	public void setRightProductionImagesDisabled(boolean disabled)
	{
		productionBR.setDisable(disabled);
		productionGR.setDisable(disabled);
		productionPR.setDisable(disabled);
		productionYR.setDisable(disabled);
	}

	public void setup(GUI gui, MainViewController mvc)
	{
		this.mainViewScene = gui.getMainViewScene();
		this.mvc = mvc;
		this.mainStage = gui.getMainStage();
		this.messageHandler = gui.getMessageHandler();

		buttons = new ArrayList<>();
		Collections.addAll(buttons, activateLeaderOneButton, activateLeaderTwoButton, discardLeaderOneButton, discardLeaderTwoButton);
	}
}