package jaja.window.gui.guises;

import jaja.window.Renderer;
import jaja.window.gui.Element;

public class DepthElement extends Element {
  
  public DepthElement() {
    super();
    getText().add("DEPTH");
    getText().add("0");

    this.getBorder().moveTo(0, 0);
    this.getBorder().lineTo(100, 0);
    this.getBorder().lineTo(100, 100);
    this.getBorder().lineTo(0, 100);
    this.getBorder().closePath();
  }

  @Override
  public void update(Renderer r) {
    getText().set(1, (r.map.localDepth+1)+"");
  } 

  @Override
  public void onClick(Renderer r) {
    
  }
}
