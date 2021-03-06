package org.quasar.interactive.coffee.dispenser.sm;

public class NoCoins implements CoffeeDispenserState {
  @Override
  public void fill(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "fill",
        getClass().getSimpleName()));
  }

  @Override
  public void accept(CoffeeDispenser coffeeDispenser) {
    // Transition: NoCoins -> HasCoins
    if (coffeeDispenser.getClientAmount() < CoffeeDispenser.COFFEE_PRICE) {
      coffeeDispenser.setCurrentstate(new HasCoins());
    }

    // Transition: NoCoins -> EnoughCoins
    if (coffeeDispenser.getClientAmount() >= CoffeeDispenser.COFFEE_PRICE) {
      coffeeDispenser.setCurrentstate(new EnoughCoins());
    }
  }

  @Override
  public void brew(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "brew",
        getClass().getSimpleName()));
  }

  @Override
  public void reset(CoffeeDispenser coffeeDispenser) {
    throw new UnsupportedOperationException(String.format(Constants.UNSUPPORTED_OPERATION_ERROR_MESSAGE, "reset",
        getClass().getSimpleName()));
  }
}
