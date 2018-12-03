package org.quasar.interactive.coffee.dispenser.sm;


public class NoCoins extends CoffeeDispenserState{
  private static final String NO_COINS = "NoCoins";

  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "fill", NO_COINS));
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser, int amount) {
    if(!preCondition(amount)){
      throw new IllegalArgumentException(String.format(INVALID_COIN_ERROR_MESSAGE, amount));
    }
    coffeeDispenser.setClientAmount(amount);
    if(coffeeDispenser.getClientAmount() > 0 
        && coffeeDispenser.getClientAmount() < CoffeeDispenser.COFFEE_PRICE 
        && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new HasCoins());
    }
    
    if(coffeeDispenser.getClientAmount() >= CoffeeDispenser.COFFEE_PRICE 
        && coffeeDispenser.getWaterAvailable() >= CoffeeDispenser.MINIMUM_CAPACITY){
      coffeeDispenser.setCurrentstate(new EnoughCoins());
    }
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser, CoffeeType coffeType) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew", NO_COINS));
  }

  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION_ERROR_MESSAGE, "reset", NO_COINS));
  }
  
  private boolean preCondition(int amount){
    for(int coin: CoffeeDispenser.VALID_COINS){
      if(coin == amount){
        return true;
      }
    }
    return false;
  }  
}
