package com.sylksoft.img.backremoval.convert;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ij.process.ImageProcessor;

public class BackgroundDetect {
	ImageProcessor ip;
	ImageProcessor ipin;
	int blockWidth = 8;
	int blockHeight = 8;

	public BackgroundDetect(ImageProcessor ip, ImageProcessor ipin) {
		this.ip = ip;
		this.ipin = ipin;
	}
	
	private int backgroundR;
	private int backgroundG;
	private int backgroundB;

	public void detect() {
		// block split
		ImageProcessor temp = ipin.duplicate();
		int countBlock = 0;
		for (int i = 0; i < blockWidth; i++) {
			for (int j = 0; j < blockHeight; j++) {
				
				if (countBlock == 0 || countBlock ==( blockWidth * (blockHeight-1) )) {

					int tempBlock[][] = new int[ip.getWidth() / blockWidth][ip
							.getHeight() / blockHeight];
					int tempW = ip.getWidth() / blockWidth;
					int tempH = ip.getHeight() / blockHeight;
					for (int x = 0; x < ip.getWidth() / blockWidth; x++) {
						for (int y = 0; y < ip.getHeight() / blockHeight; y++) {
							int rgb[] = new int[3];
							ipin.getPixel(i * tempW + x, j * tempH + y, rgb);
							if (rgb[0] > 250 && rgb[1] > 250 && rgb[0] > 250) {
								tempBlock[x][y] = -1;
							} else {
								tempBlock[x][y] = ip.get(i * tempW + x, j
										* tempH + y);
								ip.set(i * tempW + x, j * tempH + y, 0);
							}

						}
					}
					int count = 0;
					int r = 0;
					int g = 0;
					int b = 0;
					for (int x = 0; x < ip.getWidth() / blockWidth; x++) {
						for (int y = 0; y < ip.getHeight() / blockHeight; y++) {
							if (tempBlock[x][y] != -1) {
								count++;
								r += (tempBlock[x][y] & 0xff0000) >> 16;
								g += (tempBlock[x][y] & 0xff00) >> 8;
								b += tempBlock[x][y] & 0xff;
							}
						}
					}
					if (count != 0) {
						r /= count;
						g /= count;
						b /= count;
						backgroundR += r;
						backgroundG += g;
						backgroundB += b;
						System.out.print("R:" + r + ",");
						System.out.print("G:" + g + ",");
						System.out.println("B:" + b);
						for (int x = 0; x < ip.getWidth() / blockWidth; x++) {
							for (int y = 0; y < ip.getHeight() / blockHeight; y++) {
								if (tempBlock[x][y] != -1) {
									temp.set(i * tempW + x, j * tempH + y,
											((r << 16) + (g << 8) + b));
								}

							}
						}
					}
				}
				countBlock++;
			}
		}

		
		
		try {
			File outputFile = new File("img/BackgroundDetect.png");
			ImageIO.write(temp.getBufferedImage(), "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		backgroundR /=2;
		backgroundG /=2;
		backgroundB /=2;
		
		//消除雜點
		for (int i = 0; i < blockWidth; i++) {
			for (int j = 0; j < blockHeight; j++) {
				
				
				int tempW = ip.getWidth() / blockWidth;
				int tempH = ip.getHeight() / blockHeight;
				int tempBlock[][] = new int[tempW][tempH];
				                            					
				for (int x = 0; x < tempW; x++) {
					for (int y = 0; y < tempH; y++) {
						int rgb[] = new int[3];
						ipin.getPixel(i * tempW + x, j * tempH + y, rgb);
						if (rgb[0] > 250 && rgb[1] > 250 && rgb[0] > 250) {
							tempBlock[x][y] = -1;
						} else {
							tempBlock[x][y] = 0;
						}

					}
				}
				int cell = 20;
				int edgeW = tempW/cell;
				int edgeH = tempH/cell;
				//上
				for (int x = 0; x < edgeW; x++) {
					for (int y = 0; y < tempH; y++) {
						ipin.set(i * tempW + x, j * tempH + y, 255);
						
					}
				}
				
				//下
				
				for (int x = edgeW * cell; x <  tempW; x++) {
					for (int y = 0; y < tempH; y++) {
						ipin.set(i * tempW + x, j * tempH + y, 255 << 8);
						
					}
				}
				
				//左
				for (int x = 0; x < tempW; x++) {
					for (int y = 0; y < edgeH; y++) {
						ipin.set(i * tempW + x, j * tempH + y, 255 << 16);
						
					}
				}
				//右
				for (int x = 0; x < tempW; x++) {
					for (int y = edgeH * cell; y < tempH; y++) {
						ipin.set(i * tempW + x, j * tempH + y, 0);
						
					}
				}
				
			}
		}
		
		try {
			File outputFile = new File("img/BackgroundDetect_edge.png");
			ImageIO.write(ipin.getBufferedImage(), "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
