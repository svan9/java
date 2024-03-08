package jaja.window.scene;

import java.util.HashMap;

import jaja.map.Map;
import jaja.window.Renderer;

public class SceneRow {
  private HashMap<String, Map> scenes = new HashMap<>();

  public SceneRow() {}

  public Map getScene(String name) {
    return this.scenes.get(name);
  }

  public void setScene(String name, Map map) {
    if (!this.scenes.containsKey(name)) {
      this.scenes.put(name, map);
    }
  }

  public void forceScene(Renderer r, String name) {
    Map map = this.getScene(name);
    
    if (map == null) {
      throw new Error("MAP IS NULL");
    }

    r.setMap(map);
    r.map.renderer = r;
  }

}
