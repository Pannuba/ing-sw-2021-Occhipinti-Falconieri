package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.controllers.LauncherPlayController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/launcherPlay.fxml"));
		Parent root = loader.load();

		LauncherPlayController lpc = loader.getController();
		lpc.setup();

		Scene scene = new Scene(root);

		mainStage.setTitle("Masters of Renaissance Launcher");
		mainStage.getIcons().add(new Image("/img/inkwell.png"));		/* Check if it works from .jar */
		mainStage.setScene(scene);
		//mainStage.setResizable(false);
		mainStage.show();
	}
}
