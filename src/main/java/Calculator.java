public class Calculator
{

  private Integer value = 0;

  public void add(final Integer value)
  {
    if (this.value == Integer.MAX_VALUE && value > 0)
    {
      throw new IllegalStateException(
          "Can't add the given value, because it would produce an Integer overflow");
    }

    this.value += value;
  }

  public void clear()
  {
    this.value = 0;
  }

  public void subtract(final Integer value)
  {
    this.value -= value;
  }

  public Integer subtotal()
  {
    return value;
  }
}
