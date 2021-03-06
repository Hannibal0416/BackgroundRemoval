package com.sylksoft.img.backremoval.convert;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.util.BackgroundRemovalUtil;

import ij.process.FloodFiller;
import ij.process.ImageProcessor;

public class BackgroundDetect {
	ImageProcessor ip;
	ImageProcessor ipin;
	int blockWidth = 8;
	int blockHeight = 8;
	
	int sensitive = 50;

	public BackgroundDetect(ImageProcessor ip, ImageProcessor ipin) {
		this.ip = ip;
		this.ipin = ipin;
	}
	
	public void setSensitive(int sensitive) {
		this.sensitive = sensitive;
	}
	
	private int backgroundR;
	private int backgroundG;
	private int backgroundB;
	
	public ImageProcessor getImageProcessor(){
		return this.ipin;
	}

	public void detect() {
		// block split
		ImageProcessor temp = ipin.duplicate();
		int countBlock = 0;
		int tempr = 0,tempg = 0,tempb = 0;
		List<Integer> rList = new ArrayList<Integer>();
		List<Integer> gList = new ArrayList<Integer>();
		List<Integer> bList = new ArrayList<Integer>();
		for (int i = 0; i < blockWidth; i++) {
			for (int j = 0; j < blockHeight; j++) {
				
//				if (countBlock == 0 || countBlock ==( blockWidth * (blockHeight-1) )) {

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
								tempBlock[x][y] = ip.get(i * tempW + x, j
										* tempH + y);
//								ip.set(i * tempW + x, j * tempH + y, 0);
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
						if(countBlock == 0) {
							tempr = r;
							tempg = g;
							tempb = b;
						}
						
						if(BackgroundRemovalUtil.range(new int[]{r,g,b}, new int[]{tempr,tempg,tempb}, sensitive)) {
							backgroundR += r;
							backgroundG += g;
							backgroundB += b;
							rList.add(r);
							gList.add(g);
							bList.add(b);
							countBlock++;
//							System.out.print("R:" + r + ",");
//							System.out.print("G:" + g + ",");
//							System.out.println("B:" + b);
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
//				}
				
			}
		}

		
		
//		try {
//			File outputFile = new File("img/BackgroundDetect.png");
//			ImageIO.write(temp.getBufferedImage(), "png", outputFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//平均
		backgroundR /=countBlock;
		backgroundG /=countBlock;
		backgroundB /=countBlock;
		//標準差
		Integer rMax = Collections.max(rList);
		Integer gMax = Collections.max(gList);
		Integer bMax = Collections.max(bList);
		Integer rMin = Collections.min(rList);
		Integer gMin = Collections.min(gList);
		Integer bMin = Collections.min(bList);
//		Integer rRange = Collections.max(Arrays.asList(new Integer[]{Math.abs(backgroundR - rMax) , Math.abs(backgroundR - rMin)}))  ;
//		Integer gRange = Collections.max(Arrays.asList(new Integer[]{Math.abs(backgroundG - gMax) , Math.abs(backgroundG - gMin)}))  ;
//		Integer bRange = Collections.max(Arrays.asList(new Integer[]{Math.abs(backgroundB - bMax) , Math.abs(backgroundB - bMin)}))  ;
		Integer rRange = Math.abs(rMin - rMax);
		Integer gRange = Math.abs(gMin - gMax);
		Integer bRange = Math.abs(bMin - bMax);
//		System.out.println(rRange + "," + gRange + ","+ bRange + ",");
//		System.out.print("R:" + backgroundR + ",");
//		System.out.print("G:" + backgroundG + ",");
//		System.out.println("B:" + backgroundB);
		int backgroundRGB[] = new int[]{backgroundR,backgroundG,backgroundB};

		for (int x = 0; x < ip.getWidth(); x++) {
			for (int y = 0; y < ip.getHeight(); y++) {
				int rgb[] = new int[3];
				ipin.getPixel(x, y,rgb);
				int _rgb[] = new int[]{0,0,0};
//				if(BackgroundRemovalUtil.range(rgb, _rgb, 5) ) {
					int rgb_ip[] = new int[3];
					ip.getPixel(x, y, rgb_ip);
					if(!BackgroundRemovalUtil.range(rgb_ip, backgroundRGB, rRange,gRange,bRange)) {
						ipin.set(x, y, Color.WHITE.getRGB());
					} else {
						ipin.set(x, y, Color.BLACK.getRGB());
					}
					
//				}
//				else {
//					int rgb_ip[] = new int[3];
//					ip.getPixel(x, y, rgb);
//					if(BackgroundRemovalUtil.range(rgb_ip, backgroundRGB, 20)) {
//						ipin.set(x, y, Color.BLACK.getRGB());
//					}
//				}
			}
		}
		
//		try {
//			File outputFile = new File("img/BackgroundDetect2.png");
//			ImageIO.write(ipin.getBufferedImage(), "png", outputFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//消除雜點
//		for (int i = 0; i < blockWidth; i++) {
//			for (int j = 0; j < blockHeight; j++) {
//				
//				
//				int tempW = ip.getWidth() / blockWidth;
//				int tempH = ip.getHeight() / blockHeight;
//				int tempBlock[][] = new int[tempW][tempH];
//				                            					
//				for (int x = 0; x < tempW; x++) {
//					for (int y = 0; y < tempH; y++) {
//						int rgb[] = new int[3];
//						ipin.getPixel(i * tempW + x, j * tempH + y, rgb);
//						if (rgb[0] > 250 && rgb[1] > 250 && rgb[0] > 250) {
//							tempBlock[x][y] = -1;
//						} else {
//							tempBlock[x][y] = 0;
//						}
//
//					}
//				}
//				int cell = 20;
//				int edgeW = tempW/cell;
//				int edgeH = tempH/cell;
//				//上
//				for (int x = 0; x < edgeW; x++) {
//					for (int y = 0; y < tempH; y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255);
//					}
//				}
//				
//				//下
//				
//				for (int x = edgeW * cell; x <  tempW; x++) {
//					for (int y = 0; y < tempH; y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255 << 8);
//					}
//				}
//				
//				//左
//				for (int x = 0; x < tempW; x++) {
//					for (int y = 0; y < edgeH; y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255 << 16);
//					}
//				}
//				//右
//				for (int x = 0; x < tempW; x++) {
//					for (int y = edgeH * cell; y < tempH; y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 0);
//					}
//				}
//				
//			}
//		}
//		for (int i = 0; i < blockWidth - 1; i++) {
//			for (int j = 0; j < blockHeight - 1; j++) {
//				
//				
//				int tempW = ip.getWidth() / blockWidth;
//				int tempH = ip.getHeight() / blockHeight;
//				int tempBlock[][] = new int[tempW][tempH];
//				                            					
//				for (int x = (blockWidth /2); x < tempW - (blockWidth /2); x++) {
//					for (int y = (blockHeight /2); y < tempH - (blockHeight /2); y++) {
//						int rgb[] = new int[3];
//						ipin.getPixel(i * tempW + x, j * tempH + y, rgb);
//						if (rgb[0] > 250 && rgb[1] > 250 && rgb[0] > 250) {
//							tempBlock[x][y] = -1;
//							 
//						} else {
//							tempBlock[x][y] = 0;
//						}
//
//					}
//				}
//				int cell = 20;
//				int edgeW = tempW/cell;
//				int edgeH = tempH/cell;
//				//上
//				for (int x = (tempW /2); x < edgeW + (tempW /2); x++) {
//					for (int y = (tempH /2); y < tempH + (tempH /2) ; y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255);
//					}
//				}
//				
//				//下
//				
//				for (int x = edgeW * cell + (tempW /2); x <  tempW + (tempW /2); x++) {
//					for (int y = (tempH /2); y < tempH + (tempH /2); y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255 << 8);
//					}
//				}
//				
//				//左
//				for (int x = (tempW /2); x < tempW + (tempW /2); x++) {
//					for (int y = (tempH /2); y < edgeH + (tempH /2); y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 255 << 16);
//					}
//				}
//				//右
//				for (int x = (tempW /2); x < tempW + (tempW /2); x++) {
//					for (int y = edgeH * cell + (tempH /2); y < tempH + (tempH /2); y++) {
//						ipin.set(i * tempW + x, j * tempH + y, 0);
//					}
//				}
//				
//			}
//		}
//		
//		try {
//			File outputFile = new File("img/BackgroundDetect_edge.png");
//			ImageIO.write(ipin.getBufferedImage(), "png", outputFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	

}
