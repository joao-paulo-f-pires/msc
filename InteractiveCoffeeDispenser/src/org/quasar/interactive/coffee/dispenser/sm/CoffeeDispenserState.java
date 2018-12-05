package org.quasar.interactive.coffee.dispenser.sm;

public interface CoffeeDispenserState {
  void fill(CoffeeDispenser coffeeDispenser);
  void accept(CoffeeDispenser coffeeDispenser);
  void brew(CoffeeDispenser coffeeDispenser);
  void reset(CoffeeDispenser coffeeDispenser);
}
