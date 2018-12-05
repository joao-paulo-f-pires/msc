package org.quasar.interactive.coffee.dispenser.sm;


public class EnoughCoins implements CoffeeDispenserState{

  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "fill", getClass().getSimpleName()));
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser) {
    //Nothing to be done.
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser) {
    //Transition: ENOUGH_COINS-> HAS_COINS
    if (coffeeDispenser.getClientAmount() > 0 && coffeeDispenser.getClientAmount() < CoffeeDispenser.COFFEE_PRICE
        && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY) {
      coffeeDispenser.setCurrentstate(new HasCoins());
    }
    
    //Transition: ENOUGH_COINS-> NO_COINS
    if (coffeeDispenser.getClientAmount() == 0 && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY) {
      coffeeDispenser.setCurrentstate(new NoCoins());
    }
    
    //Transition: ENOUGH_COINS-> UNAVAILABLE
    if (coffeeDispenser.getWaterAvailable() < CoffeeDispenser.MINIMUM_CAPACITY) {
      coffeeDispenser.setCurrentstate(new Unavailable());
    }
  }
  
  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    //Transition: EnoughCoins -> NoCoins
    coffeeDispenser.setCurrentstate(new NoCoins());
  }
}
