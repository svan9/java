package jaja.window;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import jaja.map.Map;
import jaja.map.utils.Vector2d;
import jaja.map.utils.Vector2i;
import jaja.window.events.KeyPressed;
import jaja.window.gui.Element;

public class Listener {

  public Listener() {

  }

  public void onRender(Renderer renderer) {

  }

  public void onUpdate(Renderer renderer) {

  }

  public void update(Map map, Map.Position<?> pos) {
    
  }

  // * MOUSE EVENT
  public void onMouseClick(MouseEvent e, Renderer r) {
    Vector2i pos = new Vector2i(e.getX(), e.getY());
    int m_ = 0;

    //@ ui
    for (Element element: r.elements) {
      if (element.isIn(pos, r)) {
        element.onClick(r);
        m_++;
      }
    }
    

    //@ cells
    if (m_ == 0) { 
      pos = r.getUnderCell(pos);

      if (e.getButton() == MouseEvent.BUTTON1) {
        r.map.putUnderBlock(pos);
      }
    }
  }

  public void onMouseDown() { }
  public void onMouseUp() { }
  public void onMouseMove() { }
  public void onMouseHold() { }
  
  // * KEYS EVENT
  public void onKeyHold(Map map, KeyEvent keyevent) { }

  public void onKeyDown(Map map, KeyEvent keyevent, KeyPressed keysPressed) {
    if (map.mainPlayer == null) return;
    
    map.mainPlayer.onStartMove();

    switch (keyevent.getKeyCode()) {
      case KeyEvent.VK_G: {
        //@ depth up
        map.toUpDepth();
      } break;
      case KeyEvent.VK_F: {
        //@ depth down
        map.toDownDepth();
      } break;
      default: break;
    }
  }
  public void onKeyUp(Map map, KeyEvent keyevent, KeyPressed keysPressed) { }

  public void onKeyPress(Map map, KeyPressed keysPressed) {
    if (keysPressed.W) {
      //@up
      map.mainPlayer.toUp();
    } else if (keysPressed.S) {
      //@down
      map.mainPlayer.toDown();
    }

    if (keysPressed.A) {
      //@left
      map.mainPlayer.toLeft();
    } else if (keysPressed.D) {
      //@right
      map.mainPlayer.toRight();
    }

    if (keysPressed.R) {
      map.mainPlayer.rotateCurrentBlockDirection();
    }
  }

  public void onPlayerMove(Renderer r) {
    // System.out.println(new Vector2d(r.map.mainPlayer.pos.x, r.map.mainPlayer.pos.y));
    r.restartUpdate(new Vector2d(r.map.mainPlayer.pos.x, r.map.mainPlayer.pos.y));
  }


  // * KEYBOARD EVENT
  // TODO
  public void onPressKeyBoardShortcut(Object kind) {

  }


  // * kruto
  // ! ai blyat
  // todo zdelaii choto suka
  // ? voprosy? 
  // comment
  //// crossed comment
  // @hz
  // #hz v2
  // ~hidden

}
