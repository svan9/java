package jaja.map.register;

import java.util.List;

import jaja.map.utils.DeltaTimer;

import java.awt.Image;

public class ImageResource {
  private Resource.Type type;
  private Image[] images;
  private int length;
  private Object frames = 1;
  private DeltaTimer dt = new DeltaTimer();
  private int time = 0;
  private int currentImage = 0;

  public Object getFrames() {
    return frames;
  }

  public void setFrames(Object frames) {
    this.frames = frames;
  }

  public int getLength() {
    return length;
  }

  public Image[] getImages() {
    return images;
  }

  public Resource.Type getType() {
    return type;
  }

  public ImageResource(Resource.Type type, Image[] images) {
    this.type = type;
    this.images = images;
    this.length = java.lang.reflect.Array.getLength(images);
  }

  public Image restart() {
    this.currentImage = 0;
    return this.images[0];
  }

  public Image getImageShot() {
    if (this.length == 1) {
      return this.images[0];
    }
    this.time += dt.deltaTime();
    int frame_time = 10;

    if (this.frames instanceof List frameTimes) {
      frame_time = (int)frameTimes.get(this.currentImage);
    } else if (this.frames instanceof Integer ft) {
      frame_time = ft;
    }

    if (this.time >= frame_time) {
      this.time = 0;
      this.currentImage++;
    }

    if (this.currentImage >= this.length) {
      this.currentImage = 0;
    }
    
    return this.images[this.currentImage];
  }

  @Override
  public String toString() {
    return (
      ("currentImage: "+this.currentImage)+
      ("; length: "+this.length)+
      ("; time: "+this.time)+
      ("; images: "+this.images)+
      ("; frames: "+this.frames)
      );
  }

}
