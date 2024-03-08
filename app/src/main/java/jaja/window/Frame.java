package jaja.window;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import jaja.map.utils.Recent;
import jaja.window.events.KeyPressed;

public class Frame extends JFrame implements KeyListener, MouseListener {
  public Renderer renderer;
  public boolean isKeyPressed = false;
  public Recent<KeyEvent> keys = new Recent<>(10);
  public KeyPressed keysPressed = new KeyPressed();
  
  public Frame() {
    super();
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    this.setBackground(Color.black);
  }
  
  @Override
  public void keyTyped(KeyEvent e) { 

  }

  @Override
  public void keyPressed(KeyEvent e) {
    keys.add(e);
    keysPressed.press(e);
   
    if (!isKeyPressed) {
      renderer.listener.onKeyDown(renderer.map, e, keysPressed);
      isKeyPressed = true;
    }
  }
  
  public void pressed() {
    renderer.listener.onKeyPress(renderer.map, keysPressed);
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    keysPressed.release(e);
    renderer.listener.onKeyUp(renderer.map, e, keysPressed);
    isKeyPressed = false;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.renderer.listener.onMouseClick(e, renderer);
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
  
}
