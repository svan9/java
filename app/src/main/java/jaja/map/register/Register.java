package jaja.map.register;

import java.util.*;

import javax.imageio.ImageIO;

import java.io.*;
import java.awt.Image;

import com.fasterxml.jackson.databind.*;

import jaja.window.Renderer;

public class Register {
  public HashMap<String, Integer> blocks = new HashMap<>();
  public HashMap<Integer, ImageResource> textures = new HashMap<>();
  public HashMap<Integer, Resource> resources = new HashMap<>();

  public Register() {
  }

  public void add(String name) {
    String path = "json/" + name + ".json";
    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    if (stream == null)
      return;

    ObjectMapper objectMapper = new ObjectMapper();
    Resource info = null;

    try {
      info = objectMapper.readValue(stream, Resource.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (info == null) {
      this.add("empty");
    }

    HashMap<String, String> Alltextures = info.getTextures();
    Set<String> keys = Alltextures.keySet();

    for (String key: keys) {
      String texture = Alltextures.get(key);
      Image image = readTexture(texture+".png");

      ImageResource imgResource = new ImageResource(info.getType(), Renderer.cutImageRow(image));
      imgResource.setFrames(info.getFrames().get(key));
      
      String nameID = name;

      if (key.isEmpty()) {
        // nameID += ":default";
      } else {
        nameID += ":"+key;
      }
      
      int id = blocks.size();
      this.blocks.put(nameID, id);
      this.textures.put(id, imgResource);
      this.resources.put(id, info);
    }

  }

  private Image readTexture(String path) {
    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    Image texure = null;
    try {
      texure = ImageIO.read(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return texure;
  }

  public int getBlockId(String name) {
    return blocks.get(name);
  }

  public Image getTexture(int id) {
    return textures.get(id).getImageShot();
  }

  public Image getTexture(String name) {
    if (!blocks.containsKey(name)) {
      throw new Error("texture nameID undefined: "+ name);
    }
  
    int textureID = blocks.get(name);

    if (!textures.containsKey(textureID)) {
      throw new Error("texture nameID undefined: " + textureID);
    }

    Image img = textures.get(textureID).getImageShot();

    return img;
  }

  public Image getDefaultTexture(String name) {
    return this.getTexture(name, "default");
  }

  public Image getTexture(String name, String pointer) {
    return textures.get(blocks.get(this.getTextureName(name, pointer))).getImageShot();
  }

  private String getTextureName(String name, String pointer) {
    return name+":"+pointer;
  }

  public Resource getResource(String name) {
    return resources.get(blocks.get(name));
  }

  @Override
  public String toString() {
    return "blocks: "+blocks+"\ntextures: "+textures+"\nresources: " + resources + "\n";
  }
}
