package jaja.window;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class GlobalG {
  public static final ImageObserver imageObserver = new ImageObserver() {
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
      return false;
    }
  };
}
