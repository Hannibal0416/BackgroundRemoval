package com.sylksoft.img.backremoval.convert;

import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class Threshold {
	private int threshold;
	
	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		for (int x = 1; x < w - 2; x++) {
			for (int y = 1; y < h - 2; y++) {
				int[] rgb = new int[3];
				ip.getPixel(x, y, rgb);

				if (rgb[0] <threshold && rgb[1] <threshold && rgb[2] <threshold) {
					ip.set(x, y, ImageProcessor.BLACK);
					
				} else {
					ip.set(x, y,(255<<16) + (255<<8) + 255);
				}
			}
		}
	}
}
