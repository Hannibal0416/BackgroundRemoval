package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.convert.*;

public class BackgroundRemovalImpl implements BackgroundRemoval {
	
	private BufferedImage inputFile = null;
	private Convert c = new Convert();

	public static void main(String[] args) {

		try {
			File imgFile = new File("img/1.png");
			BufferedImage inputFile = ImageIO.read(imgFile);
			BackgroundRemoval br = new BackgroundRemovalImpl();
			br.setBufferedImage(inputFile);
			br.remove();
			inputFile = br.getBufferedImage();
			try {
				File outputFile = new File("img/Opercity.png");
				ImageIO.write(inputFile, "png", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setBufferedImage(BufferedImage image) {
		this.inputFile = image;

	}

	@Override
	public BufferedImage getBufferedImage() {
		return c.getBufferedImage();
	}

	@Override
	public void remove() {
		c.invertImage(inputFile);
	}

}
