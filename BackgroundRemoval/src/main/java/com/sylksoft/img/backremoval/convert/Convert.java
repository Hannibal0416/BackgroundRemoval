package com.sylksoft.img.backremoval.convert;
import ij.ImagePlus;
import ij.plugin.filter.GaussianBlur;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Convert
{

	private String imgFile;
	private BufferedImage bufferedImage;
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}
    public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public void invertImage(BufferedImage inputFile) {
    	
        
        
        ImagePlus imageProc = new ImagePlus(); 
        imageProc.setImage(inputFile);
        ImageProcessor ip = new ShortProcessor(inputFile.getWidth(), inputFile.getHeight()); 
        ip = imageProc.getProcessor();
        
        
        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                inputFile.setRGB(x, y, col.getRGB());
            }
        }

        try {
            File outputFile = new File("img/negate-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ImageProcessor ipin = ip.duplicate();
        
        ipin.findEdges();
        
        //Filter_Plugin fp = new Filter_Plugin();
        //fp.run(ipin);
        inputFile = ipin.getBufferedImage();
        

        try {
            File outputFile = new File("img/sobel-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        GaussianBlur gb = new GaussianBlur();
        
        gb.blurGaussian(ipin, 1, 1, 0.02);
        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/1GaussianBlur-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Threshold threshold = new Threshold();
        threshold.setThreshold(15);
        threshold.run(ipin);
        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/1threshold-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	   ipin.dilate();
    	   ipin.erode();

        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/DilationErosion-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
         
        inputFile = ipin.getBufferedImage();

        try {
            File outputFile = new File("img/FloodFiller-"+imgFile);
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        BackgroundDetect bd = new BackgroundDetect(ip.duplicate(),ipin.duplicate());
        bd.detect();
        bufferedImage = ip.getBufferedImage();
        try {
            File outputFile = new File("img/BackgroundDetect-"+imgFile);
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //find background color
        ipin = bd.getImageProcessor();
        ipin.erode();
        ipin.dilate();
        Opercity o = new Opercity(ip.duplicate(),ipin.duplicate());
        o.doOpercity();
    }
	
	
	

}