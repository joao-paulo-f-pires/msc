package org.quasar.interactive.coffee.dispenser.sm;

public abstract class CoffeeDispenserState {
  abstract void fill(CoffeeDispenser coffeeDispenser);
  abstract void accept(CoffeeDispenser coffeeDispenser);
  abstract void brew(CoffeeDispenser coffeeDispenser);
  void reset(CoffeeDispenser coffeeDispenser){}
}
