package it.polimi.ingsw.client.view.gui;

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
		loader.setLocation(getClass().getResource("/scenes/launcher.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);

		mainStage.setTitle("Masters of Renaissance Launcher");
		mainStage.getIcons().add(new Image("/img/inkwell.png"));
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.sizeToScene();		/* ? */
		mainStage.show();
	}
}
