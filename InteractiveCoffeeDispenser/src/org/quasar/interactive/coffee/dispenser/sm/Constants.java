package org.quasar.interactive.coffee.dispenser.sm;

public final class Constants {
  public static final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Operation '%s' not supported for state '%s'";
  public static final String INVALID_COIN_ERROR_MESSAGE = "Coin '%d' is not valid";
  public static final String FILL_ERROR_MESSAGE = "Water is in its maximum level '%d' [ml]";
  public static final String BREW_ERROR_MESSAGE = "Water is not available or not enough coins";
  
  private Constants(){
   //Generic class 
  }
}
