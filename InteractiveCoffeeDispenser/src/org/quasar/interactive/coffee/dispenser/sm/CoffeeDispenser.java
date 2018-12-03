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
  public static final int[] VALID_COINS = { 10, 20, 50, 100, 200 };

  /**
   * Coffee types. Volumes are in [ml]
   */
  public static final CoffeeType[] COFFEE_TYPES = { CoffeeType.RISTRETTO, CoffeeType.ESPRESSO, CoffeeType.LUNGO };

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
    currentstate.fill(this);
  }

  /**
   * A coin is inserted into the dispenser.
   * 
   * @param amount
   *          Inserted coin.
   */
  public void accept(int amount) {
    currentstate.accept(this, amount);
  }

  /**
   * A coffee should be brewed<br>
   * Note, that there is no pre condition. The state machine below handles the correct amount.
   * 
   * @param coffeType
   *          Coffee type.
   */
  public void brew(CoffeeType coffeType) {
    currentstate.brew(this, coffeType);
  }

  /**
   * ReturnS the coins currently in the dispenser.
   */
  public void reset() {
    currentstate.reset(this);
  }

  /**
   * @return the amountEarned
   */
  public int getAmountEarned() {
    return amountEarned;
  }

  /**
   * @param amountEarned the amountEarned to set
   */
  public void setAmountEarned(int amountEarned) {
    this.amountEarned = amountEarned;
  }

  /**
   * @return the clientAmount
   */
  public int getClientAmount() {
    return clientAmount;
  }

  /**
   * @param clientAmount the clientAmount to set
   */
  public void setClientAmount(int clientAmount) {
    this.clientAmount = clientAmount;
  }

  /**
   * @return the coffeesBrewed
   */
  public int getCoffeesBrewed() {
    return coffeesBrewed;
  }

  /**
   * @param coffeesBrewed the coffeesBrewed to set
   */
  public void setCoffeesBrewed(int coffeesBrewed) {
    this.coffeesBrewed = coffeesBrewed;
  }

  /**
   * @return the waterAvailable
   */
  public int getWaterAvailable() {
    return waterAvailable;
  }

  /**
   * @param waterAvailable the waterAvailable to set
   */
  public void setWaterAvailable(int waterAvailable) {
    this.waterAvailable = waterAvailable;
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
