package com.sylksoft.img.backremoval.convert;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Opercity {
	ImageProcessor ip;
	ImageProcessor ipin;
	BufferedImage img;
	public Opercity(ImageProcessor ip,ImageProcessor ipin) {
		this.ip = ip;
		this.ipin = ipin;
	}
	
	public ImageProcessor getImageProcessor(){
		return this.ipin;
	}
	
	public void doOpercity(){
		BufferedImage bi = ip.getBufferedImage();
		int w = bi.getWidth();
		int h = bi.getHeight();
		img = new BufferedImage(w, h ,
			    BufferedImage.TYPE_INT_ARGB);
		for(int x = 0 ; x < w ; x ++) {
			for (int y = 0 ; y < h ; y ++) {
				int rgb[] = new int[3];
				ipin.getPixel(x, y, rgb);
//				if( range(x,y ,rgb,10)) {
				if( rgb[0] > 250 &&  rgb[1] > 250 && rgb[2] > 250) {
					img.setRGB(x, y, bi.getRGB(x, y) );
				} else {
					img.setRGB(x, y, (1 << 24) );
				}
				
			}
		}
		
		
	}
	
	private boolean range(int x, int y ,int[] original_color,int range) {
		int rgb[] = new int[3];
		if(rgb[0] >= original_color[0] +range || rgb[0] <= original_color[0] - range ||
				rgb[1] >= original_color[1] +range || rgb[1] <= original_color[1]  -range ||
				rgb[2] >= original_color[2] +range || rgb[2] <= original_color[2] -range) {
			return false;
		}
		return true;
	}

	public BufferedImage getBufferedImage() {
		return this.img;
	}
}
