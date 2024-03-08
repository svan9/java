package jaja.window.gui.guises;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import jaja.map.Noise;
import jaja.map.blocks.DynamicBlock;
import jaja.window.GlobalG;
import jaja.window.Renderer;
import jaja.window.gui.Element;

public class InventoryCell extends Element {

  private DynamicBlock inventoryBlock = null;

  private Image invbTexture = null;

  private int size;
  
  public DynamicBlock getInventoryBlock() {
    return inventoryBlock;
  }

  public void setInventoryBlock(DynamicBlock inventoryBlock) {
    this.inventoryBlock = inventoryBlock;
  }

  public InventoryCell() {
    // getText().add("DEPTH");
    // getText().add("0");

    this.getBorder().moveTo(0, 0);
    this.getBorder().lineTo(50, 0);
    this.getBorder().lineTo(50, 50);
    this.getBorder().lineTo(0, 50);
    this.getBorder().closePath();
    
    this.setAlign(Align.BOTTOM);

    this.size = 50;
  }

  public InventoryCell(int size) {
    // getText().add("DEPTH");
    // getText().add("0");

    this.getBorder().moveTo(0, 0);
    this.getBorder().lineTo(size, 0);
    this.getBorder().lineTo(size, size);
    this.getBorder().lineTo(0, size);
    this.getBorder().closePath();
    
    this.setAlign(Align.BOTTOM);

    this.size = size;
  }

  @Override
  public void update(Renderer r) {
    if (inventoryBlock == null || invbTexture != null || inventoryBlock.getId() == null) return; 
    Image texture = r.map.register.getTexture(inventoryBlock.getId());

    this.invbTexture = Noise.resize(texture, this.size-20, this.size-20);
  } 

  
  @Override
  public void appendDraw(Graphics2D g2) {
    if (invbTexture == null) return;

    g2.drawImage(this.invbTexture, 10, 10, GlobalG.imageObserver);
  }

  @Override
  public void onClick(Renderer r) {
    if (inventoryBlock == null) return;
    // this.setBackground_color(Color.white);
    r.map.mainPlayer.setCurrentBlock(inventoryBlock); 
  }
}
