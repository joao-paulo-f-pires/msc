package org.quasar.interactive.coffee.dispenser.sm;


public class Unavailable implements CoffeeDispenserState{
  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    //Transition: Unavailable -> NoCoins
    if(coffeeDispenser.getClientAmount() == 0 && coffeeDispenser.getWaterAvailable() >= 
        CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new NoCoins());
    }
    
    //Transition: Unavailable -> HasCoins
    if(coffeeDispenser.getClientAmount() >= 0 && coffeeDispenser.getClientAmount() < CoffeeDispenser.COFFEE_PRICE 
        && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new HasCoins());
    }
    
    //Transition: Unavailable -> EnoughCoins
    if(coffeeDispenser.getClientAmount() >= CoffeeDispenser.COFFEE_PRICE && 
        coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new EnoughCoins());
    }
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "accept", getClass().getSimpleName()));
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew", getClass().getSimpleName()));
  }
  
  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    //Transition: Unavailable -> Unavailable
    //Nothing to be done.
  }
}
