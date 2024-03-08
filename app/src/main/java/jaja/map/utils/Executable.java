package jaja.map.utils;

@FunctionalInterface
public interface Executable<RT, ARGS> {

  public abstract RT run(ARGS values);
}
