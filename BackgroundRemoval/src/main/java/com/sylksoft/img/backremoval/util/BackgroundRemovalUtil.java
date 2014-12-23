package com.sylksoft.img.backremoval.util;

public class BackgroundRemovalUtil {
	public static boolean range(int[] rgb, int[] _rgb, int Rrange, int Grange,int Brange) {
		boolean rFlag = false, gFlag = false, bFlag = false;
		int r = rgb[0], g = rgb[1], b = rgb[2], _r = _rgb[0], _g = _rgb[1], _b = _rgb[2];

		if (Math.abs((r - _r)) < Rrange) {
			rFlag = true;
		}
		if (Math.abs((g - _g)) < Grange) {
			gFlag = true;
		}
		if (Math.abs((b - _b)) < Brange) {
			bFlag = true;
		}

		return rFlag && gFlag&& bFlag;
	}

	public static boolean range(int[] rgb, int[] _rgb, int range) {
		return range(rgb,_rgb, range, range, range);
	}
}
