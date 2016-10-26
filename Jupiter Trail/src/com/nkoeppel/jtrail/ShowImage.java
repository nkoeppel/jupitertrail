package com.nkoeppel.jtrail;

import java.io.InputStream;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ShowImage{
	private Image image;
	private String path;
	private GraphicsContext canvas;
	
	public ShowImage(String path, GraphicsContext canvas) {
		this.path = path;
		this.canvas = canvas;
	}

	public void display(int x, int y) {
		InputStream imageStream = ShowImage.class.getClassLoader().getResourceAsStream(path);
		this.image = new Image(imageStream);
		Image imageResample = resample(this.image, Main.imgMultiplier);
		canvas.drawImage(imageResample, x, y);
	}
	public Image displayButton(){
		InputStream imageStream = ShowImage.class.getClassLoader().getResourceAsStream(path);
		this.image = new Image(imageStream);
		Image imageResample = resample(this.image, Main.imgMultiplier);
		return imageResample;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(W * S, H * S);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}

}
