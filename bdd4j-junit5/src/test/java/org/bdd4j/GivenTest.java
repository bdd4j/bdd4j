package org.bdd4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.bdd4j.StepDSL.given;

class GivenTest
{
    @Test
    public void applyLogic_withState() throws Throwable
    {
        final var api = new SomeTestedApi();

        final Given<SomeTestedApi> step = given("that I do the thing").step(state -> {
            state.doTheThing();

            return TestState.state(state);
        });

        step.applyLogic(TestState.state(api));
        assertThat(api.getNumberOfInvocations()).isEqualTo(1);
    }

    @Test
    public void applyLogic_withException()
    {
        final Given<SomeTestedApi> step = given("that I do the thing").step(TestState::state);

        assertThatThrownBy(
                () -> step.applyLogic(TestState.exception(new Exception("Something bad happened"))))
                .isInstanceOf(Exception.class)
                .hasMessage("Something bad happened");
    }
}