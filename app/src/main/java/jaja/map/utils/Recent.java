package jaja.map.utils;


public class Recent<T extends Object> {
  private Object[] queue;
  private int size = 0;

  public Recent(int size) {
    this.size = size;
    this.queue = new Object[size];
  }

  public void add(T element) {
    this.scrollLeft();
    this.pushBack(element);
  }

  public void scrollLeft() {
    for (int i = 0; i<this.size-1; i++) {
      this.queue[i] = this.queue[i+1];
    }
    this.queue[this.size-1] = null;
  }

  public void pushBack(T element) {
    this.queue[this.size-1] = element;
  }
  
  public T getLast() {
    return this.get(0);
  }

  @SuppressWarnings("unchecked")
  public T get(int index) {
    return (T)this.queue[this.size-index-1];
  }
}
