package jaja.map.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
  
  public static int randRange(int x, int y) {
    return ThreadLocalRandom.current().nextInt(x, y + 1);
  }

  public static int randRangeMax(int x, int y, double percent) {
    if (randBool(percent)) return y;
    return ThreadLocalRandom.current().nextInt(x, y + 1);
  }
  public static int randRangeMin(int x, int y, double percent) {
    if (randBool(percent)) return x;
    return ThreadLocalRandom.current().nextInt(x, y + 1);
  }

  public static int randRange(int x, int y, double percent) {
    if (randBool(percent)) return 0;
    return ThreadLocalRandom.current().nextInt(x, y + 1);
  }

  public static int randRange(int x, int y, double percent, int target) {
    if (randBool(percent)) return target;
    return ThreadLocalRandom.current().nextInt(x, y + 1);
  }

  public static boolean randBool(double percent) {
    return ThreadLocalRandom.current().nextFloat() < percent;
  }

  public static Vector2i randVec(int minX, int maxX, int minY, int maxY) {
    return new Vector2i(randRange(minX, maxX), randRange(minY, maxY));
  }

  public static Vector2i randVec(int min, int max) {
    return new Vector2i(randRange(min, max), randRange(min, max));
  }

  public static Vector2i randVec(Vector2i min, Vector2i max) {
    return new Vector2i(randRange(min.x, max.x), randRange(min.y, max.y));
  }

  public static Vector2i randVec(Vector2i range) {
    return new Vector2i(randRange(range.x, range.y), randRange(range.x, range.y));
  }

  public static int clamp(int var, int min, int max) {
    if(var <= min) return min;
    else if(var >= max) return max;
    else return var;
  }
  
}
