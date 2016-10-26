package com.nkoeppel.jtrail;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Animation extends AnimationTimer {
	GraphicsContext canvas;
	Group group;
	Stage window;
	ShowImage showBackground;
	ShowImage showTitle;
	ShowImage fullscreenButton;

	long startNanoTime = System.nanoTime();

	int modifierX = 0;
	int modifierDX = 0;
	int modifierY = 0;
	int modifierDY = 0;
	double lastTick = 0;
	double speed = .2;

	Button startButton = new Button("Start");
	Button exitButton = new Button("Exit");
	ImageView fullScreen = new ImageView();

	public Animation(final Canvas canvas, Group group, final Stage window) {
		this.canvas = canvas.getGraphicsContext2D();
		this.group = group;
		this.window = window;
		showBackground = new ShowImage("concept1.png", this.canvas);
		showTitle = new ShowImage("title.png", this.canvas);
		fullscreenButton = new ShowImage("fullscreen.png", this.canvas);
		group.getChildren().add(exitButton);
		group.getChildren().add(startButton);
		group.getChildren().add(fullScreen);

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (Main.gameStarted) {
					Main.gameStarted = false;
					startButton.setText("Start");
					showBackground.setPath("concept1.png");
				} else {
					startButton.setText("Stop");
					showBackground.setPath("concept2.png");
					Main.gameStarted = true;
				}
			}
		});
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				window.close();
			}
		});
		fullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				if (!window.isFullScreen()) {
					window.setFullScreen(true);
					Main.resX = 1920;
					Main.resY = 1080;
					Main.imgMultiplier = 6;
				} else {
					window.setFullScreen(false);
					Main.resX = 1280;
					Main.resY = 720;
					Main.imgMultiplier = 4;
				}
				setLayout(startButton, exitButton, fullScreen);
				canvas.setWidth(Main.resX);
				canvas.setHeight(Main.resY);
			}
		});
	}

	public void handle(long now) {
		double tick = (now - startNanoTime) / 1000000000.0;

		if ((tick >= (lastTick + speed)) && Main.gameStarted) {
			if (modifierX < 3 && modifierDX == 0) {
				modifierX++;
			} else if (modifierX == 3 && modifierDX == 0) {
				modifierX--;
				modifierDX = 1;
			} else if (modifierX > -3 && modifierDX == 1) {
				modifierX--;
			} else if (modifierX == -3 && modifierDX == 1) {
				modifierX++;
				modifierDX = 0;
			}
			lastTick = tick;
		}

		int x = ((Main.resX / 2) - (Main.resX / 5)) + (modifierX * Main.imgMultiplier);
		int y = (Main.resY / 3) + (modifierY * Main.imgMultiplier);

		showBackground.display(0, 0);
		showTitle.display(x, y);
		setLayout(startButton, exitButton, fullScreen);
	}

	private void setLayout(Button startButton, Button stopButton, ImageView fullScreen) {
		startButton.setLayoutX((Main.resX / 2) - 24);
		startButton.setLayoutY(Main.resY / 2);
		stopButton.setLayoutX((Main.resX / 2) + 24);
		stopButton.setLayoutY(Main.resY / 2);
		fullScreen.setLayoutX(Main.resX - (2 * Main.imgMultiplier) - (7 * Main.imgMultiplier));
		fullScreen.setLayoutY(2 * Main.imgMultiplier);
		fullScreen.setImage(fullscreenButton.displayButton());
	}
}
