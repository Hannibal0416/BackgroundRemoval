package com.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.sylksoft.img.backremoval.BackgroundRemoval;
import com.sylksoft.img.backremoval.BackgroundRemovalImpl;

public class ImageTest {
	
	@Test
	public void testRemove(){
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

}
