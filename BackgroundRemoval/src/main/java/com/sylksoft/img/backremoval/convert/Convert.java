package com.sylksoft.img.backremoval.convert;

import ij.ImagePlus;
import ij.plugin.filter.GaussianBlur;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Convert {

	private BufferedImage bufferedImage;

	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}

	public void invertImage(BufferedImage inputFile) {

		ImagePlus imageProc = new ImagePlus();
		imageProc.setImage(inputFile);
		ImageProcessor ip = new ShortProcessor(inputFile.getWidth(),
				inputFile.getHeight());
		ip = imageProc.getProcessor();

		for (int x = 0; x < inputFile.getWidth(); x++) {
			for (int y = 0; y < inputFile.getHeight(); y++) {
				int rgba = inputFile.getRGB(x, y);
				Color col = new Color(rgba, true);
				col = new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue());
				inputFile.setRGB(x, y, col.getRGB());
			}
		}

		ImageProcessor ipin = ip.duplicate();

		ipin.findEdges();

		// Filter_Plugin fp = new Filter_Plugin();
		// fp.run(ipin);

		GaussianBlur gb = new GaussianBlur();

		gb.blurGaussian(ipin, 1, 1, 0.02);
		Threshold threshold = new Threshold();
		threshold.setThreshold(15);
		threshold.run(ipin);

		ipin.dilate();
		ipin.erode();


		BackgroundDetect bd = new BackgroundDetect(ip, ipin);
		bd.detect();

		// find background color
		ipin = bd.getImageProcessor();
		ipin.erode();
		ipin.dilate();
		Opercity o = new Opercity(ip, ipin);
		o.doOpercity();
		bufferedImage = o.getBufferedImage();
	}

}