package jaja.window.scene.scenes;


import jaja.map.Map;
import jaja.window.Renderer;

public class MainScene extends Map {

  public MainScene(Map.Properties mapSize, Renderer renderer) {
    super(mapSize, renderer);

    this.register.add("empty");
    this.register.add("layer_blur");
    this.register.add("sand_floor");
    this.register.add("player");
    this.register.add("pipe");

    this.initedFillFloor(
      new String[] {
        "sand_floor",
        "sand_floor:0",
        "sand_floor:1",
        "sand_floor:2",
        "sand_floor:3",
      },
      new int[] {
        percentageGet(0.8),
        percentageGet(0.001),
        percentageGet(0.001),
        percentageGet(0.001),
        percentageGet(0.001),
      }
      );
    
  }
  
}
