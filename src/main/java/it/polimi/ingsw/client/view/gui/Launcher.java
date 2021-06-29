package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.controllers.LauncherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws IOException			/* Stage = window */
	{
		Font.loadFont(getClass().getResourceAsStream("/CharpentierRenRed-Normal.ttf"), 15);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/launcher.fxml"));
		Parent root = loader.load();
		Scene launcherScene = new Scene(root);

		LauncherController lpc = loader.getController();
		lpc.setup(launcherScene);

		mainStage.setTitle("Masters of Renaissance Launcher");
		mainStage.getIcons().add(new Image("/img/inkwell.png"));		/* Check if it works from .jar */
		mainStage.setScene(launcherScene);
		//mainStage.setResizable(false);
		mainStage.show();
	}
}
