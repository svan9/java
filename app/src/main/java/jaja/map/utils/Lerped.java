package jaja.map.utils;

public class Lerped {
  private double min;
  private double max;

  public Lerped(double min, double max) {
    this.min = min;
    this.max = max;
  }
  public double getMin() {
    return min;
  }
  public void setMin(double min) {
    this.min = min;
  }
  public double getMax() {
    return max;
  }
  public void setMax(double max) {
    this.max = max;
  }

  public double lerp(double t) {
    return (this.min = this.min + (this.max - this.min) * t);
  }
  public static double lerp(double min, double max, double t) {
    return (min = min + (max - min) * t);
  }

  public double percentFromMax(int percent) {
    return this.max * (percent);
  } 
}
