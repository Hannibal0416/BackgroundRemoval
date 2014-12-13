package com.test;

import ij.process.ImageProcessor;

public class DilationErosion {

	class Kernel {
		public Kernel(int x, int y, int value) {

		}

		int x;
		int y;
		int value;
	}

	Kernel ker[] = new Kernel[21];

	public DilationErosion() {
		init();
	}

	ImageProcessor ip;

	private void init() {
		ker[0] = new Kernel(-2, -1, 0);// (-2,-1)
		ker[1] = new Kernel(-2, 0, 0); // (-2, 0)
		ker[2] = new Kernel(-2, 1, 0); // (-2, 1)
		ker[3] = new Kernel(-1, -2, 0); // (-1,-2)
		ker[4] = new Kernel(-1, -1, 0); // (-1,-1)
		ker[5] = new Kernel(-1, 0, 0); // (-1, 0)
		ker[6] = new Kernel(-1, 1, 0); // (-1, 1)
		ker[7] = new Kernel(-1, -2, 0); // (-1, 2)
		ker[8] = new Kernel(0, -2, 0); // ( 0,-2)
		ker[9] = new Kernel(0, -1, 0);// ( 0,-1)
		ker[10] = new Kernel(0, 0, 0); // ( 0, 0)
		ker[11] = new Kernel(0, 1, 0); // ( 0, 1)
		ker[12] = new Kernel(0, 2, 0); // ( 0, 2)
		ker[13] = new Kernel(1, -2, 0); // ( 1,-2)
		ker[14] = new Kernel(1, -1, 0); // ( 1,-1)
		ker[15] = new Kernel(1, 0, 0); // ( 1, 0)
		ker[16] = new Kernel(1, 1, 0); // ( 1, 1)
		ker[17] = new Kernel(1, 2, 0);// ( 1, 2)
		ker[18] = new Kernel(2, -1, 0);// ( 2,-1)
		ker[19] = new Kernel(2, 0, 0); // ( 2, 0)
		ker[20] = new Kernel(2, 1, 0);// ( 2, 1)

	}

	public void setImageProcessor(ImageProcessor ip) {
		this.ip = ip;
	}

	private void grayDilation(int num) {
		int px, py, max, temp;

		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				max = 0;
				for (int k = 0; k < num; k++) {
					px = i - ker[k].x;
					py = j - ker[k].y;
					if ((px >= 0) && (px < w) && (py >= 0) && (py < h)) {
						temp = ip.getPixel(px, py) + ker[k].value;
						if (max < temp)
							max = temp;
					}
				}
				ip.set(i, j, max);
			}
		}
	}

	private void grayErosion(int num) {
		int px, py, min, temp;
		int w = ip.getWidth();
		int h = ip.getHeight();

		for (int i = 1; i < w - 2; i++) {
			for (int j = 1; j < h - 2; j++) {
				min = (255 << 16) + (255 << 8) + 255;
				for (int k = 0; k < 21; k++) {
					px = i + ker[k].x;
					py = j + ker[k].y;
					if ((px >= 0) && (px < w) && (py >= 0) && (py < h)) {
						temp = ip.getPixel(px, py) - ker[k].value;
						if (temp < min)
							min = temp;
					}
				}
				if (min < 0)
					min = 0;

				ip.set(i, j, min);

			}
		}
	}

	void Opening(int num) {

		grayErosion(num);
		grayDilation(num);
	}

	void gClosing(int num) {

		grayDilation(num);
		grayErosion(num);
	}
}
