package com.test;

import java.util.LinkedList;
import java.util.Queue;

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
		return x >= 0 && x < ip.getWidth() - 2 && y >= 0
				&& y < ip.getHeight() - 2;
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

	public void fill(int x, int y, int[] fill_color, int[] original_color) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		Queue<Point> queue = new LinkedList<Point>();
		if (range(x,y,original_color )) {
			return;
		}
		queue.add(new Point(x, y));

		while (!queue.isEmpty()) {
			Point p = queue.remove();
			if (range(x,y,original_color ) && !on_picture(x, y)) {
				ip.set(p.x, p.y, fill_color[0] << 16 + fill_color[1] << 8 + fill_color[2]);
				queue.add(new Point(p.x - 1, p.y));
				queue.add(new Point(p.x + 1, p.y));
				queue.add(new Point(p.x, p.y - 1));
				queue.add(new Point(p.x, p.y + 1));
			}
		}

	}

	private boolean range(int x, int y ,int[] original_color) {
		int rgb[] = new int[3];
		if(rgb[0] >= original_color[0] +5 || rgb[0] <= original_color[0] - 5 ||
				rgb[1] >= original_color[1] +5 || rgb[1] <= original_color[1]  -5 ||
				rgb[2] >= original_color[2] +5 || rgb[2] <= original_color[2] -5) {
			return false;
		}
		return true;
	}
	class Point {
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int x;
		int y;
	}

}
