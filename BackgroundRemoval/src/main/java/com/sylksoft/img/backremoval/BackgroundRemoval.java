package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;

public interface BackgroundRemoval {
	public void setBufferedImage(BufferedImage image);
	public void remove();
	public BufferedImage getBufferedImage();
}
