package org.quasar.interactive.coffee.dispenser.sm;

import org.junit.Assert;
import org.junit.Test;

public class CoffeeDispenserTest {
  @Test
  public void NoCoins_HasCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(50);
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void NoCoins_EnoughCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void EnoughCoins_NoCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.reset();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
}
