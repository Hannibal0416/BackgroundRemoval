package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.convert.*;

public class BackgroundRemovalImpl implements BackgroundRemoval{
	public static File imgFile = new File("img/80008.png");
	public static void main(String[] args) {
		
       
        try {
        	BufferedImage inputFile = ImageIO.read(imgFile);
        	BackgroundRemoval br = new BackgroundRemovalImpl();
        	br.setBufferedImage(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
	}

	private  BufferedImage inputFile = null;
	
	@Override
	public void setBufferedImage(BufferedImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		Convert c = new Convert();
		c.invertImage(inputFile);
		c.setImgFile(imgFile.getName());
	}

}
