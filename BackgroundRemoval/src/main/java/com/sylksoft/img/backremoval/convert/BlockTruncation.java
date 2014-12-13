package com.sylksoft.img.backremoval.convert;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class BlockTruncation {

	public static void main(String[] args) throws IOException {
		BufferedImage bufImgs = ImageIO.read(new File("img/img.png"));

		ArrayList<Integer> RH = new ArrayList<Integer>();
		ArrayList<Integer> RL = new ArrayList<Integer>();
		ArrayList<Integer> GH = new ArrayList<Integer>();
		ArrayList<Integer> GL = new ArrayList<Integer>();
		ArrayList<Integer> BH = new ArrayList<Integer>();
		ArrayList<Integer> BL = new ArrayList<Integer>();
		/*
		 * ArrayList RL[]=new int[15]; int GH[]=new int[15]; int GL[]=new
		 * int[15]; int BH[]=new int[15]; int BL[]=new int[15];
		 */

		int red[] = new int[(bufImgs.getWidth() * bufImgs.getHeight())];
		int green[] = new int[(bufImgs.getWidth() * bufImgs.getHeight())];
		int blue[] = new int[(bufImgs.getWidth() * bufImgs.getHeight())];
		int c = 0;

		for (int r = 0; r < bufImgs.getWidth(); r++) {
			for (int s = 0; s < bufImgs.getHeight(); s++) {
				int color = bufImgs.getRGB(r, s);

				red[c] = (color >> 16) & 0xFF;
				green[c] = (color >> 8) & 0xFF;
				blue[c] = color & 0xFF;

				c++;

			}
		}
		double mean_pix[] = new double[3];
		mean_pix[0] = mean_no(red);
		mean_pix[1] = mean_no(green);
		mean_pix[2] = mean_no(blue);

		for (int k = 0; k < mean_pix.length; k++) {
			System.out.println(mean_pix[k]);
		}

		for (int p = 0; p < red.length; p++) {
			if (red[p] < mean_pix[0]) {
				/*
				 * for(int q=0;q<RL.length;q++) { RL[q]=red[p]; }
				 */
				RL.add(red[p]);
			} else {
				/*
				 * for(int q=0;q<RL.length;q++) { RH[q]=red[p]; }
				 */
				RH.add(red[p]);
			}
		}
		System.out.println("RH :");
		{
			for (int g : RH) {
				System.out.println(g);
			}
		}

		System.out.println("RL :");
		{
			for (int g : RL) {
				System.out.println(g);
			}
		}
	}

	static double mean_no(int[] col) {
		double sum = 0;
		for (int rgb : col) {

			sum += rgb;

		}

		return (sum / col.length);
	}

}
