package com.sylksoft.img.backremoval;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sylksoft.img.backremoval.convert.*;

public class BackgroundRemovalImpl implements BackgroundRemoval{
	public static File imgFile = new File("img/22.png");
	private  BufferedImage inputFile = null;
	private Convert c = new Convert();
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
		return this.inputFile;
	}

	@Override
	public void remove() {
		
		c.setImgFile("girl.png");
		c.invertImage(inputFile);
		
	}

}
