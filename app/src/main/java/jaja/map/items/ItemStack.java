package jaja.map.items;

public class ItemStack {
  private Item item;
  private int size;
  
  public ItemStack() { }

  public ItemStack(Item item, int size) {
    this.setItem(item);
    this.setSize(size);
  }

  public ItemStack(ItemStack copyble) {
    this.setItem(copyble.getItem());
    this.setSize(copyble.getSize());
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void clear() {
    this.size = 0;
  }

  public boolean isEmpty() {
    return this.size <= 0;
  }

}
