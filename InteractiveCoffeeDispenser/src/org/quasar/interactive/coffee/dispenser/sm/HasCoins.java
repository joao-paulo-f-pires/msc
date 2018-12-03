package org.quasar.interactive.coffee.dispenser.sm;

import java.util.Arrays;

public class HasCoins extends CoffeeDispenserState{

  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "fill"));
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser, int amount) {
    if(!Arrays.asList(CoffeeDispenser.VALID_COINS).contains(amount)){
      throw new IllegalArgumentException(String.format(INVALID_COIN_ERROR_MESSAGE, amount));
    }
    coffeeDispenser.setClientAmount(amount);
    if(!(coffeeDispenser.getClientAmount() > 0 
           && coffeeDispenser.getClientAmount() < CoffeeDispenser.COFFEE_PRICE 
           && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY)){
      coffeeDispenser.setCurrentstate(new HasCoins());
    }
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser, CoffeeType coffeType) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew"));
  }

  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "reset"));
  }
}
