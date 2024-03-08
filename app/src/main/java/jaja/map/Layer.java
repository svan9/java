package jaja.map;

import java.util.List;
import java.util.ArrayList;

import jaja.map.blocks.DynamicBlock;
import jaja.map.utils.Vector2i;

public class Layer {
  public List<List<String>> staticBlocks = new ArrayList<>();
  public List<List<DynamicBlock>> dynamicBlocks = new ArrayList<>();

  public int width = 0;
  public int height = 0;

  public Layer(int width, int height) {
    this.width = width;
    this.height = height;
    this.generateMap();
  }

  public void generateMap() {
    for (int x = 0; x < this.width; x++) {
      List<String> stb = new ArrayList<>();
      List<DynamicBlock> dcb = new ArrayList<>();
      for (int y = 0; y < this.height; y++) {
        stb.add(null);
        dcb.add(null);
      }
      this.staticBlocks.add(stb);
      this.dynamicBlocks.add(dcb);
    }
  }

  public void setStaticCell(int x, int y, String id) {
    if (
      x >= this.width  || x < 0 ||
      y >= this.height || y < 0
     ) return;
    this.staticBlocks.get(x).set(y, id);
  }
  
  public void setDynamicCell(int x, int y, DynamicBlock dynb) {
    if (
      x >= this.width  || x < 0 ||
      y >= this.height || y < 0
     ) return;
     
    this.dynamicBlocks.get(x).set(y, dynb);
  }

  public DynamicBlock getDynamicCell(int x, int y) {
    if (
      x >= this.width  || x < 0 ||
      y >= this.height || y < 0
     ) return null;

    return this.dynamicBlocks.get(x).get(y);
  }

  public void fillStatic(String id) {
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        this.setStaticCell(x, y, id);
      }
    }
  }

  public Object getDynamicOrStaticCell(int x, int y) {
    if (this.dynamicBlocks.get(x).get(y) != null) {
      return this.dynamicBlocks.get(x).get(y);
    } else {
      return this.staticBlocks.get(x).get(y);
    }
  }

  public void update(Map map, int depth) {
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        Object cell = getDynamicOrStaticCell(x, y);
        if (cell instanceof DynamicBlock dyn) {
          dyn.update(map, new Vector2i(x, y), depth);
        }
      }
    }
  }
}
