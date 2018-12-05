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
  public void HasCoins_HasCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);
    
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(20);
    
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(70, coffeeDispenser.getClientAmount());
  }

  @Test
  public void HasCoins_NoCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);
    
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.reset();
    
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }

  @Test
  public void HasCoins_EnoughCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(50);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void EnoughCoins_NoCoins_Reset_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.reset();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
    
  @Test
  public void EnoughCoins_NoCoins_Brew_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.brew(CoffeeType.LUNGO);
    
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void EnoughCoins_EnoughCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(200, coffeeDispenser.getClientAmount());
  }

  @Test
  public void EnoughCoins_HasCoins_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    
    coffeeDispenser.accept(50);
    coffeeDispenser.accept(50);
    coffeeDispenser.accept(50);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(150, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.brew(CoffeeType.LUNGO);
    
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void EnoughCoins_Unavailable_Transition_Test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(1000, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    
    Assert.assertEquals(Unavailable.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
  }
}
