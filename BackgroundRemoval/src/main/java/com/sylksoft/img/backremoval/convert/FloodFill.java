package com.sylksoft.img.backremoval.convert;

import ij.process.ImageProcessor;

public class FloodFill {
	private int dx[] = { 1, -1, 0, 0 };
	private int dy[] = { 0, 0, 1, -1 };
	private ImageProcessor ip;

	public ImageProcessor getIp() {
		return ip;
	}

	public void setIp(ImageProcessor ip) {
		this.ip = ip;
	}

	// 判斷是否超出邊界
	private boolean on_picture(int x, int y) // 位於圖片上
	{
		return x >= 0 && x < ip.getWidth()-2 && y >= 0 && y < ip.getHeight()-2;
	}

	public void flood(int x, int y, int fill_color, int original_color) {
		
		
		
		
		// 不能超出邊界
		if (!on_picture(x, y))
			return;

		// 顏色一樣才flood
		if (ip.get(x, y) != original_color)
			return;

		ip.set(x, y, fill_color);

		for (int i = 0; i < 4; i++) {
			// 求出下一個預定要淹沒的位置
			int next_x = x + dx[i], next_y = y + dy[i];
			flood(next_x, next_y, fill_color, original_color);
		}
	}
	
	
	public void fill(int sx, int sy, int fill_color, int original_color) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		for (int x = 1; x < w - 2; x++) {
			for (int y = 1; y < h - 2; y++) {
				
				
			}
		}
	}
	
	
}
