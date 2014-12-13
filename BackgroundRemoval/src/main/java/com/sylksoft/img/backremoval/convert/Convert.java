package com.sylksoft.img.backremoval.convert;
import ij.ImagePlus;
import ij.plugin.filter.GaussianBlur;
import ij.process.FloodFiller;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.test.sobel;

class Convert
{
    public static void main(String[] args)
    {
        invertImage("img/girl.png");
    }

    public static void invertImage(String imageName) {
    	File imgFile =new File(imageName);
        BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            File outputFile = new File("img/negate-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ImagePlus imageProc = new ImagePlus(); 
        imageProc.setImage(inputFile);
        ImageProcessor ipin = new ShortProcessor(inputFile.getWidth(), inputFile.getHeight()); 
        ipin = imageProc.getProcessor(); 
        
        ipin.findEdges();
        
        //Filter_Plugin fp = new Filter_Plugin();
        //fp.run(ipin);
        inputFile = ipin.getBufferedImage();
        

        try {
            File outputFile = new File("img/sobel-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        GaussianBlur gb = new GaussianBlur();
        
        gb.blurGaussian(ipin, 3, 3, 0.04);
        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/1GaussianBlur-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Threshold threshold = new Threshold();
        threshold.setThreshold(15);
        threshold.run(ipin);
        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/1threshold-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        DilationErosion de = new DilationErosion();
//        de.setImageProcessor(ipin);
//        de.Opening(21);
//        inputFile = ipin.getBufferedImage();

        
        
        ipin.erode();
        ipin.dilate();
        
        inputFile = ipin.getBufferedImage();
        try {
            File outputFile = new File("img/DilationErosion-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FloodFiller ff = new FloodFiller(ipin);
//        for (int x = ipin.getWidth() /; x < ipin.getWidth() /2; x++) {
//			for (int y = ipin.getHeight() /4; y < ipin.getHeight() /2; y++) {
//				ff.fill8(x, y);
//			}
//        }
        //center
        ff.particleAnalyzerFill(ipin.getHeight() /4; y < ipin.getHeight() /2
        , 0, 1, ipin.duplicate(), bounds);
        for (int x = ipin.getWidth() /4; x < ipin.getWidth()/4 * 3; x++) {
			for (int y = ipin.getHeight() /4; y < ipin.getHeight()/4 * 3; y++) {
				if(ipin.getPixel(x, y) == ((255<< 16)+ (255 <<8) + 255)) {
					// ff.fill8(x,y);
					 break;
				}
			}
        }
       
        /*FloodFill ff = new FloodFill();
        ff.setIp(ipin);
        //ff.flood(ipin.getWidth() /4, ipin.getHeight() /4,  (255<< 16)+ (255 <<8) + 255 ,  (255<< 16)+ (255 <<8) + 255);
        ff.flood(ipin.getWidth() /8, ipin.getHeight() /8,  (255<< 16)+ (255 <<8) + 255 , ipin.get(ipin.getWidth() /8, ipin.getHeight() /8));
        */
        //ff.particleAnalyzerFill(ipin.getWidth() /2, ipin.getHeight() /2, 0.2, 0.4, ipin.duplicate(), r);
       
        
        
        
        inputFile = ipin.getBufferedImage();

        try {
            File outputFile = new File("img/FloodFiller-"+imgFile.getName());
            ImageIO.write(inputFile, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}