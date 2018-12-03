package org.quasar.interactive.coffee.dispenser.sm;

public enum CoffeeType {
  RISTRETTO("Coffee Ristretto", 15),
  ESPRESSO("Coffee Espresso", 30),
  LUNGO("Coffee Lungo", 50);

  private String name;
  private int volume;

  CoffeeType(String name, int volume) {
    this.name = name;
    this.volume = volume;
  }

  public String getName() {
    return name;
  }

  public int getVolume() {
    return volume;
  }
}
