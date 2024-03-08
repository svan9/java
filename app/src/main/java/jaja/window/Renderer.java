package jaja.window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import jaja.map.Cam;
import jaja.map.Map;
import jaja.map.Noise;
import jaja.map.Player;
import jaja.map.blocks.DynamicBlock;
import jaja.map.blocks.row.Pipe;
import jaja.map.utils.Vector2;
import jaja.map.utils.Vector2d;
import jaja.map.utils.Vector2i;
import jaja.window.gui.Element;
import jaja.window.gui.guises.DepthElement;
import jaja.window.gui.guises.InventoryCell;
import jaja.window.gui.guises.InventoryCells;
import jaja.window.scene.SceneRow;

public class Renderer extends Container implements ActionListener {
  public Runnable onRender;
  public Runnable onUpdate;

  public Vector2<Double> camOffset = new Vector2<Double>(0.0, 0.0);
  public double deltaTime = 1.0;

  public long lastTime = System.currentTimeMillis();
  public Frame parrentframe;

  public final ImageObserver imageObserver = new ImageObserver() {
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
      return false;
    }
  };

  public List<Element> elements = new ArrayList<>(); 
  public int cellSize = 64;
  public Map map;
  public Listener listener;
  public Cam cam = new Cam();

  public SceneRow sceneRow;

  public void addSceneRow(SceneRow sr) {
    this.sceneRow = sr;
  }
  
  public void gotoScene(String name) {
    if (this.sceneRow == null) return;
    this.sceneRow.forceScene(this, name);
  }

  public Renderer() {
    elements.add(new DepthElement());
    new InventoryCells(5).generate(this);
    if (this.elements.get(2) instanceof InventoryCell ivc) {
      ivc.setInventoryBlock(new Pipe(new DynamicBlock.Properties().setId("empty")));
    }
  }

  public int forceGUI(Element e) {
    elements.add(e);
    return elements.size()-2; // index
  }

  public void removeGUI(int index) {
    elements.remove(index);
  }

  public void init() {
    // this.generateAssets();
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public void generateDepthBlur(Image img) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int width  = (int)(screenSize.getWidth()*1.3);
    int height = (int)(screenSize.getHeight()*1.3);

    Image nm = Noise.blur(Noise.resize(img, width*2, height*2), 1, 5) ;
    
    for (int i = 0; i < this.map.depth; i++) {
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D g22 = image.createGraphics();

      g22.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
      
      for (int j = 0; j < i; j++) {
        g22.drawImage(nm, 0, 0, imageObserver);
      }
      
      g22.setColor(new Color(150, 150, 150, (int)(255*0.3*i)));
      g22.fillRect(0, 0, width, height);
      
      g22.dispose();

      this.map.assets.depthBlur.add(image);
    }
    
  }
  
  public void start() {
    Timer timer = new Timer(20, this);
    this.map.start(this);
    timer.start();
  }

  @Override
  public void paint(Graphics g) {
    // g.drawImage(this.map.assets.noiseMap, 0, 0, imageObserver);
    if (this.map == null || this.map.mainPlayer == null) return;
    this.update((Graphics2D)g);
    this.render((Graphics2D)g);
  }

  public void update(Graphics2D g2) {
    // * elements update
    for (Element element: this.elements) {
      if (!element.isVisible()) continue;
      element.update(this);
    }

    // * events
    parrentframe.pressed();
    
    // * calc delta time
    long currentTime = System.currentTimeMillis();
    deltaTime = (currentTime - lastTime) / 1000.0;
    this.onUpdate.run();
    lastTime = currentTime;
    
    // * children update
    map.mainPlayer.update(deltaTime);
    map.update();
    cam.update(deltaTime);
  }

  public void restartUpdate(Vector2d xy) {
    cam.restart(xy);
  }
  
  public void render(Graphics2D g2) {
    if (this.map.localDepth != 0) {
      this.putGround(g2);
    }
    this.putBlocks(g2);
    this.putPlayer(g2);
    this.putGUI(g2);
    this.onRender.run();
  }

  private void putGUI(Graphics2D g2) {
    for (Element element: this.elements) {
      element.draw(g2, this);
    }
  }

  public void putBlocks(Graphics2D g2) {
    int half_w = map.width/2;
    int half_h = map.height/2;

    for (int x = 0; x < map.width; x++) {
      for (int y = 0; y < map.height; y++) {
        Object cell = map.getDynamicOrStaticCell(x, y);
        if (cell == null) continue;

        Image texture = null;
        if (cell instanceof DynamicBlock dynBlock) {
          texture = this.map.register.getTexture(dynBlock.getId());
        } else if (cell instanceof String id) {
          texture = this.map.register.getTexture(id);
        }
        if (texture == null) continue;

        correctDrawImage(g2, texture, x-half_w, y-half_h, this.cellSize, this.cellSize);
      }
    }
  }

  public void putGround(Graphics2D g2) {
    int half_w = map.width/2;
    int half_h = map.height/2;

    for (int x = 0; x < map.width; x++) {
      for (int y = 0; y < map.height; y++) {
        Object cell = map.getDynamicOrStaticCell(x, y, 0);
        if (cell == null) continue;

        Image texture = null;
        if (cell instanceof DynamicBlock dynBlock) {
          texture = this.map.register.getTexture(dynBlock.getId());
        } else if (cell instanceof String id) {
          texture = this.map.register.getTexture(id);
        }
        if (texture == null) continue;

        correctDrawImage(g2, texture, x-half_w, y-half_h, this.cellSize, this.cellSize);
      }
    }
    
    if (this.map.assets.depthBlur.size() <= this.map.localDepth) {
      this.map.localDepth = this.map.assets.depthBlur.size()-1;
    }
    if (this.map.localDepth < 0) {
      this.map.localDepth = 0;
    }

    g2.drawImage(this.map.assets.depthBlur.get(this.map.localDepth), 0, 0, imageObserver);
  }

  public void putPlayer(Graphics2D g2) {
    Player player = map.mainPlayer;
    Image texture = this.map.register.getTexture(player.textureID);

    Double angle = player.direction.lerp(deltaTime * 4);

    correctDrawImage(g2, 
      rotateImage(texture, angle),
      player.pos.x, player.pos.y,
      (int)(this.cellSize * 2),
      (int)(this.cellSize * 2)); 
  }

  public void correctDrawImage(Graphics2D g2, Image img, double x, double y, int w, int h) {
    int half_w = map.width  / 2;
    int half_h = map.height / 2;
    
    g2.drawImage(img,
    (int)(((x-this.cam.offset.valX())*this.cellSize)+half_w+(this.getWidth()/2.5)),
    (int)(half_h-((y-this.cam.offset.valY())*this.cellSize)+(this.getHeight()/3)),
    w, h,
    imageObserver);
  }
  
  public Vector2i getUnderCell(Vector2i p1) {
    Vector2d p2 = new Vector2d(p1.x, p1.y);

    int half_w = map.width/2;
    int half_h = map.height/2;
    
    //# move 
    p2.x = p2.x-this.getWidth()/2.5;
    p2.y = p2.y-this.getHeight()/3.0;

    //# convert coords
    p2.x = p2.x+half_w;
    p2.y = half_h-p2.y;

    //# get coord under
    p2.x = p2.x/this.cellSize;
    p2.y = p2.y/this.cellSize;

    //# coord under map
    p2.x = p2.x+this.cam.offset.valX();
    p2.y = p2.y+this.cam.offset.valY();
    
    //# convert coord to index like values
    p2.x = p2.x+half_w;
    p2.y = p2.y+half_h+2;

    return p2.toInt((a) -> (double)Math.round(a)-2.0);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  public static Image rotateImage(Image img, Double angle) {
    BufferedImage image = toBufferedImage(img);

    double locationX = (int)(image.getWidth() / 2);
    double locationY = (int)(image.getHeight() / 2);

    AffineTransform tx = new AffineTransform();

    tx.translate(image.getWidth() / 4, image.getHeight() / 4);
    tx.rotate(Math.toRadians(angle), locationX, locationY);

    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);    

    BufferedImage output = new BufferedImage(
      (int)(image.getWidth() * 2),
      (int)(image.getHeight() * 2),
      image.getType()); 

    op.filter(image, output);
    return output;
  }
  
  public static Image[] cutImageRow(Image img) {
    BufferedImage image = toBufferedImage(img);
    int min = 0;
    int max = 0;
    int count = 0;

    int width = 0;
    int height = 0;

    width = image.getWidth();
    height = image.getHeight();
    
    max = Math.max(width, height);
    min = Math.min(width, height);

    if (min == max) {
      return new Image[] {img};
    }
    
    count = (int)max/min;

    Image[] images = new Image[count];

    for (int i = 0; i < count; i++) {
      images[i] = (Image)image.getSubimage(
          i * min,  //((width  / min) - 1) * 
          // ((height / min) - 1) * i * min, 
          0,
          min, min); 
    }

    return images;
  }

  public static BufferedImage toBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();
    return bimage;
  }

}
