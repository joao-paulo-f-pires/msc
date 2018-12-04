package org.quasar.interactive.coffee.dispenser.sm;

public class InvariantException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  
  public InvariantException(String message){
    super(message);
  }
}
