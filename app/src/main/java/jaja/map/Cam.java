package jaja.map;

import jaja.map.utils.LerpedVector;
import jaja.map.utils.Vector2d;

public class Cam {
  public LerpedVector offset = new LerpedVector();

  public Cam() { }

  public void restart(Vector2d xy) {
    offset.x.setMax(xy.x);
    offset.y.setMax(xy.y);
  }

  public void update(double deltaTime) {
    this.offset.lerpXY(deltaTime*2);
  }
}
