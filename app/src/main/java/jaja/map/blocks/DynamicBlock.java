package jaja.map.blocks;

import jaja.map.Map;
import jaja.map.items.ItemStack;
import jaja.map.utils.Vector2i;

public class DynamicBlock {
  private String id;
  private Direction direction = Direction.UP;
  private ItemStack items = new ItemStack();

  public ItemStack getItems() {
    return items;
  }

  public DynamicBlock clone() {
    DynamicBlock newBlock = new DynamicBlock(new Properties().setDirection(this.direction).setId(this.id));
    return newBlock;
	}

  public void setItems(ItemStack items) {
    this.items = items;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public DynamicBlock(DynamicBlock.Properties props) {
    this.id = props.getId();
    this.direction = props.getDirection();
  }

  public void update(Map map, Vector2i pos, int depth) { }

  public static void moveItems(DynamicBlock nextBlock, DynamicBlock prevBlock) {
    if (nextBlock == null || prevBlock == null) return;
    ItemStack fromItemStack = prevBlock.getItems();

    nextBlock.setItems(new ItemStack(fromItemStack));

    fromItemStack.clear();;
  }

  public static class Properties {
    private String id;
    private Direction direction = Direction.UP;

    public String getId() {
      return id;
    }

    public DynamicBlock.Properties setId(String id) {
      this.id = id;
      return this;
    }

    public Direction getDirection() {
      return direction;
    }

    public DynamicBlock.Properties setDirection(Direction direction) {
      this.direction = direction;
      return this;
    }

    public Properties() {
      
    }

    public static DynamicBlock.Properties of() {
      return new DynamicBlock.Properties(); 
    }
  }

  public static enum Direction {
    UP, LEFT, RIGHT, DOWN
  }

}
