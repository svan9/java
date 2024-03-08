package jaja.map.utils;

public class LerpedVector extends Vector2<Lerped>{

  public LerpedVector() {
    super(new Lerped(0, 0), new Lerped(0, 0));
  }

  public double valX() {
    return super.x.getMin();
  }

  public double valY() {
    return super.y.getMin();
  }

  public void setXYMax(double max) {
    this.x.setMax(max);
    this.y.setMax(max);
  }

  public void setXYMin(double min) {
    this.x.setMin(min);
    this.y.setMin(min);
  }

  public void lerpXY(double deltaTime) {
    this.x.lerp(deltaTime);
    this.y.lerp(deltaTime);
  }
}
