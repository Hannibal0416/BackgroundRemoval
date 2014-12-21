package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.convert.*;

public class BackgroundRemovalImpl implements BackgroundRemoval{
	public static File imgFile = new File("img/gg.png");
	private  BufferedImage inputFile = null;
	public static void main(String[] args) {
       
        try {
        	BufferedImage inputFile = ImageIO.read(imgFile);
        	BackgroundRemoval br = new BackgroundRemovalImpl();
        	br.setBufferedImage(inputFile);
        	br.remove();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
	}

	
	
	@Override
	public void setBufferedImage(BufferedImage image) {
		this.inputFile = image;
		
	}

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		Convert c = new Convert();
		c.setImgFile("girl.png");
		c.invertImage(inputFile);
		
	}

}
