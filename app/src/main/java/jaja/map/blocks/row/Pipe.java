package jaja.map.blocks.row;

import jaja.map.Layer;
import jaja.map.Map;
import jaja.map.blocks.DynamicBlock;
import jaja.map.items.ItemStack;
import jaja.map.utils.Vector2i;

public class Pipe extends DynamicBlock {
  public Pipe(DynamicBlock.Properties props) {
    super(props);

    switch (this.getDirection()) {
      case UP: {
        this.setId("pipe:up");
      } break;
      case RIGHT: {
        this.setId("pipe:right");
      } break;
      case LEFT: {
        this.setId("pipe:left");
      } break;
      case DOWN: {
        this.setId("pipe:down");
      } break;
    
      default: {
        this.setId("pipe:up");
      } break;
    }
  }

  @Override
  public void setDirection(Direction direction) {
    super.setDirection(direction);

    switch (this.getDirection()) {
      case UP: {
        this.setId("pipe:up");
      } break;
      case RIGHT: {
        this.setId("pipe:right");
      } break;
      case LEFT: {
        this.setId("pipe:left");
      } break;
      case DOWN: {
        this.setId("pipe:down");
      } break;
    
      default: {
        this.setId("pipe:up");
      } break;
    }
  }


  @Override
  public void update(Map map, Vector2i pos, int depth) {
    DynamicBlock nextBlock = map.getBlockToDirection(pos, depth, this.getDirection());    
    DynamicBlock.moveItems(nextBlock, this);
    
  }
  
}
