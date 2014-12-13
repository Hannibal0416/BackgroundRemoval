package com.sylksoft.img.backremoval.convert;

import ij.process.ImageProcessor;

public class BackgroundDetect {
	ImageProcessor ip;
	ImageProcessor ipin;
	int blockWidth = 4;
	int blockHeight = 4;
	public BackgroundDetect(ImageProcessor ip,ImageProcessor ipin) {
		this.ip = ip;
		this.ipin = ipin;
	}
	
	public void detect() {
		//block split
		for(int i=0 ;i < blockWidth ; i ++) {
			for(int j=0 ;j < blockHeight ; j ++) {
				
				int tempBlock[][] = new int[ip.getWidth() / blockWidth][ip.getHeight() / blockHeight];
				int tempW = ip.getWidth() / blockWidth;
				int tempH = ip.getHeight() / blockHeight;
				for(int x=0 ;x < ip.getWidth() / blockWidth ; x ++) {
					for(int y=0 ;y < ip.getHeight() / blockHeight ; y ++) {
						int rgb[] = new int[3];
						ipin.getPixel(i*tempW  + x,j*tempH + y,rgb);
						if(rgb [0] > 250 && rgb [1] > 250 && rgb [0] > 250) {
							tempBlock[x][y] = -1;
						} else {
							tempBlock[x][y] = ip.get(i*tempW  + x,j*tempH + y);
							ip.set(i*tempW + x,j*tempH + y, 0);
						}
						
					}
				}
				System.out.println(tempBlock);
			}
		}
		
		
	}
	
}
