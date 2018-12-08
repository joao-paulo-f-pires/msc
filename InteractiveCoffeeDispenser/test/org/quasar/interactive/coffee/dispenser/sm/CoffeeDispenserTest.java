package org.quasar.interactive.coffee.dispenser.sm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Using the Chinese Postman Tour (CPT) algorithm, the shortest open route that could be used<br>
 * in order to validate the state transitions and invariants for our academically example of CoffeDispenser is defined as follow:
 * 
 * Open CPT from 3 (ignore virtual arcs)<br>
 * Take arc 'virtual start' from 4 to 3<br>
 * (1) Take arc UNAVAILABLE_ENOUGH_COINS_fill from 3 to 1<br>
 * (2) Take arc ENOUGH_COINS_ENOUGH_COINS_brew from 1 to 1<br>
 * (3) Take arc ENOUGH_COINS_ENOUGH_COINS_accept from 1 to 1<br>
 * (4) Take arc ENOUGH_COINS_HAS_COINS_brew from 1 to 2<br>
 * (5) Take arc HAS_COINS_ENOUGH_COINS_accept from 2 to 1<br>
 * (6) Take arc ENOUGH_COINS_UNAVAILABLE_brew from 1 to 3<br>
 * (7) Take arc UNAVAILABLE_HAS_COINS_fill from 3 to 2<br>
 * (8) Take arc HAS_COINS_ENOUGH_COINS_accept from 2 to 1<br>
 * (9) Take arc ENOUGH_COINS_UNAVAILABLE_brew from 1 to 3<br>
 * (10) Take arc UNAVAILABLE_UNAVAILABLE_reset from 3 to 3<br>
 * (11) Take arc UNAVAILABLE_NO_COINS_fill from 3 to 0<br>
 * (12) Take arc NO_COINS_ENOUGH_COINS_accept from 0 to 1<br>
 * (13) Take arc ENOUGH_COINS_NO_COINS_reset from 1 to 0<br>
 * (14) Take arc NO_COINS_ENOUGH_COINS_accept from 0 to 1<br>
 * (15) Take arc ENOUGH_COINS_NO_COINS_brew from 1 to 0<br>
 * (16) Take arc NO_COINS_HAS_COINS_accept from 0 to 2<br>
 * (17) Take arc HAS_COINS_HAS_COINS_accept from 2 to 2<br>
 * (18) Take arc HAS_COINS_NO_COINS_reset from 2 to 0<br>
 * Take arc 'virtual end' from 0 to 4<br>
 * Best cost = 18.0<br>
 * 
 * Removing duplicated transitions from Open CPT in order to defined separated tests:<br>
 * 
 * Take arc UNAVAILABLE_ENOUGH_COINS_fill from 3 to 1<br>
 * Take arc ENOUGH_COINS_ENOUGH_COINS_brew from 1 to 1<br>
 * Take arc ENOUGH_COINS_ENOUGH_COINS_accept from 1 to 1<br>
 * Take arc ENOUGH_COINS_HAS_COINS_brew from 1 to 2<br>
 * Take arc HAS_COINS_ENOUGH_COINS_accept from 2 to 1<br>
 * Take arc ENOUGH_COINS_UNAVAILABLE_brew from 1 to 3<br>
 * Take arc UNAVAILABLE_HAS_COINS_fill from 3 to 2<br>
 * Take arc UNAVAILABLE_UNAVAILABLE_reset from 3 to 3<br>
 * Take arc UNAVAILABLE_NO_COINS_fill from 3 to 0<br>
 * Take arc NO_COINS_ENOUGH_COINS_accept from 0 to 1<br>
 * Take arc ENOUGH_COINS_NO_COINS_reset from 1 to 0<br>
 * Take arc ENOUGH_COINS_NO_COINS_brew from 1 to 0<br>
 * Take arc NO_COINS_HAS_COINS_accept from 0 to 2<br>
 * Take arc HAS_COINS_HAS_COINS_accept from 2 to 2<br>
 * Take arc HAS_COINS_NO_COINS_reset from 2 to 0<br>
 * 
 * @author Pires.J
 *
 */

