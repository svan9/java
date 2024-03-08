package jaja.map.utils;

public class Vector2i extends Vector2<Integer> {
  public Vector2i(Float x, Float y) {
    super(x.intValue(), y.intValue());
  }

  public Vector2i(Double x, Double y) {
    super(x.intValue(), y.intValue());
  }

  public Vector2i(int x, int y) {
    super(x, y);
  }

  public static Vector2i nullible() {
    return new Vector2i(0, 0);
  } 
  
}
