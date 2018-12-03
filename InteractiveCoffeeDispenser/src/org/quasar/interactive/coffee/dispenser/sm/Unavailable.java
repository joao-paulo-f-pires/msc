package org.quasar.interactive.coffee.dispenser.sm;


public class Unavailable extends CoffeeDispenserState{

  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser, int amount) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "accept", "Unavailable"));
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser, CoffeeType coffeType) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew", "Unavailable"));
  }

  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    
  }
}