public class CoffeeDispenserTest {  
  @Test
  public void UNAVAILABLE_ENOUGH_COINS_fill_test(){
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
    coffeeDispenser.accept(100);

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(1100, coffeeDispenser.getClientAmount());

    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.LUNGO);
    coffeeDispenser.brew(CoffeeType.RISTRETTO);

    Assert.assertEquals(Unavailable.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(200, coffeeDispenser.getClientAmount());
   
    coffeeDispenser.fill();
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(200, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void ENOUGH_COINS_ENOUGH_COINS_brew_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(100);
    coffeeDispenser.accept(100);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(200, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.brew(CoffeeType.ESPRESSO);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void ENOUGH_COINS_ENOUGH_COINS_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(100);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
    
    coffeeDispenser.accept(100);
    
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(200, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void ENOUGH_COINS_HAS_COINS_brew_test(){
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
  public void HAS_COINS_ENOUGH_COINS_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());

    coffeeDispenser.accept(50);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());

  }
  
  @Test
  public void ENOUGH_COINS_UNAVAILABLE_brew_test(){
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
  
  @Test
  public void UNAVAILABLE_HAS_COINS_fill_test(){
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
    coffeeDispenser.accept(50);
    coffeeDispenser.accept(20);

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(970, coffeeDispenser.getClientAmount());

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
    Assert.assertEquals(70, coffeeDispenser.getClientAmount());
   
    coffeeDispenser.fill();
    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(70, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void UNAVAILABLE_UNAVAILABLE_reset_test(){
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
    coffeeDispenser.accept(50);
    coffeeDispenser.accept(20);

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(970, coffeeDispenser.getClientAmount());

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
    Assert.assertEquals(70, coffeeDispenser.getClientAmount());
   
    coffeeDispenser.reset();
    Assert.assertEquals(Unavailable.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void UNAVAILABLE_NO_COINS_fill_test(){
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

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(900, coffeeDispenser.getClientAmount());

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
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
   
    coffeeDispenser.fill();;
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void NO_COINS_ENOUGH_COINS_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();

    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());

    coffeeDispenser.accept(100);

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void ENOUGH_COINS_NO_COINS_reset_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();

    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());

    coffeeDispenser.reset();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void ENOUGH_COINS_NO_COINS_brew_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();

    coffeeDispenser.accept(100);
    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(100, coffeeDispenser.getClientAmount());

    coffeeDispenser.brew(CoffeeType.LUNGO);

    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void NO_COINS_HAS_COINS_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());

    coffeeDispenser.accept(50);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());
  }
  
  @Test
  public void HAS_COINS_HAS_COINS_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());

    coffeeDispenser.accept(20);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(70, coffeeDispenser.getClientAmount());
  }

  @Test
  public void HAS_COINS_NO_COINS_reset_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(50);

    Assert.assertEquals(HasCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(50, coffeeDispenser.getClientAmount());

    coffeeDispenser.reset();

    Assert.assertEquals(NoCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(0, coffeeDispenser.getClientAmount());
  }
  
  @Test(expected = InvariantException.class)
  public void PRE_CONDITION_fill_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.fill();
  }
  
  @Test(expected = InvariantException.class)
  public void PRE_CONDITION_accept_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.accept(0);
  }
  
  @Test(expected = InvariantException.class)
  public void PRE_CONDITION_1_brew_test(){
    final CoffeeDispenser coffeeDispenser = new CoffeeDispenser();
    coffeeDispenser.brew(CoffeeType.RISTRETTO);
  }
  
  @Test(expected = InvariantException.class)
  public void PRE_CONDITION_2_brew_test(){
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
    coffeeDispenser.accept(20);

    Assert.assertEquals(EnoughCoins.class.getSimpleName(), coffeeDispenser.getCurrentstate().getClass().getSimpleName());
    Assert.assertEquals(920, coffeeDispenser.getClientAmount());

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
    Assert.assertEquals(20, coffeeDispenser.getClientAmount());
   
    coffeeDispenser.brew(CoffeeType.ESPRESSO);
  }
}
