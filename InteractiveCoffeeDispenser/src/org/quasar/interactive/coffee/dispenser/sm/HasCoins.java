package org.quasar.interactive.coffee.dispenser.sm;

public class HasCoins extends CoffeeDispenserState{

  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "fill", getClass().getSimpleName()));
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser) {
    if(coffeeDispenser.getClientAmount() >= CoffeeDispenser.COFFEE_PRICE
        && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new EnoughCoins());
    }
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew", getClass().getSimpleName()));
  }

  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    coffeeDispenser.setCurrentstate(new NoCoins());
  }
}
