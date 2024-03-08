package jaja.map;

import jaja.map.blocks.DynamicBlock;
import jaja.map.register.Resource;
import jaja.map.utils.Impuls;
import jaja.map.utils.Lerped;
import jaja.map.utils.Vector2;
import jaja.window.Renderer;

public class Player {
  public Map.Position<Lerped> lpos = 
    new Map.Position<Lerped>(new Lerped(0, 0), new Lerped(0, 0), 0);
  
  public Vector2<Lerped> offset = new Vector2<Lerped>(new Lerped(0, 0), new Lerped(0, 0));

  public Map.Position<Float> pos = new Map.Position<>(0.0f, 0.0f, 0);
  public Renderer renderer;
  public String textureID;
  public PlayerResource resource;
  public Double lastDirection;
  public Lerped direction;

  public DynamicBlock currentBlock = null;
  public DynamicBlock.Direction currentBlockDirection = DynamicBlock.Direction.UP;

  public DynamicBlock.Direction getCurrentBlockDirection() {
    return currentBlockDirection;
  }

  public void setCurrentBlockDirection(DynamicBlock.Direction currentBlockDirection) {
    this.currentBlockDirection = currentBlockDirection;
  }

  public DynamicBlock forceDirection() {
    this.currentBlock.setDirection(currentBlockDirection);
    
    return this.currentBlock.clone();
  }

  public void rotateCurrentBlockDirection() {
    switch (this.currentBlockDirection) {
      case UP: {
        this.currentBlockDirection = DynamicBlock.Direction.RIGHT;
      } break;
      case RIGHT: {
        this.currentBlockDirection = DynamicBlock.Direction.DOWN;
      } break;
      case DOWN: {
        this.currentBlockDirection = DynamicBlock.Direction.LEFT;
      } break;
      case LEFT: {
        this.currentBlockDirection = DynamicBlock.Direction.UP;
      } break;
    
      default: break;
    } 
  }


  public Vector2<Impuls> impuls = new Vector2<Impuls>(new Impuls(), new Impuls());

  public final double maxImpuls = 30.0;

  public void setXY(double x, double y) {
    this.pos.x = (float)x;  
    this.pos.y = (float)y;
  }

  public Player(String textureID, Renderer renderer) {
    this.resource = PlayerResource.of(renderer.map.register.getResource(textureID));
    this.renderer = renderer;
    this.direction = new Lerped(0.0, 1.0);
    this.textureID = textureID;
  }

  public void start(String textureID, Renderer renderer) {
    this.resource = PlayerResource.of(renderer.map.register.getResource(textureID));
    this.renderer = renderer;
    this.textureID = textureID;
  }

  public void toLeft() {
    lpos.x.setMin(this.pos.x);
    lpos.x.setMax(this.pos.x - this.resource.getProperties().getSpeed());
    this.direction.setMax(-90.0);
    this.impuls.x.forceImpuls(maxImpuls);
    onMove();
  }
  
  public void toRight() {
    lpos.x.setMin(this.pos.x);
    lpos.x.setMax(this.pos.x + this.resource.getProperties().getSpeed());
    this.direction.setMax(90.0);
    this.impuls.x.forceImpuls(maxImpuls);
    onMove();
  }
  
  public void toUp() {
    lpos.y.setMin(this.pos.y);
    lpos.y.setMax(this.pos.y + this.resource.getProperties().getSpeed());
    this.direction.setMax(0.0);
    this.impuls.y.forceImpuls(maxImpuls);
    onMove();
  }
  
  public void toDown() {
    lpos.y.setMin(this.pos.y);
    lpos.y.setMax(this.pos.y - this.resource.getProperties().getSpeed());
    this.direction.setMax(180.0);
    this.impuls.y.forceImpuls(maxImpuls);
    onMove();
  }

  public void onMove() {
    renderer.listener.onPlayerMove(renderer);
  }

  public void onStartMove() { }

  public void setCurrentBlock(DynamicBlock dynb) {
    this.currentBlock = dynb;
  }

  public void update(double deltaTime) {
    double offset_x = deltaTime*this.impuls.x.update(deltaTime);
    double offset_y = deltaTime*this.impuls.y.update(deltaTime);

    this.pos.x = (float)lpos.x.lerp(offset_x);
    this.pos.y = (float)lpos.y.lerp(offset_y);
  }

  public static class PlayerResource {
    private String texture;
    private PlayerResource.Properties properties;

    public PlayerResource(String texture, PlayerResource.Properties properties) {
      this.texture = texture;
      this.properties = properties;
    }

    public String getTexture() {
      return texture;
    }

    public PlayerResource.Properties getProperties() {
      return properties;
    }

    public static PlayerResource of(Resource res) {
      if (res == null) {
        throw new Error("Resources is null");
      }
      return new Player.PlayerResource(res.getDefaultTextures(), PlayerResource.Properties.of(res.getProperties()));
    }

    public static class Properties {
      private float speed = 0.0f;

      public Properties() { }

      public float getSpeed() {
        return speed;
      }

      public void setSpeed(float speed) {
        this.speed = speed;
      }

      public static PlayerResource.Properties of(java.util.HashMap<String, String> hashMap) {
        PlayerResource.Properties props_ = new PlayerResource.Properties();
        props_.setSpeed(Float.parseFloat(hashMap.get("speed")));
        return props_;
      }
      
    }
  }
}
