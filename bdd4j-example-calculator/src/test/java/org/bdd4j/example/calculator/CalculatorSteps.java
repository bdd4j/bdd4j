package org.bdd4j.example.calculator;

import org.bdd4j.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bdd4j.StepDSL.*;

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
        return given("that I have a blank calculator").step(TestState::state);
    }

    public When<Calculator> whenIAddToTheSubtotal(final Integer value)
    {
        return when("I add {0} to the subtotal", value)
                .step((calculator) -> {
                    calculator.add(value);
                    return TestState.state(calculator);
                });
    }

    public When<Calculator> whenISubtractFromTheSubtotal(final Integer value)
    {
        return when("I subtract {0} from the subtotal", value)
                .step((calculator -> {
                    calculator.subtract(value);
                    return TestState.state(calculator);
                }));
    }

    public When<Calculator> whenIClearTheCalculator()
    {
        return when("I clear the calculator")
                .step((calculator) -> {
                    calculator.clear();
                    return TestState.state(calculator);
                });
    }

    public Then<Calculator> thenTheSubtotalShouldBe(final Integer value)
    {
        return then("the subtotal should be {0}", value)
                .step((state) -> {
                    assertThat(state.state()
                                    .subtotal()).isEqualTo(value);
                    return state;
                });
    }

    public Then<Calculator> thenTheCalculationShouldHaveFailedWithTheMessage(
            final String expectedMessage)
    {
        return then("the calculation should have failed with the message: {0}", expectedMessage)
                .step((state) -> {
                    assertThat(state.exception()).hasMessage(expectedMessage);
                    return state;
                });
    }
}
