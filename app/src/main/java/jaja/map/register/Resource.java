package jaja.map.register;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.*;

@JsonView
@JsonIgnoreProperties(ignoreUnknown=true)
public class Resource {
  private String type = "static";
  private java.util.HashMap<String, String> properties = new java.util.HashMap<>();
  private HashMap<String, String> textures = new HashMap<>();
  private HashMap<String, Object> frames = new HashMap<>();

  public HashMap<String, String> getTextures() {
    return textures;
  }

  public String getDefaultTextures() {
    String texture_ = "";

    if (textures.containsKey("")) {
      texture_ = textures.get("");
    } else if (textures.containsKey("default")) {
      texture_ = textures.get("default");
    }

    return texture_+":default";
  }
  
  public HashMap<String, Object> getFrames() {
    return frames;
  }

  public Resource.Type getType() {
    return Resource.Type.of(type);
  
  }

  public java.util.HashMap<String, String> getProperties() {
    return this.properties;
  }

  public static enum Type {
    STATIC("static"), ANIMATION("animation");
    String name;

    Type(String name) {
      this.name = name;
    }

    public String toString() {
      return this.name;
    }

    public static Type of(String name) {
      return Type.valueOf(name.toUpperCase());
    }
  }
  
}
