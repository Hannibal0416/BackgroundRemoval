package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class BackgroundViewer extends Application {

	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		File f = new File("img/1.png");
		System.out.println(f.getAbsolutePath());
		Image image = new Image(f.toURI().toURL().toString());
//        // simple displays ImageView the image as is
		BackgroundRemoval br = new BackgroundRemovalImpl();
		BufferedImage bf = null;
		try {
			bf = ImageIO.read(new File("img/gg.png"));
			br.setBufferedImage(bf);
			br.remove();
			bf = br.getBufferedImage();
		} catch (IOException ex) {
			System.out.println("Image failed to load.");
		}

		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}
		
		
        ImageView iv1 = new ImageView();
        iv1.setImage(wr);
		StackPane root = new StackPane();
		root.getChildren().add(iv1);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}