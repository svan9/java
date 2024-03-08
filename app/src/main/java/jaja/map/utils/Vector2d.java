package jaja.map.utils;

public class Vector2d extends Vector2<Double> {

  public Vector2d(Double x, Double y) {
    super(x, y);
  }

  public Vector2d(Float x, Float y) {
    super(x.doubleValue(), y.doubleValue());
  }

  public Vector2d(Integer x, Integer y) {
    super(x.doubleValue(), y.doubleValue());
  }

  public Vector2i toInt() {
    return new Vector2i(this.x, this.y);
  }

  public Vector2i toInt(Executable<Double, Double> e) {
    return new Vector2i(e.run(this.x), e.run(this.y));
  }
  
}
