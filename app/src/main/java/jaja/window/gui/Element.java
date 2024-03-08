package jaja.window.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import jaja.map.utils.Vector2;
import jaja.map.utils.Vector2d;
import jaja.map.utils.Vector2i;
import jaja.window.Renderer;

public class Element {
  private List<String> text = new ArrayList<>();
  private Path2D.Double border = new Path2D.Double();
  private Color border_color = new Color(0, 0, 0, 255);
  private Color background_color = new Color(0, 0, 0, (int)(0.8*255));
  private Color text_color = new Color(255, 255, 255, 255);
  private Align align = Align.RIGHT_CENTER;
  private boolean isVisible = true;

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  private Vector2d offset = new Vector2d(0, 0);

  public Vector2d getOffset() {
    return offset;
  }

  public void setOffset(Vector2d offset) {
    this.offset = offset;
  }

  public Color getBorder_color() {
    return border_color;
  }

  public void setBorder_color(Color border_color) {
    this.border_color = border_color;
  }

  public Color getBackground_color() {
    return background_color;
  }

  public void setBackground_color(Color background_color) {
    this.background_color = background_color;
  }

  public Color getText_color() {
    return text_color;
  }

  public void setText_color(Color text_color) {
    this.text_color = text_color;
  }

  public void setText(List<String> text) {
    this.text = text;
  }

  public void setBorder(Path2D.Double border) {
    this.border = border;
  }

  public void setAlign(Align align) {
    this.align = align;
  }

  public List<String> getText() {
    return text;
  }

  public Path2D.Double getBorder() {
    return border;
  }

  public Align getAlign() {
    return align;
  }

  public Element() {

  }

  public void update(Renderer r) {

  }

  public void onClick(Renderer r) { }

  public boolean isIn(Vector2i p1, Renderer r) {
    Rectangle2D r2 = this.border.getBounds2D();

    double w = r2.getWidth()+2;
    double h = r2.getHeight()+2;
  
    Vector2d xy1 = get___m(w, h, r);

    Vector2d A = new Vector2d(xy1.x, xy1.y);
    Vector2d B = new Vector2d(xy1.x + w, xy1.y);
    Vector2d C = new Vector2d(xy1.x + w, xy1.y + h);
    
    Vector2d AB = Vector2.vector(A, B);
    Vector2d AM = Vector2.vector(A, p1);
    Vector2d BC = Vector2.vector(B, C);
    Vector2d BM = Vector2.vector(B, p1);

    double dotABAM = Vector2.dot(AB, AM);
    double dotABAB = Vector2.dot(AB, AB);
    double dotBCBM = Vector2.dot(BC, BM);
    double dotBCBC = Vector2.dot(BC, BC);
    
    return 0 <= dotABAM && dotABAM <= dotABAB && 0 <= dotBCBM && dotBCBM <= dotBCBC;
  }

  private Vector2d get___m(double w, double h, Renderer r) {
    double x1 = 0;
    double y1 = 0;

    switch (this.align) {
      case RIGHT_CENTER: {
        x1 = r.getWidth() - w - 10 + offset.x;
        y1 = (r.getHeight() / 2) - (h / 2) + offset.y;
      } break;
      
      case BOTTOM: {
        x1 = (r.getWidth()/2) - (w/2) + offset.x;
        y1 = (r.getHeight()) - h + offset.y;
      } break;
    
      default: break;
    }

    // switch (this.align) {
    //   case RIGHT_CENTER: {
    //     x = (int) (r.getWidth() - image.getWidth() - 10+offset.x);
    //     y = (int) ((r.getHeight() / 2) - (image.getHeight() / 2)+offset.y);
    //   } break;
      
    //   case BOTTOM: {
    //     x = (int) ((r.getWidth()/2) - (w/2)+offset.x);
    //     y = (int) ((r.getHeight()) - (h)+offset.y);
    //   } break;
    
    //   default: break;
    // }

    return new Vector2d(x1, y1);
  }

  public static Color decodeColor(String nm) {
    Integer intval = Integer.decode(nm);
    int i = intval.intValue();

    if (nm.length() == 7) {
      return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    } else if (nm.length() == 9) {
      return new Color((i >> 32) & 0xFF, (i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }
    
    return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
}
  
  public void draw(Graphics2D g2, Renderer r) {
    Rectangle r2 = this.border.getBounds();

    BufferedImage image = new BufferedImage(
      (int)r2.getWidth()+2,
      (int)r2.getHeight()+2,
      BufferedImage.TYPE_INT_ARGB);

    Graphics2D g22 = (Graphics2D)image.getGraphics();
    g22.setColor(this.background_color);
    g22.fill(this.border);

    g22.setColor(this.border_color);
    g22.draw(this.border);

    g22.setColor(text_color);
    for (int i = 0; i < this.text.size(); i++) {
      g22.drawString(
        this.text.get(i), 
        (int)(r2.getX()+5), 
        (int)((r2.getY()+20)+(i*20)));
    }

    this.appendDraw(g22);
    
    g22.dispose();

    Vector2d xy1 = get___m(image.getWidth(), image.getHeight(), r);

    int x = (int)(double)xy1.x;
    int y = (int)(double)xy1.y;

    g2.drawImage((Image)image, x, y, r.imageObserver);
  }

  public void appendDraw(Graphics2D g2) {

  }

  public static enum Align {
    LEFT_CENTER("left-center"),
    LEFT_TOP("left-top"),
    LEFT_BOTTOM("left-bottom"),
    
    RIGHT_CENTER("right-center"),
    RIGHT_TOP("right-top"),
    RIGHT_BOTTOM("right-bottom"),

    TOP("top"),
    CENTER("center"),
    BOTTOM("bottom")

    ;

    String name;
    Align(String name) {
      this.name = name;
    }

    public static Align fromString(String name) {
      return Align.valueOf(name.replace('-', '_').toUpperCase());
    }

    public String toString() {
      return this.name;
    }
  }
}
