package org.bdd4j.example.calculator;

import org.bdd4j.BDD4jRunner;
import org.bdd4j.Feature;
import org.bdd4j.Scenario;
import org.bdd4j.UserStory;


/**
 * A scenario based test for the very complex calculator.
 */
@Feature("Very complex calculator")
@UserStory("""
    As a mathematician
    I want to be able to do calculations
    In order to solve very complex problems.
    """)
public class CalculatorScenarioTest
{
  @Scenario("Add a value")
  public void addAValue(final CalculatorSteps steps)
  {
    BDD4jRunner.scenario(steps, steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(1), steps.thenTheSubtotalShouldBe(1));
  }

  @Scenario("Subtract a value")
  public void subtractAValue(final CalculatorSteps steps)
  {
    BDD4jRunner.scenario(steps, steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(11), steps.whenISubtractFromTheSubtotal(1),
        steps.thenTheSubtotalShouldBe(10));
  }

  @Scenario("Clear")
  public void clear(final CalculatorSteps steps)
  {
    BDD4jRunner.scenario(steps, steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(1337), steps.whenIClearTheCalculator(),
        steps.thenTheSubtotalShouldBe(0));
  }

  @Scenario("Integer overflow")
  public void integerOverflow(final CalculatorSteps steps)
  {
    BDD4jRunner.scenario(steps, steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(Integer.MAX_VALUE), steps.whenIAddToTheSubtotal(1),
        steps.thenTheCalculationShouldHaveFailedWithTheMessage(
            "Can't add the given value, because it would produce an Integer overflow"));
  }

  @Scenario("Integer underflow")
  public void integerUnderflow(final CalculatorSteps steps)
  {
    BDD4jRunner.scenario(steps, steps.givenThatIHaveABlankCalculator(),
        steps.whenIAddToTheSubtotal(Integer.MIN_VALUE), steps.whenISubtractFromTheSubtotal(1),
        steps.thenTheCalculationShouldHaveFailedWithTheMessage(
            "Can't subtract the given value, because it would produce an Integer underflow"));
  }
}
