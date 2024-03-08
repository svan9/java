package jaja.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import jaja.map.utils.Utils;
import jaja.map.utils.Vector2i;

public class Noise {

  private BufferedImage noiseMap;
  private Graphics2D g2;
  private int maxID;

  private int w;
  private int h;

  public Noise(int width, int height, int rescale, int maxID) {
    this.noiseMap = new BufferedImage((int)(width/rescale), (int)(height/rescale), BufferedImage.TYPE_INT_RGB);
    this.g2 = this.noiseMap.createGraphics();

    g2.setColor(new Color(0, 10, 0));
    g2.fillRect(0, 0, noiseMap.getWidth(), noiseMap.getHeight());

    this.maxID = maxID;
    this.w = width;
    this.h = height;
  }

  public Noise add(int id, int count) {
    Vector2i minXY = new Vector2i(0, 0);
    Vector2i maxXY = new Vector2i(noiseMap.getWidth()-1, noiseMap.getHeight()-1);
    
    // double idp = this.maxID; // 255/*idp
    
    // int ahahaha = (int)(id); 

    g2.setColor(new Color(0, 0, id));

    for (int i = 0; i < count; i++) {
      Vector2i pos = Utils.randVec(minXY, maxXY);
      g2.fillRect(pos.x, pos.y, 1, 1);
    }

    return this;
  }

  public BufferedImage get() {
    // return blur(resize(this.noiseMap, this.w, this.h), 1, 1);
    return resize(this.noiseMap, this.w, this.h);
  }

  public static BufferedImage generateNoiseMap(int width, int height, double del, double percentage, int count) {
    BufferedImage img = new BufferedImage((int)(width/del), (int)(height/del), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();

    for(int x = 0; x < img.getWidth(); x++) {
      for(int y = 0; y < img.getHeight(); y++) {
        int rr = Utils.randRange(0, 255, percentage);
        g2.setColor(new Color(rr, rr, rr));
        g2.fillRect(x, y, 1, 1);
      }
    }

    return blur(resize(img, width, height), 1, 1);
  }

  public static BufferedImage generateNoiseMap(int width, int height, double del) {
    BufferedImage img = new BufferedImage((int)(width/del), (int)(height/del), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();  

    for(int x = 0; x < img.getWidth(); x++) {
      for(int y = 0; y < img.getHeight(); y++) {
        int rr = Utils.randRange(0, 255);
        g2.setColor(new Color(rr, rr, rr));
        g2.fillRect(x, y, 1, 1);
      }
    }

    return blur(resize(img, width, height), 1, 1);
  }

  public static BufferedImage generateNoiseMap(int width, int height, double del, int maxCount) {
    BufferedImage img = new BufferedImage((int)(width/del), (int)(height/del), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = img.createGraphics();  

    for(int x = 0; x < img.getWidth(); x++) {
      for(int y = 0; y < img.getHeight(); y++) {
        int rr = Utils.randRange(0, maxCount);
        g2.setColor(new Color(rr, rr, rr));
        g2.fillRect(x, y, 1, 1);
      }
    }

    return blur(resize(img, width, height), 1, 1);
  }
  
  public static BufferedImage blur(BufferedImage img, int range, int angle) {
      BufferedImage b = new BufferedImage(img.getWidth() * 2, img.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = b.createGraphics();
      
      for(int x = 0; x < img.getWidth(); x++) {
        for(int y = 0; y < img.getHeight(); y++) {
          //horizontal
              
          int red[] = new int[range * 2], green[] = new int[range * 2], blue[] = new int[range * 2];
          int pixels[] = new int[range * 2];
          
          for(int i = 0; i < pixels.length; i++) {
            pixels[i] = img.getRGB(Utils.clamp(x - Utils.clamp(range / 2, 0, range) + i, 0, img.getWidth() - 1), Utils.clamp(y - Utils.clamp(range / 2, 0, range) + (int)(i * Math.toRadians(angle)), 0, img.getHeight() - 1));
            
            red[i] = (pixels[i] >> 16) & 0xff;
            green[i] = (pixels[i] >> 8) & 0xff;
            blue[i] = (pixels[i]) & 0xff;
          }
              
          int red_t = 0, green_t = 0, blue_t = 0;
          
          for(int i = 0; i < pixels.length; i++) {
            red_t += red[i];
            green_t += green[i];
            blue_t += blue[i];
          }
              
          int r = red_t / (range * 2);
          int gr = green_t / (range * 2);
          int bl = blue_t / (range * 2);
            
          g.setColor(new Color(r, gr, bl));
          g.fillRect(x, y, 1, 1);   
        }
      }
      g.dispose();
      
      return b;
  }

  public static BufferedImage resize(Image img, int newW, int newH) {
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return dimg;
  }

}
