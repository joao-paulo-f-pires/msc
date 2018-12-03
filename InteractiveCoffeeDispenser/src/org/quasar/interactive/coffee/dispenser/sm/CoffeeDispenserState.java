package org.quasar.interactive.coffee.dispenser.sm;

public abstract class CoffeeDispenserState {
  protected static final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Operation '%s' not supported for state '%s'";
  protected static final String INVALID_COIN_ERROR_MESSAGE = "Coin '%d' is not valid";
  
  abstract void fill(CoffeeDispenser coffeeDispenser);
  abstract void accept(CoffeeDispenser coffeeDispenser, int amount);
  abstract void brew(CoffeeDispenser coffeeDispenser, CoffeeType coffeType);
  abstract void reset(CoffeeDispenser coffeeDispenser);
}
