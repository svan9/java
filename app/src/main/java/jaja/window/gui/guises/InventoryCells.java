package jaja.window.gui.guises;

import java.util.ArrayList;
import java.util.List;

import jaja.map.utils.Vector2d;
import jaja.window.Renderer;

public class InventoryCells {
  private int count = 0;

  public InventoryCells() { }
  
  public InventoryCells(int count) {
    this.count = count;
  }

  public void add() {
    count++;
  }

  public void generate(Renderer r) {
    int margin = 10;
    int cellSize = 50;

    for (int i = 0; i < this.count; i++) {
      InventoryCell invc = new InventoryCell(cellSize);

      int toleft = (i-(this.count/2))*(cellSize+margin);

      invc.setOffset(new Vector2d(-toleft, 0));

      r.elements.add(invc);
    }

  }
}
