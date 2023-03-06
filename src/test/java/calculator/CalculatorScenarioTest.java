package calculator;

import bdd4j.BDD4jTest;
import bdd4j.BDDRunner;
import bdd4j.Feature;
import bdd4j.Scenario;
import bdd4j.UserStory;
import org.junit.jupiter.api.Test;


/**
 * A scenario based test for the very complex calculator.
 */
@BDD4jTest
@Feature("Very complex calculator")
@UserStory("""
    As a mathematician
    I want to be able to do calculations
    In order to solve very complex problems.
    """)
public class CalculatorScenarioTest
{
  @Scenario("Add a value")
  @Test
  public void addAValue(final CalculatorSteps steps)
  {
    BDDRunner.scenario(steps,
        steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(1),
        steps.thenTheSubtotalShouldBe(1));
  }

  @Scenario("Subtract a value")
  @Test
  public void subtractAValue(final CalculatorSteps steps)
  {
    BDDRunner.scenario(
        steps,
        steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(11), steps.whenISubtractFromTheSubtotal(1),
        steps.thenTheSubtotalShouldBe(10));
  }

  @Scenario("Clear")
  @Test
  public void clear(final CalculatorSteps steps)
  {
    BDDRunner.scenario(
        steps,
        steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(1337), steps.whenIClearTheCalculator(),
        steps.thenTheSubtotalShouldBe(0));
  }

  @Scenario("Integer overflow")
  @Test
  public void integerOverflow(final CalculatorSteps steps)
  {
    BDDRunner.scenario(
        steps,
        steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(Integer.MAX_VALUE), steps.whenIAddToTheSubtotal(1),
        steps.thenTheCalculationShouldHaveFailedWithTheMessage(
            "Can't add the given value, because it would produce an Integer overflow"));
  }

  @Scenario("Integer underflow")
  @Test
  public void integerUnderflow(final CalculatorSteps steps)
  {
    BDDRunner.scenario(
        steps,
        steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(Integer.MIN_VALUE),
        steps.whenISubtractFromTheSubtotal(1),
        steps.thenTheCalculationShouldHaveFailedWithTheMessage(
            "Can't subtract the given value, because it would produce an Integer underflow"));
  }
}
