package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import bdd4j.BDD4jSteps;
import bdd4j.Given;
import bdd4j.TestState;
import bdd4j.Then;
import bdd4j.When;

/**
 * The steps used to test the calculator feature.
 */
public class CalculatorSteps implements BDD4jSteps<Calculator>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public TestState<Calculator> init()
  {
    return TestState.state(new Calculator());
  }

  public Given<Calculator> givenThatIHaveABlankCalculator()
  {
    return new Given<>("that I have a blank calculator", TestState::state);
  }

  public When<Calculator> whenIAddToTheSubtotal(final Integer value)
  {
    return new When<>(String.format("I add %d to the subtotal", value), (calculator) -> {
      calculator.add(value);
      return TestState.state(calculator);
    });
  }

  public When<Calculator> whenISubtractFromTheSubtotal(final Integer value)
  {
    return new When<>(String.format("I subtract %d from the subtotal", value), (calculator) -> {
      calculator.subtract(value);
      return TestState.state(calculator);
    });
  }

  public When<Calculator> whenIClearTheCalculator()
  {
    return new When<>("I clear the calculator", (calculator) -> {
      calculator.clear();
      return TestState.state(calculator);
    });
  }

  public Then<Calculator> thenTheSubtotalShouldBe(final Integer value)
  {
    return new Then<>(String.format("the subtotal should be %d", value), (state) -> {
      assertThat(state.state().subtotal()).isEqualTo(value);
      return TestState.state(state.state());
    });
  }

  public Then<Calculator> thenTheCalculationShouldHaveFailedWithTheMessage(
      final String expectedMessage)
  {
    return new Then<>(
        String.format("the calculation should have failed with the message: '%s'", expectedMessage),
        (state) -> {
          assertThat(state.exception()).hasMessage(expectedMessage);
          return state;
        });
  }
}
