package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.MessageIO;
import it.polimi.ingsw.client.view.gui.ConvertMethods;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.SkillProduction;
import it.polimi.ingsw.model.cards.SkillStorage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LeaderCardsController		/* TODO: add resources ImageViews in both leader card areas that are set for SkillStorage cards */
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
		messageHandler.send(Arrays.asList("DISCARD_LEADER", String.valueOf(leaderCards.get(0).getCardNumber())));
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

	@FXML
	void startActiveProduction1(MouseEvent event)
	{
		productionYL.setDisable(false);
		productionPL.setDisable(false);
		productionGL.setDisable(false);
		productionBL.setDisable(false);
		chooseResource1.setVisible(true);
	}

	@FXML
	void startActiveProduction2(MouseEvent event)
	{
		productionYR.setDisable(false);
		productionPR.setDisable(false);
		productionGR.setDisable(false);
		productionBR.setDisable(false);
		chooseResource2.setVisible(true);
	}

	private void activeProduction1(String resourceToAdd)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "LEADER_SKILL", String.valueOf(leaderCards.get(0).getCardNumber()), resourceToAdd));

		productionBL.setDisable(true);
		productionGL.setDisable(true);
		productionPL.setDisable(true);
		productionYL.setDisable(true);
		chooseResource1.setVisible(false);
	}

	private void activeProduction2(String resourceToAdd)
	{
		messageHandler.send(Arrays.asList("ACTIVATE_PRODUCTION", "LEADER_SKILL", String.valueOf(leaderCards.get(1).getCardNumber()), resourceToAdd));

		productionBR.setDisable(true);
		productionGR.setDisable(true);
		productionPR.setDisable(true);
		productionYR.setDisable(true);
		chooseResource2.setVisible(false);
	}

	public void update(List<LeaderCard> leaderCards, boolean isMyTurn)
	{
		this.leaderCards = leaderCards;

		leaderCard1.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(0).getCardNumber() + ".png")));
		leaderCard2.setImage(new Image(getClass().getResourceAsStream("/img/leadercards/" + leaderCards.get(1).getCardNumber() + ".png")));
		leaderCard1.setDisable(true);
		leaderCard2.setDisable(true);

		if (!leaderCards.get(0).isActive())
		{
			activateLeaderOneButton.setVisible(true);
			discardLeaderOneButton.setVisible(true);
		}

		if (!leaderCards.get(1).isActive())
		{
			activateLeaderTwoButton.setVisible(true);
			discardLeaderTwoButton.setVisible(true);
		}

		if (leaderCards.get(0).isActive())
		{
			activateLeaderOneButton.setVisible(false);
			discardLeaderOneButton.setVisible(false);

			if (leaderCards.get(0) instanceof SkillProduction)
			{
				leaderCard1.setDisable(false);
				leaderCard1.setCursor(Cursor.HAND);
				productionBL.setVisible(true);
				productionGL.setVisible(true);
				productionPL.setVisible(true);
				productionYL.setVisible(true);
			}

			if (leaderCards.get(0) instanceof SkillStorage)
			{
				resL1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceType()))));
				resL2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceType()))));

				if (((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceQuantity() == 0)
				{
					resL1.setVisible(false);
					resL2.setVisible(false);
				}

				else if (((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceQuantity() == 1)
				{
					resL1.setVisible(true);
					resL2.setVisible(false);
				}

				else if (((SkillStorage) leaderCards.get(0)).getAdditionalStorage().getShelfResourceQuantity() == 2)
				{
					resL1.setVisible(true);
					resL2.setVisible(true);
				}
			}
		}

		if (leaderCards.get(1).isActive())
		{
			activateLeaderTwoButton.setVisible(false);
			discardLeaderTwoButton.setVisible(false);

			if (leaderCards.get(1) instanceof SkillProduction)
			{
				leaderCard2.setDisable(false);
				leaderCard2.setCursor(Cursor.HAND);
				productionBR.setVisible(true);
				productionGR.setVisible(true);
				productionPR.setVisible(true);
				productionYR.setVisible(true);
			}

			if (leaderCards.get(1) instanceof SkillStorage)
			{
				resR1.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceType()))));
				resR2.setImage(new Image(getClass().getResourceAsStream(ConvertMethods.convertResTypeToPath(((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceType()))));

				if (((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceQuantity() == 0)
				{
					resR1.setVisible(false);
					resR2.setVisible(false);
				}

				else if (((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceQuantity() == 1)
				{
					resR1.setVisible(true);
					resR2.setVisible(false);
				}

				else if (((SkillStorage) leaderCards.get(1)).getAdditionalStorage().getShelfResourceQuantity() == 2)
				{
					resR1.setVisible(true);
					resR2.setVisible(true);
				}
			}
		}

		if (!isMyTurn)
		{
			for (int i = 0; i < buttons.size(); i++)
				buttons.get(i).setDisable(true);
		}
	}

	public void setup(GUI gui)
	{
		this.mainViewScene = gui.getMainViewScene();
		this.mainStage = gui.getMainStage();
		this.messageHandler = gui.getMessageHandler();

		buttons = new ArrayList<>();
		Collections.addAll(buttons, activateLeaderOneButton, activateLeaderTwoButton, discardLeaderOneButton, discardLeaderTwoButton);
	}
}