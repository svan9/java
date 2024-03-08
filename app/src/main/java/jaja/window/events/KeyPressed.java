package jaja.window.events;

import java.awt.event.KeyEvent;

public class KeyPressed {
  public boolean W = false;
  public boolean S = false;
  public boolean A = false;
  public boolean D = false;

  public boolean R = false;

  public KeyPressed() { }

  public void press(KeyEvent ev) {
    switch (ev.getKeyCode()) {
      case KeyEvent.VK_W: {
        this.W = true;
      } break;
      case KeyEvent.VK_S: {
        this.S = true;
      } break;
      case KeyEvent.VK_A: {
        this.A = true;
      } break;
      case KeyEvent.VK_D: {
        this.D = true;
      } break;
      case KeyEvent.VK_R: {
        this.R = true;
      } break;

      default: break;
    }
  }

  public void release(KeyEvent ev) {
    switch (ev.getKeyCode()) {
      case KeyEvent.VK_W: {
        this.W = false;
      } break;
      case KeyEvent.VK_S: {
        this.S = false;
      } break;
      case KeyEvent.VK_A: {
        this.A = false;
      } break;
      case KeyEvent.VK_D: {
        this.D = false;
      } break;
      case KeyEvent.VK_R: {
        this.R = false;
      } break;

      default: break;
    }
  }
}
