package jaja.window;

import javax.swing.*;

public class Window {
  public int width  = 500;
  public int height = 500;
  
  public Frame frame;
  public Listener listener;
  public Renderer renderer;

  public Window() {
    this.frame = new Frame();
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setSize(this.width, this.width);

    this.listener = new Listener();
    this.renderer = new Renderer();

    this.frame.addKeyListener(frame);
    this.frame.addMouseListener(frame);

    this.frame.setContentPane(this.renderer);
  
    this.renderer.listener = this.listener;
    
    this.renderer.onRender = () -> this.listener.onRender(this.renderer);
    this.renderer.onUpdate = () -> this.listener.onUpdate(this.renderer);
    
    this.frame.renderer = renderer;
    this.renderer.parrentframe = this.frame;

    this.init();
  }

  private void init() {
    this.frame.setExtendedState(this.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    this.frame.setUndecorated(true);
  }

  public void start() {
    this.frame.setVisible(true);
    this.renderer.start();
  }
  
}
