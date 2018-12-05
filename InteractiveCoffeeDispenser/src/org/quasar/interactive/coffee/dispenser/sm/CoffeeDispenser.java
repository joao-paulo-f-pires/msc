package org.quasar.interactive.coffee.dispenser.sm;

public class CoffeeDispenser {

  /**
   * Price of one coffee.
   */
  public static final int COFFEE_PRICE = 100;

  /**
   * Milliliters [ml].
   */
  public static final int RESERVOIR_CAPACITY = 500;

  /**
   * Milliliters [ml]. Reservoir should not be used until exhaustion.
   */
  public static final int MINIMUM_CAPACITY = 100;

  /**
   * Valid coins in cents.
   */
  private static final int[] VALID_COINS = { 10, 20, 50, 100, 200 };

  /**
   * Total amount inserted into the dispenser by the client.
   */
  private int clientAmount = 0;

  /**
   * Total number of coffees brewed since the dispenser was turned on.
   */
  private int coffeesBrewed = 0;

  /**
   * Total amount earned by the dispenser
   */
  private int amountEarned = coffeesBrewed * COFFEE_PRICE;

  /**
   * Remaining water in reservoir [ml]
   */
  private int waterAvailable = RESERVOIR_CAPACITY;

  /**
   * Current state of the instances of this class.
   */
  private CoffeeDispenserState currentstate;

  public CoffeeDispenser() {
    super();
    this.currentstate = new NoCoins();
  }

  /**
   * Fill the water reservoir.
   */
  public void fill() {
    //pre: waterAvailable < RESERVOIR_CAPACITY)
    if (!(waterAvailable < RESERVOIR_CAPACITY)) {
      throw new InvariantException(String.format(Constants.INVALID_COIN_ERROR_MESSAGE, RESERVOIR_CAPACITY));
    }
    waterAvailable = RESERVOIR_CAPACITY;
    currentstate.fill(this);
  }

  /**
   * A coin is inserted into the dispenser.
   * 
   * @param amount
   *          Inserted coin.
   */
  public void accept(int amount) {
    //pre: VALID_COINS->includes(i) 
    boolean isCoinValid = false;
    for(int coin: VALID_COINS){
      if(coin == amount){
        isCoinValid =  true;
        break;
      }
    }
    
    if(!isCoinValid){
      throw new InvariantException(String.format(Constants.INVALID_COIN_ERROR_MESSAGE, amount));
    }
    this.clientAmount += amount;
    currentstate.accept(this);
  }

  /**
   * A coffee should be brewed<br>
   * Note, that there is no pre condition. The state machine below handles the correct amount.
   * 
   * @param coffeType
   *          Coffee type.
   */
  public void brew(CoffeeType coffeType) {
    // pre: waterAvailable >= MINIMUM_CAPACITY and clientAmount >= COFFEE_PRICE;
    if (!(waterAvailable >= MINIMUM_CAPACITY && clientAmount >= COFFEE_PRICE)) {
      throw new InvariantException(Constants.BREW_ERROR_MESSAGE);
    }
    clientAmount -= COFFEE_PRICE;
    amountEarned += COFFEE_PRICE;
    coffeesBrewed += 1;
    
    if(CoffeeType.RISTRETTO == coffeType){
      waterAvailable -= 15;  
    }
    
    if(CoffeeType.ESPRESSO == coffeType){
      waterAvailable -= 30;  
    }
    
    if(CoffeeType.LUNGO == coffeType){
      waterAvailable -= 50;  
    }
    currentstate.brew(this);
  }

  /**
   * ReturnS the coins currently in the dispenser.
   */
  public void reset() {
    this.clientAmount = 0;
    currentstate.reset(this);
  }

  /**
   * @return the amountEarned
   */
  public int getAmountEarned() {
    return amountEarned;
  }

  /**
   * @return the clientAmount
   */
  public int getClientAmount() {
    return clientAmount;
  }

  /**
   * @return the coffeesBrewed
   */
  public int getCoffeesBrewed() {
    return coffeesBrewed;
  }

  /**
   * @return the waterAvailable
   */
  public int getWaterAvailable() {
    return waterAvailable;
  }

  /**
   * @return the currentstate
   */
  public CoffeeDispenserState getCurrentstate() {
    return currentstate;
  }

  /**
   * @param currentstate the currentstate to set
   */
  public void setCurrentstate(CoffeeDispenserState currentstate) {
    this.currentstate = currentstate;
  }  
}
