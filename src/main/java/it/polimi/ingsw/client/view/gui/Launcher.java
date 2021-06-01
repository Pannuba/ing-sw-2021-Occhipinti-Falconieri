package it.polimi.ingsw.client.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException			/* Stage = window */
	{
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/scenes/launcher.fxml"));		/* TODO: launcher controller */
		root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setTitle("Masters of Renaissance Launcher");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();		/* ? */
		primaryStage.show();
	}
}
