package jaja.window.gui;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementResource {
  private List<String> text = new ArrayList<>();
  private List<Double> border = new ArrayList<>();
  private String border_color = "#000000";
  private String background_color = "#ffffff";
  private String align = "right-center";

  public List<String> getText() {
    return text;
  }
  public List<Double> getBorder() {
    return border;
  }
  public String getBorder_color() {
    return border_color;
  }
  public String getBackground_color() {
    return background_color;
  }
  public String getAlign() {
    return align;
  }
}
