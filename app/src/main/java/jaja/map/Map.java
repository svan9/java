package jaja.map;

import jaja.map.blocks.DynamicBlock;
import jaja.map.register.Register;
import jaja.map.utils.DeltaTimer;
import jaja.map.utils.Utils;
import jaja.map.utils.Vector2i;
import jaja.window.Assets;
import jaja.window.Renderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
  public List<Layer> layers = new ArrayList<>();
  public int width;
  public int height;
  public int depth;

  public Assets assets = new Assets();
  public Register register = new Register();
  
  public int localDepth = 0;

  public Player mainPlayer;
  public Renderer renderer;

  private String playerTexture;

  public Map(Map.Properties properties, Renderer renderer) {
    this.width = properties.width;
    this.height = properties.height;
    this.depth = properties.depth;
    this.playerTexture = properties.playerTexture;

    this.renderer = renderer;

    this.generateMap();
    this.generateAssets();
  }

  public void generateAssets() {
    this.generateDepthBlur();

    // BufferedImage mapNoise = Noise.generateNoiseMap(this.width, this.height, 1, 0.9);
    // this.assets.noiseMap = mapNoise;
  }

  public void setNoiseMap(BufferedImage mapNoise ) {
    this.assets.noiseMap = mapNoise;
  }

  public void generateDepthBlur() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int width  = (int)screenSize.getWidth();
    int height = (int)screenSize.getHeight();
    
    for (int i = 0; i < this.depth; i++) {
      BufferedImage image = new BufferedImage(
        width,
        height,
        BufferedImage.TYPE_4BYTE_ABGR);
        
      Graphics2D g22 = image.createGraphics();
      g22.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f * i));
      g22.setColor(Color.GRAY);
      g22.fillRect(0, 0, width, height);
      g22.dispose();

      this.assets.depthBlur.add(image);
    }
  }

  public void initedFillFloor(String[] items, int[] counts) {
    int maxID = java.lang.reflect.Array.getLength(items);
    Noise nmap = new Noise(width, height, 1, maxID);

    int filling = 0;
    int lastMax_ = counts[0];
    for (int i = 0; i < maxID; i++) {
      if (lastMax_ < counts[i]) {
        filling = i;
      }
    }

    for (int i = 0; i < maxID; i++) {
      nmap.add(this.register.getBlockId(items[i]), counts[i]);
    }

    this.assets.noiseMap = nmap.get();
    
    File outputfile = new File("image.png");
    try {
      ImageIO.write(this.assets.noiseMap, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    this.fillFloor(items, filling, this.assets.noiseMap);
  }

  public int percentageGet(double percent) {
    return (int) (this.width*this.height*percent);
  }

  public void start(Renderer renderer) {
    this.mainPlayer = new Player(this.playerTexture, renderer);
    DeltaTimer.threadUpdate(() -> tickUpdate(), 1000, 100);
    
    // this.mainPlayer.setXY(this.width/2, this.height/2);
  }

  public void update() {}

  public void tickUpdate() {
    this.updateLayers();
  }

  public void generateMap() {
    for (int z = 0; z < this.depth; z++) {
      layers.add(z, new Layer(this.width, this.height));
    }
  }

  public void toUpDepth() {
    if (this.localDepth >= this.depth - 1) return;
    this.localDepth++;
  }

  public void toDownDepth() {
    if (this.localDepth <= 0) return;
    this.localDepth--;
  }

  public Layer getLayer(int depth) {
    return this.layers.get(depth);
  } 

  public Layer getUsedLayer() {
    return this.layers.get(this.localDepth);
  } 
  
  public DynamicBlock getDynamicCell(int x, int y, int depth) {
    if (
      depth >= this.depth || depth < 0 ||
      x >= this.width || x < 0 ||
      y >= this.height || y < 0
    ) return null;

    return this.layers.get(depth).dynamicBlocks.get(x).get(y);
  }

  public String getStaticCell(int x, int y, int depth) {
    return this.layers.get(depth).staticBlocks.get(x).get(y);
  }

  public Object getDynamicOrStaticCell(int x, int y, int depth) {
    if (depth > this.layers.size() - 1) return null;
    Layer layer = this.layers.get(depth);
    if (layer.dynamicBlocks.get(x).get(y) != null) {
      return layer.dynamicBlocks.get(x).get(y);
    } else {
      return layer.staticBlocks.get(x).get(y);
    }
  }

  public Object getDynamicOrStaticCell(int x, int y) {
    if (
      this.localDepth > this.layers.size() - 1 || this.localDepth < 0
    ) return null;
    Layer layer = this.layers.get(this.localDepth);
    if (layer.dynamicBlocks.get(x).get(y) != null) {
      return layer.dynamicBlocks.get(x).get(y);
    } else {
      return layer.staticBlocks.get(x).get(y);
    }
  }

  public void updateUnderCell(int x, int y) {
    Object obj = this.getDynamicOrStaticCell(x, y);
    if (obj instanceof DynamicBlock dyn) {
      dyn.update(this, new Vector2i(x, y), this.localDepth);
    }
  }

  public void updateUnderCell(int x, int y, int depth) {
    Object obj = this.getDynamicOrStaticCell(x, y, depth);
    if (obj instanceof DynamicBlock dyn) {
      dyn.update(this, new Vector2i(x, y), depth);
    }
  }

  public void updateLayers() {
    for (int depth = 0; depth < this.depth; depth++){
      this.layers.get(depth).update(this, depth);
    }
  }

  public void setStaticCell(int x, int y, int depth, String id) {
    if (depth > this.layers.size() - 1) return;
    this.layers.get(depth).setStaticCell(x, y, id);
  }

  public void setDynamicCell(int x, int y, int depth, DynamicBlock dynb) {
    if (depth > this.layers.size() - 1) return;
    this.layers.get(depth).setDynamicCell(x, y, dynb);
  }

  public void setStaticCell(int x, int y, String id) {
    if (this.localDepth > this.layers.size() - 1) return;
    this.layers.get(this.localDepth).setStaticCell(x, y, id);
  }

  public void fillFloor(String blockID) {
    this.layers.get(0).fillStatic(blockID);
  }

  public void fillFloor(String[] blockIDs, int[] range) {
    this.layers.get(0).fillStatic(blockIDs[0]);

    int blockLen = Array.getLength(blockIDs);

    int full_size = this.width * this.height;

    for (int b = 0; b < blockLen; b++) {
      int count = (int) (full_size * (range[b] / 100.0));

      for (int i = 0; i < count; i++) {
        int x = Utils.randRange(0, this.width);
        int y = Utils.randRange(0, this.height);

        this.layers.get(0).setStaticCell(x, y, blockIDs[b]);
      }
    }
  }

  public void fillFloor(String[] blockIDs, int filling, BufferedImage noiseMap) {
    Layer layer = this.layers.get(0);
    int maxID = java.lang.reflect.Array.getLength(blockIDs);

    // double idp = maxID; //255/

    for (int x = 0; x < layer.width; x++) {
      for (int y = 0; y < layer.height; y++) {
        Color clr = new Color(noiseMap.getRGB(x, y));
        int a = clr.getGreen();
        int index = filling;
        if (a == 0) {
          index = clr.getBlue()-1;
        } //else continue;

        layer.setStaticCell(x, y, blockIDs[index < maxID? index: maxID-1]); // 
      }
    }
  }

  public static class Position<T> {
    public T x;
    public T y;
    public int depth;

    public Position(T x, T y, int depth) {
      this.x = x;
      this.y = y;
      this.depth = depth;
    }
  }

  public static Map.Properties of(int width, int height, int depth) {
    return new Map.Properties(width, height, depth);
  }

  public static class Properties {
    public int width;
    public int height;
    public int depth;
    public String playerTexture = "player";

    public Properties(int width, int height, int depth) {
      this.width = width;
      this.height = height;
      this.depth = depth;
    }

    public Map.Properties setPlayerTexture(String name) {
      this.playerTexture = name;
      return this;
    }
  }

  @Override
  public String toString() {
    return this.register.toString();
  }

  public void putUnderBlock(Vector2i pos) {
    if (this.mainPlayer.currentBlock == null) return;

    this.setDynamicCell(pos.x, pos.y, this.localDepth, this.mainPlayer.forceDirection());    
  }
  
  // ! TEST 

  public DynamicBlock getBlockToDirection(Vector2i pos, int depth, DynamicBlock.Direction direction) {
    DynamicBlock dyn = null;

    switch (direction) {
      case UP: {
        dyn = this.getUpBlock(pos, depth);
      } break;
      case DOWN: {
        dyn = this.getDownBlock(pos, depth);
      } break;
      case LEFT: {
        dyn = this.getLeftBlock(pos, depth);
      } break;
      case RIGHT: {
        dyn = this.getRightBlock(pos, depth);
      } break;
  
      default: break;
    }

    return dyn;
  }

  public DynamicBlock getLowerBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x, pos.y, depth-1);
  }

  public DynamicBlock getTopBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x, pos.y, depth+1);

  }
  public DynamicBlock getLeftBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x-1, pos.y, depth);
  }

  public DynamicBlock getRightBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x+1, pos.y, depth);
  }

  public DynamicBlock getUpBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x, pos.y-1, depth);
  }

  public DynamicBlock getDownBlock(Vector2i pos, int depth) {
    return this.getDynamicCell(pos.x, pos.y+1, depth);
  }
  
}
