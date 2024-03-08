package jaja.map.utils;

public class Vector2<T> {
  public T x;
  public T y;

  public Vector2(T x, T y) {
    this.x = x;
    this.y = y;
  }
  public T getX() {
    return x;
  }
  public void setX(T x) {
    this.x = x;
  }
  public T getY() {
    return y;
  }
  public void setY(T y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "(x:"+this.x+"; "+"y:"+this.y+")";
  }

  public static double dot(Vector2i u, Vector2i v) {
    return u.x * v.x + u.y * v.y;
  }

  public static double dot(Vector2d u, Vector2d v) {
    return u.x * v.x + u.y * v.y;
  }

  public static Vector2d vector(Vector2d p1, Vector2d p2) {
    return new Vector2d(p2.x - p1.x, p2.y - p1.y);
  }

  public static Vector2d vector(Vector2i p1, Vector2d p2) {
    return new Vector2d(p2.x - p1.x, p2.y - p1.y);
  }
  
  public static Vector2d vector(Vector2d p1, Vector2i p2) {
    return new Vector2d(p2.x - p1.x, p2.y - p1.y);
  }

  public static Vector2d vector(Vector2i p1, Vector2i p2) {
    return new Vector2d(p2.x - p1.x, p2.y - p1.y);
  }
}
