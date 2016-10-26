package com.nkoeppel.jtrail;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static int resX = 1280;
	public static int resY = 720;
	public static int fullresX = 1920;
	public static int fullresY = 1080;
	public static boolean gameStarted = false;
	public static boolean fullScreen = false;
	public static int imgMultiplier = 4;
	public static String version = "Alpha 0.01";
	public static int level = 0;
	public Group group;

	public static void main(String[] args) {
		Application.launch(args);
	}


	public void start(Stage window) throws Exception {
		window.setTitle("Jupiter Trail " + version);
		StackPane root = new StackPane();
		group = new Group();
		Scene scene = new Scene(root, resX,resY);
		Canvas canvas = new Canvas(resX,resY);
		group.getChildren().add(canvas);
		root.getChildren().add(group);
		
		AnimationTimer animation = new Animation(canvas, group, window);
		animation.start();
		
		window.setScene(scene);
		window.setResizable(false);
		window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		window.show();
		
	}

}
