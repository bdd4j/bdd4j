import static org.assertj.core.api.Assertions.assertThat;

import bdd4j.BDD4jSteps;
import bdd4j.Given;
import bdd4j.Then;
import bdd4j.When;

public class CalculatorSteps implements BDD4jSteps
{
  private final Calculator calculator = new Calculator();

  public Given givenThatIHaveABlankCalculator()
  {
    return new Given("that I have a blank calculator", () -> {
      // Blank on purpose
    });
  }

  public When whenIAddToTheSubtotal(final Integer value)
  {
    return new When(String.format("I add %d to the subtotal", value),
        () -> calculator.add(value));
  }

  public When whenISubtractFromTheSubtotal(final Integer value)
  {
    return new When(String.format("I subtract %d from the subtotal", value),
        () -> calculator.subtract(value));
  }

  public When whenIClearTheCalculator()
  {
    return new When("I clear the calculator", calculator::clear);
  }

  public Then thenTheSubtotalShouldBe(final Integer value)
  {
    return new Then(String.format("the subtotal should be %d", value),
        () -> assertThat(calculator.subtotal()).isEqualTo(value));
  }

  public Then thenTheCalculationShouldHaveFailedWithTheMessage(final String expectedMessage)
  {
    return new Then(
        String.format("the calculation should have failed with the message %s", expectedMessage),
        () -> {
          throw new IllegalStateException("Not implemented");
        });
  }
}
