package jaja.map.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.google.common.util.concurrent.Runnables;

import jaja.window.Renderer;

public class DeltaTimer {
  private long lastTime = 0; 
  private long currentTime = 0; 

  public DeltaTimer() {
    this.lastTime = this.getTime();
    this.currentTime = this.getTime();
  }
  
  public int deltaTime() {
    this.currentTime = this.getTime();
    int deltaTime_ = (int)(this.currentTime - this.lastTime);
    this.lastTime = this.currentTime;
    return deltaTime_;
  }

  private long getTime() {
    return System.currentTimeMillis();
  }

  public static void threadUpdate(Runnable callback, long period, long delay) {
    Timer timer = new Timer();

    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        callback.run();
      }
    };

    timer.scheduleAtFixedRate(timerTask, delay, period);
  }
}