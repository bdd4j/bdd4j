package org.bdd4j.example.calculator;

import org.bdd4j.api.Feature;
import org.bdd4j.api.Scenario;
import org.bdd4j.api.ScenarioOutline;
import org.bdd4j.api.ScenarioOutlineSpec;
import org.bdd4j.api.ScenarioSpec;
import org.bdd4j.api.UserStory;


/**
 * A scenario based test for the very complex calculator.
 */
@Feature("Very complex calculator")
@UserStory("""
    As a mathematician
    I want to be able to do calculations
    In order to solve very complex problems.
    """)
public class CalculatorScenarioTest {

  @Scenario("Add a value")
  public ScenarioSpec<CalculatorSteps, Calculator> addAValue() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(1),
            steps.thenTheSubtotalShouldBe(1));
  }

  @Scenario("Clear")
  public ScenarioSpec<CalculatorSteps, Calculator> clear() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(1337),
            steps.whenIClearTheCalculator(),
            steps.thenTheSubtotalShouldBe(0));
  }

  @Scenario("Integer overflow")
  public ScenarioSpec<CalculatorSteps, Calculator> integerOverflow() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(Integer.MAX_VALUE),
            steps.whenIAddToTheSubtotal(1),
            steps.thenTheCalculationShouldHaveFailedWithTheMessage(
                "Can't add the given value, because it would produce an Integer overflow"));
  }

  @Scenario("Integer underflow")
  public ScenarioSpec<CalculatorSteps, Calculator> integerUnderflow() {
    return (builder, steps) ->
        builder.defineScenario(
            steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(Integer.MIN_VALUE),
            steps.whenISubtractFromTheSubtotal(1),
            steps.thenTheCalculationShouldHaveFailedWithTheMessage(
                "Can't subtract the given value, because it would produce an Integer underflow"));
  }

  @ScenarioOutline(description = "Parameterized test",
      data = """
          | a | b | expected sum |
          | 1 | 1 | 2            |
          | 2 | 2 | 4            |
          """)
  public ScenarioOutlineSpec<CalculatorSteps, Calculator> parameterizedTest() {
    return (builder, steps, row) ->
        builder.defineScenario(
            steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(row.getInteger("a")),
            steps.whenIAddToTheSubtotal(row.getInteger("b")),
            steps.thenTheSubtotalShouldBe(row.getInteger("expected sum")));
  }

  @Scenario("Subtract a value")
  public ScenarioSpec<CalculatorSteps, Calculator> subtractAValue() {
    return (builder, steps) ->
        builder.defineScenario(steps.givenThatIHaveABlankCalculator(),
            steps.whenIAddToTheSubtotal(11), steps.whenISubtractFromTheSubtotal(1),
            steps.thenTheSubtotalShouldBe(10));
  }
}
