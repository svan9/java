package jaja.map.utils;

public class Saved<T> {
  private T last = null;
  private T current = null;

  public T getLast() {
    return last;
  }

  public T getCurrent() {
    return current;
  }

  public Saved() { }

  public Saved(T newValue) {
    this.current = newValue;
    this.last = newValue;
  }

  public T SetAndGet(T newValue) {
    T temp = this.current;
    this.last = this.current;
    this.current = newValue;
    return temp;
  }

}
