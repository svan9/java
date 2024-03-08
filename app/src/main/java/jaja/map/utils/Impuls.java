package jaja.map.utils;

public class Impuls {
  private Lerped impuls = new Lerped(0, 0);

  public Impuls() { }
  
  public void forceImpuls(double imp) {
    this.impuls.setMin(Lerped.lerp(this.impuls.getMin(), imp, 0.5));
    this.impuls.setMax(0);
  }
  
  public double get() {
    return this.impuls.getMin();
  }

  public double update(double deltaTime) {
    return this.impuls.lerp(deltaTime);
  }

}
