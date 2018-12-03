package org.quasar.interactive.coffee.dispenser.sm;

import org.junit.Assert;
import org.junit.Test;

public class CoffeeDispenserTest {
  @Test
  public void NoCoins_HasCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals("NoCoins", coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    coffeeDispenser.accept(50);
    Assert.assertEquals("HasCoins", coffeeDispenser.getCurrentstate().getClass().getSimpleName());
  }
  
  @Test
  public void NoCoins_EnoughCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals("NoCoins", coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    coffeeDispenser.accept(100);
    Assert.assertEquals("EnoughCoins", coffeeDispenser.getCurrentstate().getClass().getSimpleName());
  }
}
