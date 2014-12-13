package com.test;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.management.Query;

public class Main {

    public Main() {
    }

    public static boolean isBlack(BufferedImage image, int posX, int posY) {

        int color = image.getRGB(posX, posY);

        int brightness = (color & 0xFF) + ((color >> 2) & 0xFF)
        + ((color >> 4) & 0xFF);
        brightness /= 3;

        return brightness < 128;
    }

    public static void main(String[] args) {
        

        String filename = "img/girl.png";
        // String filename =
        // "C:\\Users\\Natthawut\\Desktop\\blob.jpg";
        try {
            BufferedImage bimg = ImageIO.read(new File(filename));
            ImagePlus imageProc = new ImagePlus(); 
            imageProc.setImage(bimg);
            ImageProcessor ip = new ShortProcessor(bimg.getWidth(), bimg.getHeight()); 
            ip = imageProc.getProcessor();

            boolean[][] painted = new boolean[bimg.getHeight()][bimg.getWidth()];

            for (int i = 0; i < bimg.getHeight(); i++) {
                for (int j = 0; j < bimg.getWidth(); j++) {

                    if (isBlack(bimg, j, i) && !painted[i][j]) {

                        Queue<Point> queue = new LinkedList<Point>();
                        queue.add(new Point(j, i));

                        int pixelCount = 0;
                        while (!queue.isEmpty()) {
                            Point p = queue.remove();

                            if ((p.x >= 0)
                                    && (p.x < bimg.getWidth() && (p.y >= 0) && (p.y < bimg
                                            .getHeight()))) {
                                if (!painted[p.y][p.x]
                                                  && isBlack(bimg, p.x, p.y)) {
                                    painted[p.y][p.x] = true;
                                    ip.set(p.x, p.y,  (255<< 16)+ (255 <<8) + 255);
                                    
                                    pixelCount++;
                                    
                                    queue.add(new Point(p.x + 1, p.y));
                                    queue.add(new Point(p.x - 1, p.y));
                                    queue.add(new Point(p.x, p.y + 1));
                                    queue.add(new Point(p.x, p.y - 1));
                                }
                            }
                        }
                        System.out.println("Blob detected : " + pixelCount
                                + " pixels");
                    }

                }
            }
            
            
        try {
            File outputFile = new File("img/test.png");
            ImageIO.write(ip.getBufferedImage(), "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}