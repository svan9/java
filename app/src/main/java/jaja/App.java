package jaja;

import jaja.map.Map;
import jaja.window.Window;
import jaja.window.scene.SceneRow;
import jaja.window.scene.scenes.MainScene;

public class App {
  public static void main(String[] args) {
    Window window = new Window();
    
    SceneRow scenes = new SceneRow();
    window.renderer.addSceneRow(scenes);
    
    scenes.setScene("main", 
      new MainScene(
        new Map.Properties(100, 100, 3)
          .setPlayerTexture("player"), 
          window.renderer
        )
      );
    scenes.forceScene(window.renderer, "main");
    
    window.start();
  }
}

// todo 
//@ optimization 
/* 
  # render only visible
  # generate image with all used depthes
*/
//@ generate floor of map with noise map
