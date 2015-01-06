package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.convert.*;

public class BackgroundRemovalImpl implements BackgroundRemoval {
	
	private BufferedImage inputFile = null;
	private Convert c = new Convert();

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
