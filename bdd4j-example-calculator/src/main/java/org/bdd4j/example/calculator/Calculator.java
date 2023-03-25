package org.bdd4j.example.calculator;

/**
 * A very complex calculator.
 */
public class Calculator {

  private Integer value = 0;

  /**
   * Clears the subtotal of the calculator.
   */
  public void clear() {
    this.value = 0;
  }

  /**
   * Adds the given value to the current subtotal.
   *
   * @param value The value that should be added.
   */
  public void add(final Integer value) {
    if (this.value == Integer.MAX_VALUE && value > 0) {
      throw new IllegalStateException(
          "Can't add the given value, because it would produce an Integer overflow");
    }

    this.value += value;
  }

  /**
   * Subtracts the given value from the current subtotal.
   *
   * @param value The value that should be subtracted.
   */
  public void subtract(final Integer value) {
    if (this.value == Integer.MIN_VALUE && value > 0) {
      throw new IllegalStateException(
          "Can't subtract the given value, because it would produce an Integer underflow");
    }

    this.value -= value;
  }

  /**
   * The current subtotal.
   *
   * @return The subtotal.
   */
  public Integer subtotal() {
    return value;
  }
}
