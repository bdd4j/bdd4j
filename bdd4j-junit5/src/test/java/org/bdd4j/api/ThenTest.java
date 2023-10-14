package org.bdd4j.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bdd4j.api.StepDSL.then;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ThenTest {

  @Test
  public void applyLogic_withState() {
    final var api = new SomeTestedApi();

    final Then<SomeTestedApi> step = then("the thing should have been done").step(state -> {
      state.state().doTheThing();

      return state;
    });

    step.applyLogic(TestState.state(api));
    assertThat(api.getNumberOfInvocations()).isEqualTo(1);
  }

  @Test
  public void applyLogic_withException() {
    final Then<SomeTestedApi> step = then("that I do the thing").step(state -> state);

    final var result =
        step.applyLogic(TestState.exception(new Exception("Something bad happened")));

    final var assertions = new SoftAssertions();

    assertions.assertThat(result.exception().getMessage()).isEqualTo("Something bad happened");
    assertions.assertThat(result.exception()).isInstanceOf(Exception.class);

    assertions.assertAll();
  }
}