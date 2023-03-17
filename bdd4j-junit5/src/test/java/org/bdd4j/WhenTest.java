package org.bdd4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.bdd4j.StepDSL.when;

import org.junit.jupiter.api.Test;

class WhenTest
{

  @Test
  public void applyLogic_withState() throws Throwable
  {
    final var api = new SomeTestedApi();

    final When<SomeTestedApi> step = when("I do the thing").step(state -> {
      state.doTheThing();

      return TestState.state(state);
    });

    step.applyLogic(TestState.state(api));
    assertThat(api.getNumberOfInvocations()).isEqualTo(1);
  }

  @Test
  public void applyLogic_withException()
  {
    final When<SomeTestedApi> step = when("I do the thing").step(TestState::state);

    assertThatThrownBy(
        () -> step.applyLogic(TestState.exception(new Exception("Something bad happened"))))
        .isInstanceOf(Exception.class)
        .hasMessage("Something bad happened");
  }
}